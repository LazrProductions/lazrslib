package com.lazrproductions.lazrslib.common.network;


import com.lazrproductions.lazrslib.common.network.handling.LazrPayloadWrapper;
import com.lazrproductions.lazrslib.common.network.handling.NeoForgePacketContext;
import com.lazrproductions.lazrslib.common.network.packet.ILazrPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class LazrNetwork implements ILazrNetwork {
    private final ResourceLocation channel;
    private final String version;
    private int id = 0;

    private final List<PacketRegistration<?>> registrations = new ArrayList<>();
    private final Map<Class<? extends ILazrPacket>, CustomPacketPayload.Type<?>> packetTypes = new HashMap<>();

    public LazrNetwork(ResourceLocation location, int version) {
        this(ModLoadingContext.get().getActiveContainer().getEventBus(), location, version);
    }

    public LazrNetwork(IEventBus modEventBus, ResourceLocation location, int version) {
        this.channel = location;
        this.version = String.valueOf(version);
        modEventBus.addListener(this::registerPayloads);
    }

    private void registerPayloads(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(this.version);
        for (PacketRegistration<?> reg : registrations) {
            registerToNeoForge(registrar, reg);
        }
    }

    private <T extends ILazrPacket> void registerToNeoForge(PayloadRegistrar registrar, PacketRegistration<T> reg) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(
                channel.getNamespace(),
                channel.getPath() + "_" + reg.clazz.getSimpleName().toLowerCase(java.util.Locale.ROOT)
        );
        CustomPacketPayload.Type<LazrPayloadWrapper<T>> type = new CustomPacketPayload.Type<>(id);

        StreamCodec<FriendlyByteBuf, LazrPayloadWrapper<T>> codec = StreamCodec.of(
                (buf, wrapper) -> wrapper.getPacket().encode(buf),
                buf -> new LazrPayloadWrapper<>(type, reg.decoder.apply(buf))
        );

        if (reg.direction == PacketFlow.SERVERBOUND) {
            registrar.playToServer(
                    type,
                    codec,
                    (wrapper, context) -> wrapper.getPacket().handle(new NeoForgePacketContext(context))
            );
        } else if (reg.direction == PacketFlow.CLIENTBOUND) {
            registrar.playToClient(
                    type,
                    codec,
                    (wrapper, context) -> wrapper.getPacket().handle(new NeoForgePacketContext(context))
            );
        } else {
            registrar.playBidirectional(
                    type,
                    codec,
                    new DirectionalPayloadHandler<>(
                            (wrapper, context) -> wrapper.getPacket().handle(new NeoForgePacketContext(context)),
                            (wrapper, context) -> wrapper.getPacket().handle(new NeoForgePacketContext(context))
                    )
            );
        }

        packetTypes.put(reg.clazz, type);
    }

    public <T extends ILazrPacket> void registerPacket(Class<T> classType, Function<FriendlyByteBuf, T> decoder) {
        registrations.add(new PacketRegistration<>(classType, decoder, null));
    }

    private int getNextId() {
        return id++;
    }

    @SuppressWarnings("unchecked")
    private <T extends ILazrPacket> LazrPayloadWrapper<T> wrap(T packet) {
        CustomPacketPayload.Type<LazrPayloadWrapper<T>> type = (CustomPacketPayload.Type<LazrPayloadWrapper<T>>) packetTypes.get(packet.getClass());
        if (type == null) {
            throw new IllegalArgumentException("Packet " + packet.getClass().getName() + " is not registered in this channel!");
        }
        return new LazrPayloadWrapper<>(type, packet);
    }

    /**
     * Sends a packet to the server
     *
     * @param msg Packet to send
     */
    @Override
    public void sendToServer(ILazrPacket msg) {
        PacketDistributor.sendToServer(wrap(msg));
    }

    /**
     * Sends a packet to a player
     *
     * @param msg    Packet
     * @param player Player to send
     */
    @Override
    public void sendToClient(ILazrPacket msg, Player player) {
        if (player instanceof ServerPlayer) {
            sendToClient(msg, (ServerPlayer) player);
        }
    }

    /**
     * Sends a packet to a player
     *
     * @param msg    Packet
     * @param player Player to send
     */
    @Override
    public void sendToClient(ILazrPacket msg, ServerPlayer player) {
        if (!(player instanceof FakePlayer)) {
            PacketDistributor.sendToPlayer(player, wrap(msg));
        }
    }

    /**
     * Sends a packet to all entities tracking the given entity
     *
     * @param msg    Packet
     * @param entity Entity to check
     */
    @Override
    public void sendToTrackingAndSelf(ILazrPacket msg, Entity entity) {
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, wrap(msg));
    }

    /**
     * Sends a packet to all entities tracking the given entity
     *
     * @param msg    Packet
     * @param entity Entity to check
     */
    @Override
    public void sendToTracking(ILazrPacket msg, Entity entity) {
        PacketDistributor.sendToPlayersTrackingEntity(entity, wrap(msg));
    }

    /**
     * Sends a vanilla packet to the given entity
     *
     * @param player Player receiving the packet
     * @param packet Packet
     */
    @Override
    public void sendVanillaPacket(Packet<?> packet, Entity player) {
        if (player instanceof ServerPlayer) {
            ((ServerPlayer) player).connection.send(packet);
        }
    }

    private record PacketRegistration<T extends ILazrPacket>(Class<T> clazz, Function<FriendlyByteBuf, T> decoder,
                                                             @Nullable PacketFlow direction) {
    }
}