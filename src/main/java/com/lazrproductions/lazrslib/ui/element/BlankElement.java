package com.lazrproductions.lazrslib.ui.element;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.screen.base.ScreenRect;

import net.minecraft.client.Minecraft;
import com.lazrproductions.lazrslib.gui.GuiGraphics;

public class BlankElement extends AbstractElement {
    public BlankElement(@Nonnull Minecraft instance, int height) {
        super(instance, height);
    }

    @Override
    public void draw(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics, @Nonnull ScreenRect area, int mouseX,
            int mouseY, boolean mouseDown) {
    }
}
