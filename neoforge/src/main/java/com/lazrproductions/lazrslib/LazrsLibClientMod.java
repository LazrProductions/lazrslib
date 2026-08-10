package com.lazrproductions.lazrslib;

import com.lazrproductions.lazrslib.events.LazrsLibClientEventsListener;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;

public class LazrsLibClientMod {
    public static void init(IEventBus eventBus) {
        eventBus.addListener(LazrsLibClientMod::clientSetup);
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        NeoForge.EVENT_BUS.register(LazrsLibClientEventsListener.class);
    }
}
