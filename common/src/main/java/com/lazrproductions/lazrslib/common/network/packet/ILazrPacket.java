package com.lazrproductions.lazrslib.common.network.packet;

import com.lazrproductions.lazrslib.common.network.handling.IPacketContext;
import net.minecraft.network.FriendlyByteBuf;

public interface ILazrPacket {
    void encode(FriendlyByteBuf buffer);
    void handle(IPacketContext ctx);
}