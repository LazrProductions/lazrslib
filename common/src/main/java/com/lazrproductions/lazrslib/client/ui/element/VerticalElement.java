package com.lazrproductions.lazrslib.client.ui.element;

import com.lazrproductions.lazrslib.client.screen.base.ScreenCoordinate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

public class VerticalElement {
    ScreenCoordinate pos;
    final HorizontalElement[] horizontals;
    final int height;
    final int spacing;

    public VerticalElement(@NotNull ScreenCoordinate availableArea, @NotNull HorizontalElement... horizontals) {
        this.pos = availableArea;
        this.horizontals = horizontals;
        this.height = calculateHeight();
        this.spacing = 0;
    }

    public VerticalElement(@NotNull ScreenCoordinate availableArea, int spacing,
                           @NotNull HorizontalElement... horizontals) {
        this.pos = availableArea;
        this.horizontals = horizontals;
        this.height = calculateHeight();
        this.spacing = spacing;
    }

    public int draw(@NotNull Minecraft instance, @NotNull GuiGraphics graphics, int mouseX, int mouseY,
            boolean mouseDown) {
        int spacing = 0;
        for (int i = 0; i < horizontals.length; i++)
            spacing += horizontals[i].draw(instance, graphics, pos.move(0, spacing), mouseX, mouseY, mouseDown)
                    + this.spacing;
        return spacing;
    }

    public int drawDebug(@NotNull Minecraft instance, @NotNull GuiGraphics graphics) {
        int spacing = 0;
        for (int i = 0; i < horizontals.length; i++)
            spacing += horizontals[i].drawDebug(instance, graphics, pos.move(0, spacing), i) + this.spacing;
        return spacing;
    }

    public void setAvailableArea(@NotNull ScreenCoordinate newArea) {
        this.pos = newArea;
    }

    int calculateHeight() {
        int spacing = 0;
        for (int i = 0; i < horizontals.length; i++)
            spacing += horizontals[i].calculateHeight(horizontals[i].elements);
        return spacing;
    }
}
