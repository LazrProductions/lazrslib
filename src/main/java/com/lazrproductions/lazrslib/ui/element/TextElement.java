package com.lazrproductions.lazrslib.ui.element;

import java.util.List;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.font.ComponentUtilities;
import com.lazrproductions.lazrslib.font.FontUtilities;
import com.lazrproductions.lazrslib.screen.base.ScreenRect;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class TextElement extends AbstractElement {
    final List<Component> text;
            final int color;

            public TextElement(@Nonnull Minecraft instance, @Nonnull Component text, int height, int color) {
                super(instance, height);
                this.text = List.of(text);
                this.color = color;
            }
            public TextElement(@Nonnull Minecraft instance, int width, @Nonnull Component text, int color) {
                super(instance, instance.font.wordWrapHeight(text, width));
                this.text = List.of(text);
                this.color = color;
            }
            public TextElement(@Nonnull Minecraft instance, int width, @Nonnull List<Component> text, int color) {
                super(instance, ComponentUtilities.getTotalHeight(instance, text, width));
                this.text = text;
                this.color = color;
            }


            @Override
            public void draw(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics, @Nonnull ScreenRect area, int mouseX, int mouseY, boolean mouseDown) {
                FontUtilities.drawParagraph(instance, graphics, area.toBlitCoordinates(), text, color);
            }
}
