package com.lazrproductions.lazrslib.screen.base;

import javax.annotation.Nonnull;

import com.mojang.blaze3d.platform.Window;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class GenericScreen extends Screen {

    Window window;
    int width;
    int height;

    int mouseX, mouseY;

    protected InputAction lastKeyInput = InputAction.NONE;
    protected InputAction lastMouseInput = InputAction.NONE;

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
        
        lastKeyInput = InputAction.NONE;
        lastMouseInput = InputAction.NONE;
    }

    public void tick() {
    }

    public void handleMouseAction(int mouseButton, int action) {
        lastMouseInput = new InputAction(mouseButton, action);
    } 
    
    public void handleKeyAction(int keyCode, int action) {
        lastKeyInput = new InputAction(keyCode, action);
    }

    public void onClose() {

    }
}