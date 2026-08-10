package com.lazrproductions.lazrslib;

import com.lazrproductions.lazrslib.events.LazrsLibClientEventsListener;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;

public class LazrsLibClientMod {
    public LazrsLibClientMod() {
        MinecraftForge.EVENT_BUS.register(new LazrsLibClientEventsListener());
    }

    @OnlyIn(Dist.CLIENT)
    public static Player getLocalPlayer() {
        return Minecraft.getInstance().player;
    }
}
