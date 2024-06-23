package com.lazrproductions.lazrslib.client.ui.element;

import java.util.List;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.client.font.FontUtilities;
import com.lazrproductions.lazrslib.client.gui.GuiGraphics;
import com.lazrproductions.lazrslib.client.screen.base.BlitCoordinates;
import com.lazrproductions.lazrslib.client.screen.base.ScreenRect;
import com.lazrproductions.lazrslib.client.ui.Alignment;

import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class LabelElement extends AbstractElement {

    final Component text;
    final boolean renderShadow;
    final Alignment alignment;
    final int width;
    final int color;

    public LabelElement(@Nonnull Minecraft instance, @Nonnull Component text, @Nonnull Alignment alignment, int color) {
        super(instance, instance.font.lineHeight);
        this.text = text;
        this.alignment = alignment;

        this.color = color;
        this.renderShadow = true;
        
        this.width = 0;
    }
    public LabelElement(@Nonnull Minecraft instance, @Nonnull Component text, @Nonnull Alignment alignment, int color, boolean renderShadow) {
        super(instance, instance.font.lineHeight);
        this.text = text;
        this.alignment = alignment;

        this.color = color;
        this.renderShadow = renderShadow;
        
        this.width = 0;
    }
    public LabelElement(@Nonnull Minecraft instance, @Nonnull Component text, @Nonnull Alignment alignment, int color, int height) {
        super(instance, height);
        this.text = text;
        this.alignment = alignment;

        this.color = color;
        this.renderShadow = true;

        this.width = 0;
    }
    public LabelElement(@Nonnull Minecraft instance, @Nonnull Component text, @Nonnull Alignment alignment, int color, boolean renderShadow, int height) {
        super(instance, height);
        this.text = text;
        this.alignment = alignment;

        this.color = color;
        this.renderShadow = renderShadow;


        this.width = 0;
    }
    public LabelElement(@Nonnull Minecraft instance, @Nonnull Component text, @Nonnull Alignment alignment, int color, int width, int height) {
        super(instance, height);
        this.text = text;
        this.alignment = alignment;

        this.color = color;
        this.renderShadow = true;

        this.width = width;
    }
    public LabelElement(@Nonnull Minecraft instance, @Nonnull Component text, @Nonnull Alignment alignment, int color, boolean renderShadow, int width, int height) {
        super(instance, height);
        this.text = text;
        this.alignment = alignment;

        this.color = color;
        this.renderShadow = renderShadow;

        this.width = width;
    }

    @Override
    public void draw(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics, @Nonnull ScreenRect area, int mouseX, int mouseY, boolean mouseDown) {
        int wrappedHeight = instance.font.wordWrapHeight(text.getContents(), area.getWidth());
        BlitCoordinates pos = alignment.fitToArea(area.toBlitCoordinates(), wrappedHeight <= instance.font.lineHeight ? instance.font.width(text) : area.getWidth(), wrappedHeight);

        if(width > 0)
            pos = alignment.fitToArea(area.toBlitCoordinates().withWidth(width), wrappedHeight <= instance.font.lineHeight ? instance.font.width(text) : width, wrappedHeight);


        FontUtilities.drawParagraph(instance, graphics, 
            pos, 
            List.of(text), color, renderShadow);
    }
}
