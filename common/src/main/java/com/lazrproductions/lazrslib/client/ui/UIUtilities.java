package com.lazrproductions.lazrslib.client.ui;

import com.lazrproductions.lazrslib.client.ui.element.VerticalElement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

/**
 * A class containing utilities for drawing UI to the screen.
 */
public class UIUtilities {
    public static boolean DRAW_DEBUG_WIDGETS = false;

    /**
     * Draw a page containing the given {@link VerticalElement}
     */
    public static void drawPage(@NotNull Minecraft instance, @NotNull GuiGraphics graphics, int mouseX, int mouseY,
                                boolean mouseDown, VerticalElement verticals) {
        if (DRAW_DEBUG_WIDGETS)
            verticals.drawDebug(instance, graphics);
        verticals.draw(instance, graphics, mouseX, mouseY, mouseDown);
    }
}
