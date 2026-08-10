package com.lazrproductions.lazrslib;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLLoader;

@Mod(LazrsLibConstants.MOD_ID)
public class LazrsLibMod {
    public LazrsLibMod(IEventBus eventBus) {
        if (FMLLoader.getDist() == Dist.CLIENT)
            LazrsLibClientMod.init(eventBus);
    }
}