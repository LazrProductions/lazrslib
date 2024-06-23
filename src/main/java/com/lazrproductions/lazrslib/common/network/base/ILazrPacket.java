package com.lazrproductions.lazrslib.common.network.base;

import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

public interface ILazrPacket {

  void encode(FriendlyByteBuf buffer);

  void read(FriendlyByteBuf buffer);

  void handle(Supplier<NetworkEvent.Context> context);
}