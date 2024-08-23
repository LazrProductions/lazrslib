package com.lazrproductions.lazrslib.common.network.base;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

public interface ILazrPacket {

  void encode(FriendlyByteBuf buffer);

  void handle(CustomPayloadEvent.Context ctx);
}