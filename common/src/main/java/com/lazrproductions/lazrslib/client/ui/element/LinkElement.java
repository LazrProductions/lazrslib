package com.lazrproductions.lazrslib.client.ui.element;

import com.lazrproductions.lazrslib.client.font.FontUtilities;
import com.lazrproductions.lazrslib.client.screen.base.ScreenRect;
import com.lazrproductions.lazrslib.client.ui.Alignment;
import com.lazrproductions.lazrslib.client.ui.IOnClickFunction;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class LinkElement extends AbstractElement {

    final Component text;
    final int color;
    final int highlightedColor;
    final Alignment alignText;
    final IOnClickFunction supplier;

    public LinkElement(@NotNull Minecraft instance, @NotNull Component text, Alignment textAlignment,
                       IOnClickFunction supplier, int height, int color, int highlightedColor) {
        super(instance, height);
        this.text = text;
        this.alignText = textAlignment;
        this.color = color;
        this.highlightedColor = highlightedColor;
        this.supplier = supplier;
    }

    public LinkElement(@NotNull Minecraft instance, int width, @NotNull Component text, Alignment textAlignment,
                       IOnClickFunction supplier, int color, int highlightedColor) {
        super(instance, instance.font.wordWrapHeight(text, width));
        this.text = text;
        this.alignText = textAlignment;
        this.color = color;
        this.highlightedColor = highlightedColor;
        this.supplier = supplier;
    }

    @Override
    public void draw(@NotNull Minecraft instance, @NotNull GuiGraphics graphics, @NotNull ScreenRect area, int mouseX,
            int mouseY, boolean mouseDown) {
        int textWidth = instance.font.width(text);
        int textHeight = instance.font.lineHeight;
        if (FontUtilities.drawLink(instance.font, graphics,
                alignText.fitToArea(area.toScreenCoordinate(), textWidth, textHeight), text, color, highlightedColor,
                false, mouseX, mouseY, mouseDown))
            supplier.call();
    }
}