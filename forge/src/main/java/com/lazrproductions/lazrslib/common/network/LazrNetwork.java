package com.lazrproductions.lazrslib.common.network;


import com.lazrproductions.lazrslib.LazrsLibConstants;
import com.lazrproductions.lazrslib.common.network.handling.ForgePacketContext;
import com.lazrproductions.lazrslib.common.network.packet.ILazrPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.*;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class LazrNetwork implements ILazrNetwork {
    final String modId;
    final SimpleChannel network;
    final int version;
    private int id = 0;

    public LazrNetwork(ResourceLocation location, int version) {
        this.modId = location.getNamespace();
        this.version = version;
        this.network = ChannelBuilder.named(location)
                .clientAcceptedVersions(Channel.VersionTest.exact(this.version))
                .serverAcceptedVersions(Channel.VersionTest.exact(this.version))
                .networkProtocolVersion(this.version)
                .simpleChannel();
    }

    public <T extends ILazrPacket> void registerPacket(Class<T> classType, Function<FriendlyByteBuf, T> decoder) {
        network.messageBuilder(classType, getNextId())
                .encoder(ILazrPacket::encode)
                .decoder(decoder)
                .consumerNetworkThread((a, b) -> {
                    ForgePacketContext forgeContext = new ForgePacketContext(b);
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
        this.network.send(msg, PacketDistributor.SERVER.noArg());
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
        network.send(msg, player.connection.getConnection());
    }

    /**
     * Sends a packet to all entities tracking the given entity
     *
     * @param msg    Packet
     * @param entity Entity to check
     */
    @Override
    public void sendToTrackingAndSelf(ILazrPacket msg, Entity entity) {
        this.network.send(msg, PacketDistributor.TRACKING_ENTITY_AND_SELF.with(entity));
    }

    /**
     * Sends a packet to all entities tracking the given entity
     *
     * @param msg    Packet
     * @param entity Entity to check
     */
    @Override
    public void sendToTracking(ILazrPacket msg, Entity entity) {
        this.network.send(msg, PacketDistributor.TRACKING_ENTITY.with(entity));
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