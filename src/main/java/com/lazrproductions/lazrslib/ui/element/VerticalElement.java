package com.lazrproductions.lazrslib.ui.element;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.gui.GuiGraphics;
import com.lazrproductions.lazrslib.screen.base.BlitCoordinates;

import net.minecraft.client.Minecraft;

public class VerticalElement {
    BlitCoordinates pos;
    final HorizontalElement[] horizontals;
    final int height;
    final int spacing;

    public VerticalElement(@Nonnull BlitCoordinates availableArea, @Nonnull HorizontalElement... horizontals) {
        this.pos = availableArea;
        this.horizontals = horizontals;
        this.height = calculateHeight();
        this.spacing = 0;
    }

    public VerticalElement(@Nonnull BlitCoordinates availableArea, int spacing,
            @Nonnull HorizontalElement... horizontals) {
        this.pos = availableArea;
        this.horizontals = horizontals;
        this.height = calculateHeight();
        this.spacing = spacing;
    }

    public int draw(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics, int mouseX, int mouseY,
            boolean mouseDown) {
        int spacing = 0;
        for (int i = 0; i < horizontals.length; i++)
            spacing += horizontals[i].draw(instance, graphics, pos.move(0, spacing), mouseX, mouseY, mouseDown)
                    + this.spacing;
        return spacing;
    }

    public int drawDebug(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics) {
        int spacing = 0;
        for (int i = 0; i < horizontals.length; i++)
            spacing += horizontals[i].drawDebug(instance, graphics, pos.move(0, spacing), i) + this.spacing;
        return spacing;
    }

    public void setAvailableArea(@Nonnull BlitCoordinates newArea) {
        this.pos = newArea;
    }

    int calculateHeight() {
        int spacing = 0;
        for (int i = 0; i < horizontals.length; i++)
            spacing += horizontals[i].calculateHeight(horizontals[i].elements);
        return spacing;
    }
}
