package com.lazrproductions.lazrslib.ui.element;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.screen.base.BlitCoordinates;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class HorizontalElement {
    final AbstractElement[] elements;
    final int height;

    public HorizontalElement(@Nonnull AbstractElement... elements) {
        this.elements = elements;
        this.height = calculateHeight(elements);
    }

    public int draw(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics, @Nonnull BlitCoordinates availableArea,
            int mouseX, int mouseY, boolean mouseDown) {
        availableArea = availableArea.withHeight(height);
        int widthPerElement = availableArea.getWidth() / elements.length;

        for (int i = 0; i < elements.length; i++) {
            elements[i].draw(instance, graphics, availableArea.move(widthPerElement * i, 0)
                    .withWidth(widthPerElement)
                    .withHeight(height).toRect(),
                    mouseX, mouseY, mouseDown);
        }

        return height;
    }

    public int drawDebug(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics,
            @Nonnull BlitCoordinates availableArea, int index) {
        availableArea = availableArea.withHeight(height);
        int widthPerElement = availableArea.getWidth() / elements.length;

        for (int i = 0; i < elements.length; i++) {
            elements[i].drawDebug(instance, graphics, availableArea.move(widthPerElement * i, 0)
                    .withWidth(widthPerElement)
                    .withHeight(height).toRect(),
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
