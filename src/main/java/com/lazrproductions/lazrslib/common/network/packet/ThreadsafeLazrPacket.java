package com.lazrproductions.lazrslib.common.network.packet;

import com.lazrproductions.lazrslib.common.network.base.ILazrPacket;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public interface ThreadsafeLazrPacket extends ILazrPacket {

    @Override
    default void handle(IPayloadContext context) {
        context.enqueueWork(() -> handleThreadsafe(context));
    }

    void handleThreadsafe(IPayloadContext context);
}
