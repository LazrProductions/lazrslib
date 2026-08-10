package com.lazrproductions.lazrslib.common.network;


import com.lazrproductions.lazrslib.LazrsLibConstants;
import com.lazrproductions.lazrslib.common.network.handling.FabricPacketContext;
import com.lazrproductions.lazrslib.common.network.packet.ILazrPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerChunkCache;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.function.Function;

public class LazrNetwork implements ILazrNetwork {
    final ResourceLocation channel;
    final int version;
    private int id = 0;

    //final ServerHandler serverHandler;

    private final HashMap<Class<? extends ILazrPacket>, CustomPacketPayload.Type<? extends ILazrPacket>> packetTypes = new HashMap<>();

    public LazrNetwork(ResourceLocation location, int version) {
        this.version = version;
        this.channel = location;
        //this.serverHandler = new ClientHandler();
    }

    public <T extends ILazrPacket> void registerPacket(Class<T> classType, Function<FriendlyByteBuf, T> decoder) {
        var id = new CustomPacketPayload.Type<T>(ResourceLocation.tryBuild(channel.getNamespace(), channel.getPath() + getNextId() + "s"));

        PayloadTypeRegistry.playC2S().register(id, StreamCodec.ofMember(
                T::encode,
                (buf) -> {
                    T packet = decoder.apply(buf);
                    packet.setType(id);
                    return packet;
                }));
        PayloadTypeRegistry.playS2C().register(id, StreamCodec.ofMember(
                T::encode,
                (buf) -> {
                    T packet = decoder.apply(buf);
                    packet.setType(id);
                    return packet;
                }));

        ServerHandler.register(classType, id);
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
            ClientHandler.register(classType, id);
        packetTypes.put(classType, id);
    }

    protected <T extends ILazrPacket> T prepare(T packet) {
        packet.setType(packetTypes.get(packet.getClass()));
        return packet;
    }

    private int getNextId() {
        return id++;
    }

    public static class ServerHandler {
        public static <T extends ILazrPacket> void register(Class<T> classType, CustomPacketPayload.Type<T> id) {
            ServerPlayNetworking.registerGlobalReceiver(id, (payload, context) -> {
                        try {
                            context.server().execute(() -> payload.handle(new FabricPacketContext(context.player(), Side.SERVER)));
                        } catch (Throwable e) {
                            LazrsLibConstants.LOG.error("Executing a packet ran into an exception", e);
                            throw e;
                        }
                    });
        }
    }

    @Environment(EnvType.CLIENT)
    public static class ClientHandler {
        @Environment(EnvType.CLIENT)
        public static <T extends ILazrPacket> void register(Class<T> classType, CustomPacketPayload.Type<T> id) {
            ClientPlayNetworking.registerGlobalReceiver(id, (payload, context) -> {
                try {
                    context.client().execute(() -> payload.handle(new FabricPacketContext(context.player(), Side.CLIENT)));
                } catch (Throwable e) {
                    LazrsLibConstants.LOG.error("Executing a packet ran into an exception", e);
                    throw e;
                }
            });
        }
    }

    /**
     * Sends a packet to the server
     *
     * @param msg Packet to send
     */
    @Override
    public void sendToServer(ILazrPacket msg) {
        ClientPlayNetworking.send(prepare(msg));
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
            sendToClient(prepare(msg), (ServerPlayer) player);
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
        ServerPlayNetworking.send(player, prepare(msg));
    }

    /**
     * Sends a packet to all entities tracking the given entity
     *
     * @param msg    Packet
     * @param entity Entity to check
     */
    @Override
    public void sendToTrackingAndSelf(ILazrPacket msg, Entity entity) {
        if (entity.level().getChunkSource() instanceof ServerChunkCache chunkCache)
            chunkCache.broadcastAndSend(entity, ServerPlayNetworking.createS2CPacket(prepare(msg)));
    }

    /**
     * Sends a packet to all entities tracking the given entity
     *
     * @param msg    Packet
     * @param entity Entity to check
     */
    @Override
    public void sendToTracking(ILazrPacket msg, Entity entity) {
        if (entity.level().getChunkSource() instanceof ServerChunkCache chunkCache)
            chunkCache.broadcastAndSend(entity, ServerPlayNetworking.createS2CPacket(prepare(msg)));
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
}