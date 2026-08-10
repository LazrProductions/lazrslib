package com.lazrproductions.lazrslib.common.network.handling;

import com.lazrproductions.lazrslib.LazrsLibClientMod;
import com.lazrproductions.lazrslib.common.network.Side;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.fml.DistExecutor;
import org.jetbrains.annotations.NotNull;

public class ForgePacketContext implements IPacketContext {

    final CustomPayloadEvent.Context forgeContext;
    @NotNull Player player;
    @NotNull final Side side;

    public ForgePacketContext(CustomPayloadEvent.Context forgeContext) {
        this.forgeContext = forgeContext;

        if(forgeContext.isServerSide()) {
            var p = forgeContext.getSender();
            if(p != null)
                this.player = p;
            else
                throw new NullPointerException();
        }
        else
            this.player = DistExecutor.safeCallWhenOn(Dist.CLIENT, () -> LazrsLibClientMod::getLocalPlayer);

        if(forgeContext.isServerSide())
            side = Side.SERVER;
        else
            side = Side.CLIENT;
    }

    @NotNull
    public Player player() {
        return player;
    }

    @NotNull
    public Side side() {
        return side;
    }

    public void setHandled(boolean value) {
        forgeContext.setPacketHandled(value);
    }

    public boolean isHandled() {
        return forgeContext.getPacketHandled();
    }

    public void enqueueWork(Runnable runnable) {
        forgeContext.enqueueWork(runnable);
    }
}