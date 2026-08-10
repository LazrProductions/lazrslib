package com.lazrproductions.lazrslib.events;

import com.lazrproductions.lazrslib.LazrsLibConstants;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LazrsLibConstants.MOD_ID, value = Dist.CLIENT)
public class LazrsLibClientEventsListener {
    @SubscribeEvent
    public void onKeyInput(InputEvent.Key event) {
        LazrsLibClientEvents.onKeyInput(event.getKey(), event.getAction(), event.getModifiers());
    }

    @SubscribeEvent
    public void onMouseInput(InputEvent.MouseButton.Post event) {
        LazrsLibClientEvents.onMouseInput(event.getButton(), event.getAction(), event.getModifiers());
    }

    @SubscribeEvent
    public void clientTick(TickEvent.ClientTickEvent.Post event) {
        LazrsLibClientEvents.clientTick();
    }
}
