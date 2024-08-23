package com.lazrproductions.lazrslib.common.network.packet;

import java.util.function.Supplier;

import com.lazrproductions.lazrslib.common.network.base.ILazrPacket;

import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;

public interface ThreadsafeLazrPacket extends ILazrPacket {

    @Override
    default void handle(Supplier<Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> handleThreadsafe(ctx));
        ctx.setPacketHandled(true);
    }

    void handleThreadsafe(NetworkEvent.Context ctx);
}
