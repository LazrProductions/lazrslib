package com.lazrproductions.lazrslib.client.ui;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.client.ui.element.VerticalElement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class UIUtilities {
    public static boolean DRAW_DEBUG_WIDGETS = false;

    public static void drawPage(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics, int mouseX, int mouseY,
            boolean mouseDown, VerticalElement verticals) {
        if (DRAW_DEBUG_WIDGETS)
            verticals.drawDebug(instance, graphics);
        verticals.draw(instance, graphics, mouseX, mouseY, mouseDown);
    }
}
