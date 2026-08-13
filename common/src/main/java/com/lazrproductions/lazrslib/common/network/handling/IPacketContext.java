package com.lazrproductions.lazrslib.common.network.handling;

import com.lazrproductions.lazrslib.common.network.Side;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nonnull;

public interface IPacketContext {
    @Nonnull Player player();
    @Nonnull Side side();

    void setHandled(boolean value);
    boolean isHandled();

    void enqueueWork(Runnable runnable);
}
