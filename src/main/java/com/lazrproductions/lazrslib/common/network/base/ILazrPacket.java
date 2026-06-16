package com.lazrproductions.lazrslib.common.network.base;

import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public interface ILazrPacket {

  void encode(FriendlyByteBuf buffer);

  void handle(IPayloadContext context);
}