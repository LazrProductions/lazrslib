package com.lazrproductions.lazrslib.common.network.packet;

import com.lazrproductions.lazrslib.common.network.base.ILazrPacket;

import net.minecraftforge.event.network.CustomPayloadEvent;

public interface ThreadsafeLazrPacket extends ILazrPacket {

    @Override
    default void handle(CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> handleThreadsafe(ctx));
        ctx.setPacketHandled(true);
    }

    void handleThreadsafe(CustomPayloadEvent.Context ctx);
}
