package com.lazrproductions.lazrslib.common.network;

import com.lazrproductions.lazrslib.common.network.handling.FabricPacketContext;
import com.lazrproductions.lazrslib.common.network.packet.ILazrPacket;
import io.netty.buffer.Unpooled;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
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

    private final HashMap<Class<? extends ILazrPacket>, ResourceLocation> packetChannels = new HashMap<>();

    public LazrNetwork(ResourceLocation location, int version) {
        this.version = version;
        this.channel = location;
    }

    public <T extends ILazrPacket> void registerPacket(Class<T> classType, Function<FriendlyByteBuf, T> decoder) {

        ResourceLocation id = new ResourceLocation(channel.getNamespace(), channel.getPath() + getNextId());

        ServerHandler.register(decoder, id);
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
            ClientHandler.register(decoder, id);

        packetChannels.put(classType, id);
    }

    private int getNextId() {
        return id++;
    }

    public static class ServerHandler {
        public static <T extends ILazrPacket> void register(Function<FriendlyByteBuf, T> decoder, ResourceLocation id) {
            ServerPlayNetworking.registerGlobalReceiver(id, (server, player, handler, buf, responseSender) -> {
                var message = decoder.apply(buf);
                server.execute(() -> message.handle(new FabricPacketContext(player, Side.SERVER)));
            });
        }
    }

    @Environment(EnvType.CLIENT)
    public static class ClientHandler {
        @Environment(EnvType.CLIENT)
        public static <T extends ILazrPacket> void register(Function<FriendlyByteBuf, T> decoder, ResourceLocation id) {
            ClientPlayNetworking.registerGlobalReceiver(id, new Handler<>(decoder));
        }

        @Environment(EnvType.CLIENT)
        private static class Handler<T extends ILazrPacket> implements ClientPlayNetworking.PlayChannelHandler {
            private final Function<FriendlyByteBuf, T> decoder;

            Handler(Function<FriendlyByteBuf, T> decoder) {
                this.decoder = decoder;
            }

            @Override
            public void receive(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) {
                var message = decoder.apply(buf);
                client.execute(() -> message.handle(new FabricPacketContext(client.player, Side.CLIENT)));
            }
        }
    }

    /**
     * Sends a packet to the server
     *
     * @param msg Packet to send
     */
    @Override
    public void sendToServer(ILazrPacket msg) {
        var buffer = new FriendlyByteBuf(Unpooled.buffer());
        msg.encode(buffer);
        ClientPlayNetworking.send(packetChannels.get(msg.getClass()), buffer);
    }

    /**
     * Sends a packet to a player
     *
     * @param msg    Packet
     * @param player Player to send
     */
    @Override
    public void sendToClient(ILazrPacket msg, Player player) {
        if (player instanceof ServerPlayer serverPlayer) {
            sendToClient(msg, serverPlayer);
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
        var buffer = new FriendlyByteBuf(Unpooled.buffer());
        msg.encode(buffer);
        ServerPlayNetworking.send(player, packetChannels.get(msg.getClass()), buffer);
    }

    /**
     * Sends a packet to all entities tracking the given entity
     *
     * @param msg    Packet
     * @param entity Entity to check
     */
    @Override
    public void sendToTrackingAndSelf(ILazrPacket msg, Entity entity) {
        var buffer = new FriendlyByteBuf(Unpooled.buffer());
        msg.encode(buffer);
        if (entity.level().getChunkSource() instanceof ServerChunkCache chunkCache)
            chunkCache.broadcastAndSend(entity, ServerPlayNetworking.createS2CPacket(packetChannels.get(msg.getClass()), buffer));
    }

    /**
     * Sends a packet to all entities tracking the given entity
     *
     * @param msg    Packet
     * @param entity Entity to check
     */
    @Override
    public void sendToTracking(ILazrPacket msg, Entity entity) {
        var buffer = new FriendlyByteBuf(Unpooled.buffer());
        msg.encode(buffer);
        if (entity.level().getChunkSource() instanceof ServerChunkCache chunkCache)
            chunkCache.broadcast(entity, ServerPlayNetworking.createS2CPacket(packetChannels.get(msg.getClass()), buffer));
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