package com.lazrproductions.lazrslib.event;

import com.lazrproductions.lazrslib.LazrsLibMod;
import com.lazrproductions.lazrslib.overlay.base.InteractableOverlay;
import com.lazrproductions.lazrslib.screen.base.GenericScreen;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.event.TickEvent.Phase;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LazrsLibMod.MODID, value = Dist.CLIENT)
public class ModClientBusEvents {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft inst = Minecraft.getInstance();

        if (inst != null) {
            if (inst.screen instanceof GenericScreen sc) {
                sc.handleKeyAction(event.getKey(), event.getAction());

                if (event.getKey() == 256) {
                    sc.onClose();
                    inst.setScreen(null);
                }
            }

            if (inst.getOverlay() instanceof InteractableOverlay io) {
                io.handleKeyAction(event.getKey(), event.getAction());

                if (event.getKey() == 256) {
                    io.onClose();
                    inst.setOverlay(null);
                }
            }
        }
    }

    @SubscribeEvent
    public static void onMouseInput(InputEvent.MouseButton.Post event) {
        Minecraft inst = Minecraft.getInstance();

        if (inst != null) {
            if (inst.screen instanceof GenericScreen sc)
                sc.handleMouseAction(event.getButton(), event.getAction());

            if (inst.getOverlay() instanceof InteractableOverlay io)
                io.handleMouseAction(event.getButton(), event.getAction());
        }
    }

    @SubscribeEvent
    public void clientTick(ClientTickEvent event) {
        if (event.phase == Phase.END) {
            Minecraft inst = Minecraft.getInstance();
            if(inst != null) {
                if (inst.screen instanceof GenericScreen sc)
                    sc.tick();

                if (inst.getOverlay() instanceof InteractableOverlay io)
                    io.tick();
            }
        }
    }
}
