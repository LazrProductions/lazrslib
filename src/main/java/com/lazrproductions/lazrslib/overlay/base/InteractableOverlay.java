package com.lazrproductions.lazrslib.overlay.base;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.screen.base.InputAction;
import com.mojang.blaze3d.platform.Window;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Overlay;

public class InteractableOverlay extends Overlay {

    final boolean lockView;
    final boolean showCroshair;

    public InteractableOverlay (@Nonnull Minecraft inst, boolean lockView, boolean showCroshair) {
        minecraft = inst;
        window = minecraft.getWindow();
        width = window.getWidth();
        height = window.getHeight();
    
        this.lockView = lockView;
        this.showCroshair = showCroshair;
    }

    Minecraft minecraft;
    Window window;
    int width;
    int height;

    int mouseX, mouseY;

    protected InputAction lastKeyInput = InputAction.NONE;
    protected InputAction lastMouseInput = InputAction.NONE;

    @Override
    public void render(@Nonnull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        width = window.getGuiScaledWidth();
        height = window.getGuiScaledHeight();

        lastKeyInput = InputAction.NONE;
        lastMouseInput = InputAction.NONE;
    }

    public void tick() {}

    public void handleMouseAction(int mouseButton, int action) {
        lastMouseInput = new InputAction(mouseButton, action);
    } 
    public void handleKeyAction(int keyCode, int action) {
        lastKeyInput = new InputAction(keyCode, action);
    }

    public void onClose() {}

    public boolean getLockView() {
        return lockView;
    }
    public boolean getShowCroshair() {
        return showCroshair;
    }
}
