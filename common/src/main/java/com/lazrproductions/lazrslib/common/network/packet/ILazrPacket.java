package com.lazrproductions.lazrslib.common.network.packet;

import com.lazrproductions.lazrslib.common.network.handling.IPacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public interface ILazrPacket extends CustomPacketPayload {
    void setType(Type<? extends ILazrPacket> type);

    void encode(FriendlyByteBuf buffer);
    void handle(IPacketContext ctx);
}