package com.lazrproductions.lazrslib.client.ui.element;

import com.lazrproductions.lazrslib.client.screen.base.ScreenCoordinate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

public class HorizontalElement {
    final AbstractElement[] elements;
    final int height;

    public HorizontalElement(@NotNull AbstractElement... elements) {
        this.elements = elements;
        this.height = calculateHeight(elements);
    }

    public int draw(@NotNull Minecraft instance, @NotNull GuiGraphics graphics, @NotNull ScreenCoordinate availableArea,
                    int mouseX, int mouseY, boolean mouseDown) {
        availableArea = availableArea.withHeight(height);
        int widthPerElement = availableArea.getWidth() / elements.length;

        for (int i = 0; i < elements.length; i++) {
            elements[i].draw(instance, graphics, availableArea.move(widthPerElement * i, 0)
                    .withWidth(widthPerElement)
                    .withHeight(height).toScreenRect(),
                    mouseX, mouseY, mouseDown);
        }

        return height;
    }

    public int drawDebug(@NotNull Minecraft instance, @NotNull GuiGraphics graphics,
                         @NotNull ScreenCoordinate availableArea, int index) {
        availableArea = availableArea.withHeight(height);
        int widthPerElement = availableArea.getWidth() / elements.length;

        for (int i = 0; i < elements.length; i++) {
            elements[i].drawDebug(instance, graphics, availableArea.move(widthPerElement * i, 0)
                    .withWidth(widthPerElement)
                    .withHeight(height).toScreenRect(),
                    i);
        }

        return height;
    }

    public int calculateHeight(AbstractElement[] elements) {
        int maxHeight = 0;
        for (AbstractElement element : elements) {
            int height = element.getFixedHeight();
            if (height > maxHeight)
                maxHeight = height;
        }
        return maxHeight;
    }
}