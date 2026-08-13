package com.lazrproductions.lazrslib.client.ui.element;

import com.lazrproductions.lazrslib.client.screen.base.ScreenRect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import javax.annotation.Nonnull;

/**
 * The base class for a renderable UI element
 */
public abstract class AbstractElement {
    final int fixedHeight;

    public AbstractElement(@Nonnull Minecraft instance, int height) {
        this.fixedHeight = height;
    }

    /**
     * Get the height of this element.
     * @return The height of this element.
     */
    public int getFixedHeight() {
        return fixedHeight;
    }

    /**
     * Render this element to the screen
     * @param area The generated {@link ScreenRect} that this element occupies.
     */
    public abstract void draw(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics, @Nonnull ScreenRect area,
            int mouseX, int mouseY, boolean mouseDown);

    /**
     * render debug lines for this element.
     * @param area The generated {@link ScreenRect} that this element occupies.
     */
    public void drawDebug(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics, @Nonnull ScreenRect area, int index) {
        graphics.fill(area.getFromX(), area.getFromY(), area.getToX(), area.getToY(),
                index % 2 == 0 ? 0xFFFF0000 : 0xFF0009FF);

        graphics.fill(area.getFromX() - 10, area.getFromY(), area.getToX() + 10, area.getFromY() + 1, 0xFF000000);
    }
}
