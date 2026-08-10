package com.lazrproductions.lazrslib.client.ui.element;

import com.lazrproductions.lazrslib.client.screen.base.ScreenRect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

/**
 * A simple box element that fills its area with the given color.
 */
public class BoxElement extends AbstractElement {
    final int color;

    public BoxElement(@NotNull Minecraft instance, int height, int color) {
        super(instance, height);
        this.color = color;
    }

    @Override
    public void draw(@NotNull Minecraft instance, @NotNull GuiGraphics graphics, @NotNull ScreenRect area, int mouseX,
            int mouseY, boolean mouseDown) {
        graphics.fill(area.getFromX(), area.getFromY(), area.getToX(), area.getToY(), color);
    }
}
