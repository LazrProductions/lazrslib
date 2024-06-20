package com.lazrproductions.lazrslib.screen.base;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.gui.GuiGraphics;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class GenericScreen extends Screen {
    protected final GuiGraphics graphics;
    
    Window window;
    int width;
    int height;

    int mouseX, mouseY;

    protected int tick;

    protected InputAction lastKeyInput = InputAction.NONE;
    protected InputAction lastMouseInput = InputAction.NONE;

    protected GenericScreen(Component title) {
        super(title);

        graphics = GuiGraphics.from(Minecraft.getInstance());
    }
    public GenericScreen(@Nonnull Minecraft instance) {
        super(Component.literal(""));
        this.minecraft = instance;
        this.window = minecraft.getWindow();
        this.width = window.getWidth();
        this.height = window.getHeight();
        
        graphics = GuiGraphics.from(instance);
    }

    @Override
    public void render(@Nonnull PoseStack stack, int mouseX, int mouseY, float partialTick) {
        super.render(stack, mouseX, mouseY, partialTick);

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

    public void onClose() {

    }
}