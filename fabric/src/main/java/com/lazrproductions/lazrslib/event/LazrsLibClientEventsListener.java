package com.lazrproductions.lazrslib.event;

import com.lazrproductions.lazrslib.client.overlay.base.InteractableOverlay;
import com.lazrproductions.lazrslib.client.screen.base.GenericScreen;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;

public class LazrsLibClientEventsListener {
    public static void onTickClient(Minecraft client) {
        if (client.screen instanceof GenericScreen screen)
            screen.tick();

        if (client.getOverlay() instanceof InteractableOverlay overlay)
            overlay.tick();
    }
}
