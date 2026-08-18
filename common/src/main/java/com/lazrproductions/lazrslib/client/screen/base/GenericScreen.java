package com.lazrproductions.lazrslib.client.screen.base;

import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;

public abstract class GenericScreen extends Screen {

    Window window;
    int width;
    int height;

    int mouseX, mouseY;

    protected int tick;

    protected @Nonnull InputAction lastKeyInput = InputAction.NONE;
    protected @Nonnull InputAction lastMouseInput = InputAction.NONE;

    protected GenericScreen(Component title) {
        super(title);
    }
    public GenericScreen(@Nonnull Minecraft instance) {
        super(Component.literal(""));
        this.minecraft = instance;
        this.window = minecraft.getWindow();
        this.width = window.getWidth();
        this.height = window.getHeight();
    }

    @Override
    public void render(@Nonnull GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        super.render(graphics, mouseX, mouseY, partialTick);

        this.mouseX = mouseX;
        this.mouseY = mouseY;
        width = window.getGuiScaledWidth();
        height = window.getGuiScaledHeight();
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
}
