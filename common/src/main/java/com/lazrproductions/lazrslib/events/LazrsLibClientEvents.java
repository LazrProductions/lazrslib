package com.lazrproductions.lazrslib.events;

import com.lazrproductions.lazrslib.client.overlay.base.InteractableOverlay;
import com.lazrproductions.lazrslib.client.screen.base.GenericScreen;
import net.minecraft.client.Minecraft;

public class LazrsLibClientEvents {
    public static void onKeyInput(int key, int action, int modifiers) {
        Minecraft inst = Minecraft.getInstance();

        if (inst.screen instanceof GenericScreen sc) {
            sc.handleKeyAction(key, action);

            if (key == 256 && sc.shouldCloseOnEsc()) {
                sc.onClose();
                inst.setScreen(null);
            }
        }

        if (inst.getOverlay() instanceof InteractableOverlay io) {
            io.handleKeyAction(key, action);

            if (key == 256 && io.shouldCloseOnEsc()) {
                io.onClose();
                inst.setOverlay(null);
            }
        }
    }

    public static void onMouseInput(int button, int action, int modifiers) {
        Minecraft inst = Minecraft.getInstance();

        if (inst.screen instanceof GenericScreen sc)
            sc.handleMouseAction(button, action);

        if (inst.getOverlay() instanceof InteractableOverlay io)
            io.handleMouseAction(button, action);
    }

    public static void clientTick() {
        Minecraft inst = Minecraft.getInstance();
        if (inst.screen instanceof GenericScreen sc)
            sc.tick();
        if (inst.getOverlay() instanceof InteractableOverlay io)
            io.tick();
    }
}
