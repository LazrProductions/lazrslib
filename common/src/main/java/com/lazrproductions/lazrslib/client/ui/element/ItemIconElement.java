package com.lazrproductions.lazrslib.client.ui.element;

import com.lazrproductions.lazrslib.client.screen.ScreenUtilities;
import com.lazrproductions.lazrslib.client.screen.base.ScreenCoordinate;
import com.lazrproductions.lazrslib.client.screen.base.ScreenRect;
import com.lazrproductions.lazrslib.client.ui.Alignment;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemIconElement extends AbstractElement {
    final ItemStack stack;
    final Alignment alignment;

    public ItemIconElement(Minecraft instance, ItemStack stack, Alignment alignment, int height) {
        super(instance, height);
        this.stack = stack;
        this.alignment = alignment;
    }

    @Override
    public void draw(@NotNull Minecraft instance, @NotNull GuiGraphics graphics, @NotNull ScreenRect area, int mouseX,
                     int mouseY, boolean mouseDown) {
        ScreenCoordinate pos = alignment.fitToArea(area.toScreenCoordinate(), area.getHeight(), area.getHeight());
        ScreenUtilities.drawItemStack(instance, graphics, stack, pos.getX(), pos.getY(), pos.getHeight());
    }
}
