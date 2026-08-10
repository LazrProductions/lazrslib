package com.lazrproductions.lazrslib.common.network.handling;

import com.lazrproductions.lazrslib.common.network.Side;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public record FabricPacketContext(Player player, Side side) implements IPacketContext {

    @Override
    @NotNull
    public Player player() {
        return player;
    }

    @Override
    @NotNull
    public Side side() {
        return side;
    }

    public void setHandled(boolean value) {
    }

    public boolean isHandled() {
        return false;
    }

    public void enqueueWork(Runnable runnable) {
        runnable.run();
    }
}
