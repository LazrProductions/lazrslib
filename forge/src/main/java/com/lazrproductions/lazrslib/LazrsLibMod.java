package com.lazrproductions.lazrslib;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod(LazrsLibConstants.MOD_ID)
public class LazrsLibMod {
    
    public LazrsLibMod() {
        DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> LazrsLibClientMod::new);
        var context = ModLoadingContext.get();
    }
}