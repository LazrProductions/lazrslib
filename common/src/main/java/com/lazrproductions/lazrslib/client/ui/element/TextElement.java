package com.lazrproductions.lazrslib.client.ui.element;

import java.util.List;

import com.lazrproductions.lazrslib.client.font.FontUtilities;
import com.lazrproductions.lazrslib.client.screen.base.ScreenRect;

import com.lazrproductions.lazrslib.common.component.ComponentUtilities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class TextElement extends AbstractElement {
    final List<Component> text;
    final int color;
    final boolean renderShadow;

    public TextElement(@NotNull Minecraft instance, @NotNull Component text, int height, int color) {
        super(instance, height);
        this.text = List.of(text);
        this.color = color;
        this.renderShadow = true;
    }

    public TextElement(@NotNull Minecraft instance, @NotNull Component text, int height, int color,
            boolean renderShadow) {
        super(instance, height);
        this.text = List.of(text);
        this.color = color;
        this.renderShadow = renderShadow;
    }

    public TextElement(@NotNull Minecraft instance, int width, @NotNull Component text, int color) {
        super(instance, instance.font.wordWrapHeight(text, width));
        this.text = List.of(text);
        this.color = color;
        this.renderShadow = true;
    }

    public TextElement(@NotNull Minecraft instance, int width, @NotNull Component text, int color,
            boolean renderShadow) {
        super(instance, instance.font.wordWrapHeight(text, width));
        this.text = List.of(text);
        this.color = color;
        this.renderShadow = renderShadow;
    }

    public TextElement(@NotNull Minecraft instance, int width, @NotNull List<Component> text, int color) {
        super(instance, ComponentUtilities.getTotalHeight(instance, text, width));
        this.text = text;
        this.color = color;
        this.renderShadow = true;
    }

    public TextElement(@NotNull Minecraft instance, int width, @NotNull List<Component> text, int color,
            boolean renderShadow) {
        super(instance, ComponentUtilities.getTotalHeight(instance, text, width));
        this.text = text;
        this.color = color;
        this.renderShadow = renderShadow;
    }

    @Override
    public void draw(@NotNull Minecraft instance, @NotNull GuiGraphics graphics, @NotNull ScreenRect area, int mouseX,
            int mouseY, boolean mouseDown) {
        FontUtilities.drawParagraph(instance.font, graphics, area.toScreenCoordinate(), text, color, renderShadow);
    }
}