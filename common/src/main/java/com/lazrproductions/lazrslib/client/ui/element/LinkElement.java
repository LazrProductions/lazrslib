package com.lazrproductions.lazrslib.client.ui.element;

import com.lazrproductions.lazrslib.client.font.FontUtilities;
import com.lazrproductions.lazrslib.client.screen.base.ScreenRect;
import com.lazrproductions.lazrslib.client.ui.Alignment;
import com.lazrproductions.lazrslib.client.ui.IOnClickFunction;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;

public class LinkElement extends AbstractElement {

    final Component text;
    final int color;
    final int highlightedColor;
    final Alignment alignText;
    final IOnClickFunction supplier;

    public LinkElement(@Nonnull Minecraft instance, @Nonnull Component text, Alignment textAlignment,
                       IOnClickFunction supplier, int height, int color, int highlightedColor) {
        super(instance, height);
        this.text = text;
        this.alignText = textAlignment;
        this.color = color;
        this.highlightedColor = highlightedColor;
        this.supplier = supplier;
    }

    public LinkElement(@Nonnull Minecraft instance, int width, @Nonnull Component text, Alignment textAlignment,
                       IOnClickFunction supplier, int color, int highlightedColor) {
        super(instance, instance.font.wordWrapHeight(text, width));
        this.text = text;
        this.alignText = textAlignment;
        this.color = color;
        this.highlightedColor = highlightedColor;
        this.supplier = supplier;
    }

    @Override
    public void draw(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics, @Nonnull ScreenRect area, int mouseX,
            int mouseY, boolean mouseDown) {
        int textWidth = instance.font.width(text);
        int textHeight = instance.font.lineHeight;
        if (FontUtilities.drawLink(instance.font, graphics,
                alignText.fitToArea(area.toScreenCoordinate(), textWidth, textHeight), text, color, highlightedColor,
                false, mouseX, mouseY, mouseDown))
            supplier.call();
    }
}