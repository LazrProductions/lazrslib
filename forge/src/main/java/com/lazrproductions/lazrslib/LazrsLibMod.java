package com.lazrproductions.lazrslib;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(LazrsLibConstants.MOD_ID)
public class LazrsLibMod {
    public LazrsLibMod(FMLJavaModLoadingContext context) {
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> LazrsLibClientMod::new);
    }
}