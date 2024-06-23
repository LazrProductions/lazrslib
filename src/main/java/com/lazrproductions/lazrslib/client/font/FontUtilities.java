package com.lazrproductions.lazrslib.client.font;

import java.util.List;

import com.lazrproductions.lazrslib.client.screen.ScreenUtilities;
import com.lazrproductions.lazrslib.client.screen.base.BlitCoordinates;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;

public class FontUtilities {
    public static void renderLabel(Minecraft instance, GuiGraphics graphics, int x, int y, List<Component> list,
            int color, boolean renderShadow) {
        int space = 15;
        int width = 0;
        for (int i = 0; i < list.size(); i++) {
            String text = list.get(i).getString();
            width = Math.max(width, instance.font.width(text) + 10);
        }

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();

        for (int i = 0; i < list.size(); i++) {
            String text = list.get(i).getString();

            graphics.drawString(instance.font, text,
                    x - instance.font.width(text) / 2,
                    y + ((list.size() / 2) * space + (space * i)),
                    color, renderShadow);
        }

        RenderSystem.enableDepthTest();
    }

    public static void renderLabel(Minecraft instance, GuiGraphics graphics, int x, int y, List<Component> list,
            int color) {
        renderLabel(instance, graphics, x, y, list, color, true);
    }

    public static int drawParagraph(Minecraft instance, GuiGraphics graphics, int x, int y, List<Component> list,
            int maxWidth, int color, boolean renderShadow) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();

        FormattedText text = FormattedText.composite(list);
        for (FormattedCharSequence formattedcharsequence : instance.font.split(text, maxWidth)) {
            graphics.drawString(instance.font, formattedcharsequence, x, y, color, renderShadow);
            y += 9;
        }

        RenderSystem.enableDepthTest();

        return instance.font.wordWrapHeight(text, maxWidth);
    }

    public static int drawParagraph(Minecraft instance, GuiGraphics graphics, int x, int y, List<Component> list,
            int maxWidth, int color) {
        return drawParagraph(instance, graphics, x, y, list, maxWidth, color, true);
    }

    public static int drawParagraph(Minecraft instance, GuiGraphics graphics, BlitCoordinates pos,
            List<Component> list, int color) {
        return drawParagraph(instance, graphics, pos.getX(), pos.getY(), list, pos.getWidth(), color, true);
    }

    public static int drawParagraph(Minecraft instance, GuiGraphics graphics, BlitCoordinates pos,
            List<Component> list, int color, boolean renderShadow) {
        return drawParagraph(instance, graphics, pos.getX(), pos.getY(), list, pos.getWidth(), color, renderShadow);
    }

    public static void drawText(Minecraft instance, GuiGraphics graphics, BlitCoordinates pos, Component text,
            int color) {
        drawText(instance, graphics, pos, text, color, true);
    }

    public static void drawText(Minecraft instance, GuiGraphics graphics, BlitCoordinates pos, Component text,
            int color, boolean renderShadow) {
        pos = pos.withHeight(instance.font.lineHeight).withWidth(instance.font.width(text));

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();

        graphics.drawString(instance.font, text, pos.getX(), pos.getY(), color, renderShadow);

        RenderSystem.enableDepthTest();
    }

    public static boolean drawLink(Minecraft instance, GuiGraphics graphics, BlitCoordinates pos, Component text,
            int color, int highlightedColor, double mouseX, double mouseY, boolean mouseDown) {
        return drawLink(instance, graphics, pos, text, color, highlightedColor, true, mouseX, mouseY, mouseDown);
    }

    public static boolean drawLink(Minecraft instance, GuiGraphics graphics, BlitCoordinates pos, Component text,
            int color, int highlightedColor, boolean renderShadow, double mouseX, double mouseY, boolean mouseDown) {
        int areaWidth = instance.font.width(text);
        int areaHeight = instance.font.lineHeight;
        boolean highlighted = ScreenUtilities.mouseInArea(mouseX, mouseY,
                pos.withWidth(areaWidth).withHeight(areaHeight).toRect());

        drawText(instance, graphics, pos, text, highlighted ? highlightedColor : color, renderShadow);

        if (highlighted && mouseDown)
            return true;
        return false;
    }

    public static boolean drawLinkWrapped(Minecraft instance, GuiGraphics graphics, BlitCoordinates pos, Component text,
            int color, int highlightedColor, double mouseX, double mouseY, boolean mouseDown) {
        return drawLinkWrapped(instance, graphics, pos, text, color, highlightedColor, true, mouseX, mouseY, mouseDown);
    }

    public static boolean drawLinkWrapped(Minecraft instance, GuiGraphics graphics, BlitCoordinates pos, Component text,
            int color, int highlightedColor, boolean renderShadow, double mouseX, double mouseY, boolean mouseDown) {
        int areaWidth = pos.getWidth();
        int areaHeight = instance.font.wordWrapHeight(text, areaWidth);
        boolean highlighted = ScreenUtilities.mouseInArea(mouseX, mouseY,
                pos.withWidth(areaWidth).withHeight(areaHeight).toRect());

        drawParagraph(instance, graphics, pos, List.of(text), highlighted ? highlightedColor : color, renderShadow);

        if (highlighted && mouseDown)
            return true;
        return false;
    }
}
