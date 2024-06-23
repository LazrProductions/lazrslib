package com.lazrproductions.lazrslib.client.ui.element;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.client.screen.base.ScreenRect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class BlankElement extends AbstractElement {
    public BlankElement(@Nonnull Minecraft instance, int height) {
        super(instance, height);
    }

    @Override
    public void draw(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics, @Nonnull ScreenRect area, int mouseX,
            int mouseY, boolean mouseDown) {
    }
}
