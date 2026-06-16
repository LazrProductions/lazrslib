package com.lazrproductions.lazrslib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

@Mod(LazrsLibMod.MODID)
public class LazrsLibMod {
    public static final Logger LOGGER = LogManager.getLogger(LazrsLibMod.MODID);
    public static final String MODID = "lazrslib";

    public LazrsLibMod(IEventBus modEventBus) {
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
    }
}