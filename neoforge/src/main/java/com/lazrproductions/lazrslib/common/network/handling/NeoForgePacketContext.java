package com.lazrproductions.lazrslib.common.network.handling;

import com.lazrproductions.lazrslib.common.network.Side;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.ClientPayloadContext;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.network.handling.ServerPayloadContext;
import org.jetbrains.annotations.NotNull;

public class NeoForgePacketContext implements IPacketContext {
    final IPayloadContext neoforgeContext;
    final Player player;
    final Side side;

    public NeoForgePacketContext(IPayloadContext neoforgeContext) {
        this.neoforgeContext = neoforgeContext;
        this.player = neoforgeContext.player();
        if (neoforgeContext.flow() == PacketFlow.CLIENTBOUND)
            this.side = Side.CLIENT;
        else
            this.side = Side.SERVER;
    }

    @NotNull
    public Player player() {
        return player;
    }

    @NotNull
    public Side side() {
        return side;
    }

    public void setHandled(boolean value) { }

    public boolean isHandled() { return false; }

    public void enqueueWork(Runnable runnable) {
        neoforgeContext.enqueueWork(runnable);
    }
}
