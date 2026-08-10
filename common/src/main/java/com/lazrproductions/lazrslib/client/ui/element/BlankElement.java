package com.lazrproductions.lazrslib.client.ui.element;

import com.lazrproductions.lazrslib.client.screen.base.ScreenRect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

/**
 * A simple black element, acts as an empty space.
 */
public class BlankElement extends AbstractElement {
    public BlankElement(@NotNull Minecraft instance, int height) {
        super(instance, height);
    }

    @Override
    public void draw(@NotNull Minecraft instance, @NotNull GuiGraphics graphics, @NotNull ScreenRect area, int mouseX,
            int mouseY, boolean mouseDown) {
    }
}
