package com.lazrproductions.lazrslib.client.ui.element;

import java.util.List;

import com.lazrproductions.lazrslib.client.font.FontUtilities;
import com.lazrproductions.lazrslib.client.screen.base.ScreenCoordinate;
import com.lazrproductions.lazrslib.client.screen.base.ScreenRect;
import com.lazrproductions.lazrslib.client.ui.Alignment;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class LabelElement extends AbstractElement {

    final Component text;
    final Alignment alignment;
    final int width;
    final int color;

    public LabelElement(@NotNull Minecraft instance, @NotNull Component text, @NotNull Alignment alignment, int color) {
        super(instance, instance.font.lineHeight);
        this.text = text;
        this.alignment = alignment;
        this.color = color;
        
        this.width = 0;
    }
    public LabelElement(@NotNull Minecraft instance, @NotNull Component text, @NotNull Alignment alignment, int color, int height) {
        super(instance, height);
        this.text = text;
        this.alignment = alignment;
        this.color = color;

        this.width = 0;
    }
    public LabelElement(@NotNull Minecraft instance, @NotNull Component text, @NotNull Alignment alignment, int color, int width, int height) {
        super(instance, height);
        this.text = text;
        this.alignment = alignment;
        this.color = color;

        this.width = width;
    }

    @Override
    public void draw(@NotNull Minecraft instance, @NotNull GuiGraphics graphics, @NotNull ScreenRect area, int mouseX, int mouseY, boolean mouseDown) {
        int wrappedHeight = instance.font.wordWrapHeight(text, area.getWidth());
        ScreenCoordinate pos = alignment.fitToArea(area.toScreenCoordinate(), wrappedHeight <= instance.font.lineHeight ? instance.font.width(text) : area.getWidth(), wrappedHeight);

        if(width > 0)
            pos = alignment.fitToArea(area.toScreenCoordinate().withWidth(width), wrappedHeight <= instance.font.lineHeight ? instance.font.width(text) : width, wrappedHeight);


        FontUtilities.drawParagraph(instance.font, graphics,
            pos, 
            List.of(text), color, false);
    }
}
