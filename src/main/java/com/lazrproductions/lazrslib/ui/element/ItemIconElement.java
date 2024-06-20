package com.lazrproductions.lazrslib.ui.element;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.screen.ScreenUtilities;
import com.lazrproductions.lazrslib.screen.base.BlitCoordinates;
import com.lazrproductions.lazrslib.screen.base.ScreenRect;
import com.lazrproductions.lazrslib.ui.Alignment;

import net.minecraft.client.Minecraft;
import com.lazrproductions.lazrslib.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;

public class ItemIconElement extends AbstractElement {

            final ItemStack stack;
            final Alignment alignment;

            public ItemIconElement(Minecraft instance, ItemStack stack, Alignment alignment, int height) {
                super(instance, height);
                this.stack = stack;
                this.alignment = alignment;
            }

            @Override
            public void draw(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics, @Nonnull ScreenRect area, int mouseX,
                    int mouseY, boolean mouseDown) {
                BlitCoordinates pos = alignment.fitToArea(area.toBlitCoordinates(), area.getHeight(), area.getHeight());
                ScreenUtilities.drawItemStack(instance, graphics, stack, pos.getX(), pos.getY(), pos.getHeight());
            }
}
