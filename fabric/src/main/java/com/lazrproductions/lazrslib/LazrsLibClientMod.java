package com.lazrproductions.lazrslib;

import com.lazrproductions.lazrslib.event.LazrsLibClientEventsListener;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class LazrsLibClientMod implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(LazrsLibClientEventsListener::onTickClient);
    }
}
