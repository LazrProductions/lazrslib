package com.lazrproductions.lazrslib.common.network.handling;

import com.lazrproductions.lazrslib.common.network.Side;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public interface IPacketContext {
    @NotNull Player player();
    @NotNull Side side();

    void setHandled(boolean value);
    boolean isHandled();

    void enqueueWork(Runnable runnable);
}
