package com.lazrproductions.lazrslib.common.network;


import com.lazrproductions.lazrslib.common.network.handling.ForgePacketContext;
import com.lazrproductions.lazrslib.common.network.packet.ILazrPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Function;

public class LazrNetwork implements ILazrNetwork {
    final String modId;
    final SimpleChannel network;
    final String version;
    private int id = 0;

    public LazrNetwork(ResourceLocation location, int version) {
        this.modId = location.getNamespace();
        this.version = String.valueOf(version);
        this.network = NetworkRegistry.ChannelBuilder.named(location)
                .clientAcceptedVersions(this.version::equals)
                .serverAcceptedVersions(this.version::equals)
                .networkProtocolVersion(() -> this.version)
                .simpleChannel();
    }

    public <T extends ILazrPacket> void registerPacket(Class<T> classType, Function<FriendlyByteBuf, T> decoder) {
        network.messageBuilder(classType, getNextId())
                .encoder(ILazrPacket::encode)
                .decoder(decoder)
                .consumerNetworkThread((a, b) -> {
                    ForgePacketContext forgeContext = new ForgePacketContext(b.get());
                    a.handle(forgeContext);
                }).add();
    }

    private int getNextId() {
        return id++;
    }

    /**
     * Sends a packet to the server
     *
     * @param msg Packet to send
     */
    @Override
    public void sendToServer(ILazrPacket msg) {
        this.network.sendToServer(msg);
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
        network.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
    }

    /**
     * Sends a packet to all entities tracking the given entity
     *
     * @param msg    Packet
     * @param entity Entity to check
     */
    @Override
    public void sendToTrackingAndSelf(ILazrPacket msg, Entity entity) {
        this.network.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), msg);
    }

    /**
     * Sends a packet to all entities tracking the given entity
     *
     * @param msg    Packet
     * @param entity Entity to check
     */
    @Override
    public void sendToTracking(ILazrPacket msg, Entity entity) {
        this.network.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), msg);
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