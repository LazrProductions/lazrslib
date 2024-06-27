package com.lazrproductions.lazrslib.common.network;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.lazrproductions.lazrslib.common.network.base.ILazrPacket;

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
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class LazrNetwork {
    final SimpleChannel network;
    final String version;
    private int id = 0;

    public LazrNetwork(ResourceLocation location, int version) {
        String v = String.valueOf(version);
        this.version = v;
        this.network = NetworkRegistry.ChannelBuilder.named(location)
                .clientAcceptedVersions(this.version::equals)
                .serverAcceptedVersions(this.version::equals)
                .networkProtocolVersion(() -> this.version)
                .simpleChannel();
    }

    public <T extends ILazrPacket> void registerClientBoundPacket(Class<T> c, Function<FriendlyByteBuf, T> decoder) {
        registerPacket(c, ILazrPacket::encode, decoder, ILazrPacket::handle, NetworkDirection.PLAY_TO_CLIENT);
    }
    public <T extends ILazrPacket> void registerServerBoundPacket(Class<T> c, Function<FriendlyByteBuf, T> decoder) {
        registerPacket(c, ILazrPacket::encode, decoder, ILazrPacket::handle, NetworkDirection.PLAY_TO_SERVER);
    }
    public <T extends ILazrPacket> void registerPacket(Class<T> c, Function<FriendlyByteBuf, T> decoder) {
        registerPacket(c, ILazrPacket::encode, decoder, ILazrPacket::handle, null);
    }
    

    public <T extends ILazrPacket> void registerPacket(Class<T> c, Function<FriendlyByteBuf, T> decoder,
            @Nullable NetworkDirection direction) {
        registerPacket(c, ILazrPacket::encode, decoder, ILazrPacket::handle, direction);
    }

    public <T extends ILazrPacket> void registerPacket(Class<T> c, BiConsumer<T, FriendlyByteBuf> encoder,
            Function<FriendlyByteBuf, T> decoder, BiConsumer<T, Supplier<NetworkEvent.Context>> consumer,
            @Nullable NetworkDirection direction) {
        this.network.registerMessage(this.id++, c, encoder, decoder, consumer, Optional.ofNullable(direction));
    }


    /* Sending packets */

    /**
     * Sends a packet to the server
     *
     * @param msg Packet to send
     */
    public void sendToServer(Object msg) {
        this.network.sendToServer(msg);
    }

    /**
     * Sends a packet to the given packet distributor
     *
     * @param target  Packet target
     * @param message Packet to send
     */
    public void send(PacketDistributor.PacketTarget target, Object message) {
        network.send(target, message);
    }

    /**
     * Sends a vanilla packet to the given entity
     *
     * @param player Player receiving the packet
     * @param packet Packet
     */
    public void sendVanillaPacket(Packet<?> packet, Entity player) {
        if (player instanceof ServerPlayer && ((ServerPlayer) player).connection != null) {
            ((ServerPlayer) player).connection.send(packet);
        }
    }

    /**
     * Sends a packet to a player
     *
     * @param msg    Packet
     * @param player Player to send
     */
    public void sendTo(Object msg, Player player) {
        if (player instanceof ServerPlayer) {
            sendTo(msg, (ServerPlayer) player);
        }
    }

    /**
     * Sends a packet to a player
     *
     * @param msg    Packet
     * @param player Player to send
     */
    public void sendTo(Object msg, ServerPlayer player) {
        if (!(player instanceof FakePlayer)) {
            network.sendTo(msg, player.connection.connection, NetworkDirection.PLAY_TO_CLIENT);
        }
    }

    /**
     * Sends a packet to players near a location
     *
     * @param msg         Packet to send
     * @param serverWorld World instance
     * @param position    Position within range
     */
    public void sendToClientsAround(Object msg, ServerLevel serverWorld, BlockPos position) {
        LevelChunk chunk = serverWorld.getChunkAt(position);
        network.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), msg);
    }

    /**
     * Sends a packet to all entities tracking the given entity
     *
     * @param msg    Packet
     * @param entity Entity to check
     */
    public void sendToTrackingAndSelf(Object msg, Entity entity) {
        this.network.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> entity), msg);
    }

    /**
     * Sends a packet to all entities tracking the given entity
     *
     * @param msg    Packet
     * @param entity Entity to check
     */
    public void sendToTracking(Object msg, Entity entity) {
        this.network.send(PacketDistributor.TRACKING_ENTITY.with(() -> entity), msg);
    }

    /**
     * Same as {@link #sendToClientsAround(Object, ServerLevel, BlockPos)}, but
     * checks that the world is a level accessor
     *
     * @param msg      Packet to send
     * @param world    World instance
     * @param position Target position
     */
    public void sendToClientsAround(Object msg, @Nullable LevelAccessor world, BlockPos position) {
        if (world instanceof ServerLevel) {
            sendToClientsAround(msg, (ServerLevel) world, position);
        }
    }
}
