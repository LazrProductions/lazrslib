package com.lazrproductions.lazrslib.common.network.handling;

import com.lazrproductions.lazrslib.LazrsLibClientMod;
import com.lazrproductions.lazrslib.common.network.Side;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import javax.annotation.Nonnull;

public class ForgePacketContext implements IPacketContext {

    final NetworkEvent.Context forgeContext;
    @Nonnull
    Player player;
    @Nonnull final Side side;

    public ForgePacketContext(NetworkEvent.Context forgeContext) {
        this.forgeContext = forgeContext;

        NetworkDirection dir = forgeContext.getDirection();
        if(dir == NetworkDirection.PLAY_TO_SERVER || dir == NetworkDirection.LOGIN_TO_SERVER) {
            side = Side.SERVER;
            var p = forgeContext.getSender();
            if(p != null)
                this.player = p;
            else
                throw new NullPointerException();
        }
        else {
            side = Side.CLIENT;
            this.player = DistExecutor.safeCallWhenOn(Dist.CLIENT, () -> LazrsLibClientMod::getLocalPlayer);
        }
    }

    @Nonnull
    public Player player() {
        return player;
    }

    @Nonnull
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