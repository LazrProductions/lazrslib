package com.lazrproductions.lazrslib.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;

public class LazrsLibClientEventsListener {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        LazrsLibClientEvents.onKeyInput(event.getKey(), event.getAction(), event.getModifiers());
    }

    @SubscribeEvent
    public static void onMouseInput(InputEvent.MouseButton.Post event) {
        LazrsLibClientEvents.onMouseInput(event.getButton(), event.getAction(), event.getModifiers());
    }

    @SubscribeEvent
    public static void clientTick(ClientTickEvent.Post event) {
        LazrsLibClientEvents.clientTick();
    }
}
