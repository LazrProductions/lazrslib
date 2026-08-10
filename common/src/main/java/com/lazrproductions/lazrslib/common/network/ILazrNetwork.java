package com.lazrproductions.lazrslib.common.network;

import com.lazrproductions.lazrslib.common.network.packet.ILazrPacket;
import net.minecraft.network.protocol.Packet;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public interface ILazrNetwork {
    void sendToServer(ILazrPacket msg);
    void sendToClient(ILazrPacket msg, Player player);
    void sendToClient(ILazrPacket msg, ServerPlayer player);
    void sendToTrackingAndSelf(ILazrPacket msg, Entity entity);
    void sendToTracking(ILazrPacket msg, Entity entity);
    void sendVanillaPacket(Packet<?> packet, Entity player);
}
