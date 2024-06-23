package com.lazrproductions.lazrslib.client.ui.element;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.client.screen.base.ScreenRect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public abstract class AbstractElement {
    final int fixedHeight;

    public AbstractElement(@Nonnull Minecraft instance, int height) {
        this.fixedHeight = height;
    }

    public int getFixedHeight() {
        return fixedHeight;
    }

    public abstract void draw(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics, @Nonnull ScreenRect area,
            int mouseX, int mouseY, boolean mouseDown);

    public void drawDebug(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics, @Nonnull ScreenRect area, int index) {
        graphics.fill(area.getFromX(), area.getFromY(), area.getToX(), area.getToY(),
                index % 2 == 0 ? 0xFFFF0000 : 0xFF0009FF);

        graphics.fill(area.getFromX() - 10, area.getFromY(), area.getToX() + 10, area.getFromY() + 1, 0xFF000000);
    }
}
