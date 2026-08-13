package com.lazrproductions.lazrslib.client.ui.element;

import com.lazrproductions.lazrslib.client.screen.base.ScreenCoordinate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import javax.annotation.Nonnull;

public class VerticalElement {
    ScreenCoordinate pos;
    final HorizontalElement[] horizontals;
    final int height;
    final int spacing;

    public VerticalElement(@Nonnull ScreenCoordinate availableArea, @Nonnull HorizontalElement... horizontals) {
        this.pos = availableArea;
        this.horizontals = horizontals;
        this.height = calculateHeight();
        this.spacing = 0;
    }

    public VerticalElement(@Nonnull ScreenCoordinate availableArea, int spacing,
                           @Nonnull HorizontalElement... horizontals) {
        this.pos = availableArea;
        this.horizontals = horizontals;
        this.height = calculateHeight();
        this.spacing = spacing;
    }

    public int draw(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics, int mouseX, int mouseY,
            boolean mouseDown) {
        int spacing = 0;
        for (HorizontalElement horizontal : horizontals)
            spacing += horizontal.draw(instance, graphics, pos.move(0, spacing), mouseX, mouseY, mouseDown)
                    + this.spacing;
        return spacing;
    }

    public int drawDebug(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics) {
        int spacing = 0;
        for (int i = 0; i < horizontals.length; i++)
            spacing += horizontals[i].drawDebug(instance, graphics, pos.move(0, spacing), i) + this.spacing;
        return spacing;
    }

    public void setAvailableArea(@Nonnull ScreenCoordinate newArea) {
        this.pos = newArea;
    }

    int calculateHeight() {
        int spacing = 0;
        for (HorizontalElement horizontal : horizontals)
            spacing += horizontal.calculateHeight(horizontal.elements);
        return spacing;
    }
}
