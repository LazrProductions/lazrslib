package com.lazrproductions.lazrslib.client.ui.element;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.client.screen.base.ScreenRect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class BoxElement extends AbstractElement {
    final int color;

    public BoxElement(@Nonnull Minecraft instance, int height, int color) {
        super(instance, height);
        this.color = color;
    }

    @Override
    public void draw(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics, @Nonnull ScreenRect area, int mouseX,
            int mouseY, boolean mouseDown) {
        graphics.fill(area.getFromX(), area.getFromY(), area.getToX(), area.getToY(), color);
    }
}
