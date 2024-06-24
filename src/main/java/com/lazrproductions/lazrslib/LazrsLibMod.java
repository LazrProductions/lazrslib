package com.lazrproductions.lazrslib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.lazrproductions.lazrslib.event.ModServerEvents;
import com.lazrproductions.lazrslib.testing.TestingAPI;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value e should match an entry in the META-INF/mods.toml file
@Mod(LazrsLibMod.MODID)
public class LazrsLibMod {
    public static final Logger LOGGER = LogManager.getLogger(LazrsLibMod.MODID);
    public static final String MODID = "lazrslib";


    public LazrsLibMod() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);
        
        TestingAPI.registerPackets();

        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new ModServerEvents());
    }
}