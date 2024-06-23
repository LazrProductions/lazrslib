package com.lazrproductions.lazrslib.client.overlay.base;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.client.gui.GuiGraphics;
import com.lazrproductions.lazrslib.client.screen.base.InputAction;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Overlay;

public class InteractableOverlay extends Overlay {
    protected final GuiGraphics graphics;

    final boolean lockView;
    final boolean showCroshair;

    public InteractableOverlay (@Nonnull Minecraft inst, boolean lockView, boolean showCroshair) {
        minecraft = inst;
        window = minecraft.getWindow();
        width = window.getWidth();
        height = window.getHeight();
    
        this.lockView = lockView;
        this.showCroshair = showCroshair;

        this.graphics = GuiGraphics.from(inst);
    }

    Minecraft minecraft;
    Window window;
    int width;
    int height;

    int mouseX, mouseY;

    protected int tick;

    protected InputAction lastKeyInput = InputAction.NONE;
    protected InputAction lastMouseInput = InputAction.NONE;

    @Override
    public void render(@Nonnull PoseStack graphics, int mouseX, int mouseY, float partialTick) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        width = window.getGuiScaledWidth();
        height = window.getGuiScaledHeight();

        lastKeyInput = InputAction.NONE;
        lastMouseInput = InputAction.NONE;
    }

    public void tick() {
        tick++;
    }

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
