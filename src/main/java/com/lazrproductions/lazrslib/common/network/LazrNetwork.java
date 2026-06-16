package com.lazrproductions.lazrslib.common.network;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.lazrproductions.lazrslib.common.network.base.ILazrPacket;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;

public class LazrNetwork {
    private final ResourceLocation location;
    private final String version;
    private final List<PacketRegistration<?>> registrations = new ArrayList<>();
    private final Map<Class<? extends ILazrPacket>, CustomPacketPayload.Type<?>> classToTypeMap = new HashMap<>();

    private static class PacketRegistration<T extends ILazrPacket> {
        final Class<T> clazz;
        final Function<FriendlyByteBuf, T> decoder;
        @Nullable
        final PacketFlow direction;

        PacketRegistration(Class<T> clazz, Function<FriendlyByteBuf, T> decoder, @Nullable PacketFlow direction) {
            this.clazz = clazz;
            this.decoder = decoder;
            this.direction = direction;
        }
    }

    public LazrNetwork(ResourceLocation location, int version) {
        this(net.neoforged.fml.ModLoadingContext.get().getActiveContainer().getEventBus(), location, version);
    }

    public LazrNetwork(IEventBus modEventBus, ResourceLocation location, int version) {
        this.location = location;
        this.version = String.valueOf(version);
        modEventBus.addListener(this::registerPayloads);
    }

    private void registerPayloads(final RegisterPayloadHandlersEvent event) {
        PayloadRegistrar registrar = event.registrar(this.version);
        for (PacketRegistration<?> reg : registrations) {
            registerToNeoForge(registrar, reg);
        }
    }

    @SuppressWarnings("unchecked")
    private <T extends ILazrPacket> void registerToNeoForge(PayloadRegistrar registrar, PacketRegistration<T> reg) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(
            location.getNamespace(),
            location.getPath() + "_" + reg.clazz.getSimpleName().toLowerCase(java.util.Locale.ROOT)
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
                (wrapper, context) -> wrapper.getPacket().handle(context)
            );
        } else if (reg.direction == PacketFlow.CLIENTBOUND) {
            registrar.playToClient(
                type,
                codec,
                (wrapper, context) -> wrapper.getPacket().handle(context)
            );
        } else {
            registrar.playBidirectional(
                type,
                codec,
                new DirectionalPayloadHandler<>(
                    (wrapper, context) -> wrapper.getPacket().handle(context),
                    (wrapper, context) -> wrapper.getPacket().handle(context)
                )
            );
        }

        classToTypeMap.put(reg.clazz, type);
    }

    public <T extends ILazrPacket> void registerClientBoundPacket(Class<T> c, Function<FriendlyByteBuf, T> decoder) {
        registrations.add(new PacketRegistration<>(c, decoder, PacketFlow.CLIENTBOUND));
    }
    public <T extends ILazrPacket> void registerServerBoundPacket(Class<T> c, Function<FriendlyByteBuf, T> decoder) {
        registrations.add(new PacketRegistration<>(c, decoder, PacketFlow.SERVERBOUND));
    }
    public <T extends ILazrPacket> void registerPacket(Class<T> c, Function<FriendlyByteBuf, T> decoder) {
        registrations.add(new PacketRegistration<>(c, decoder, null));
    }

    @SuppressWarnings("unchecked")
    private <T extends ILazrPacket> LazrPayloadWrapper<T> wrap(T packet) {
        CustomPacketPayload.Type<LazrPayloadWrapper<T>> type = (CustomPacketPayload.Type<LazrPayloadWrapper<T>>) classToTypeMap.get(packet.getClass());
        if (type == null) {
            throw new IllegalArgumentException("Packet " + packet.getClass().getName() + " is not registered in this channel!");
        }
        return new LazrPayloadWrapper<>(type, packet);
    }

    /* Sending packets */

    public void sendToServer(Object msg) {
        if (msg instanceof ILazrPacket packet) {
            PacketDistributor.sendToServer(wrap(packet));
        } else if (msg instanceof CustomPacketPayload payload) {
            PacketDistributor.sendToServer(payload);
        } else {
            throw new IllegalArgumentException("Cannot send non-packet object to server: " + msg);
        }
    }

    public void sendVanillaPacket(Packet<?> packet, Entity player) {
        if (player instanceof ServerPlayer && ((ServerPlayer) player).connection != null) {
            ((ServerPlayer) player).connection.send(packet);
        }
    }

    public void sendTo(Object msg, Player player) {
        if (player instanceof ServerPlayer) {
            sendTo(msg, (ServerPlayer) player);
        }
    }

    public void sendTo(Object msg, ServerPlayer player) {
        if (!(player instanceof FakePlayer) && player.connection != null) {
            PacketDistributor.sendToPlayer(player, wrap((ILazrPacket) msg));
        }
    }

    public void sendToClientsAround(Object msg, ServerLevel serverWorld, BlockPos position) {
        LevelChunk chunk = serverWorld.getChunkAt(position);
        PacketDistributor.sendToPlayersTrackingChunk(serverWorld, chunk.getPos(), wrap((ILazrPacket) msg));
    }

    public void sendToTrackingAndSelf(Object msg, Entity entity) {
        PacketDistributor.sendToPlayersTrackingEntityAndSelf(entity, wrap((ILazrPacket) msg));
    }

    public void sendToTracking(Object msg, Entity entity) {
        PacketDistributor.sendToPlayersTrackingEntity(entity, wrap((ILazrPacket) msg));
    }

    public void sendToClientsAround(Object msg, @Nullable LevelAccessor world, BlockPos position) {
        if (world instanceof ServerLevel) {
            sendToClientsAround(msg, (ServerLevel) world, position);
        }
    }
}
