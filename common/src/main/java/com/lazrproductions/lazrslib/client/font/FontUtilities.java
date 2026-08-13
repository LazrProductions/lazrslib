package com.lazrproductions.lazrslib.client.font;

import java.util.List;

import com.lazrproductions.lazrslib.client.screen.ScreenUtilities;
import com.lazrproductions.lazrslib.client.screen.base.ScreenCoordinate;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;

import javax.annotation.Nonnull;

public class FontUtilities {
    /**
     * Draw the given list of components to the screen, each on their own line.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param x The X position to draw at.
     * @param y The Y position to draw at.
     * @param list The list of components to draw.
     * @param color The color of the drawn text.
     */
    public static void renderLabel(@Nonnull Font font, @Nonnull GuiGraphics graphics, int x, int y, @Nonnull List<Component> list, int color) {
        renderLabel(font, graphics, x, y, list, color, true);
    }
    /**
     * Draw the given list of components to the screen, each on their own line.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param x The X position to draw at.
     * @param y The Y position to draw at.
     * @param list The list of components to draw.
     * @param color The color of the drawn text.
     * @param renderShadow Whether to draw the text's drop shadow.
     */
    public static void renderLabel(@Nonnull Font font, @Nonnull GuiGraphics graphics, int x, int y, @Nonnull List<Component> list, int color, boolean renderShadow) {
        int space = 15;
        int width = 0;
        for (Component component : list) {
            String text = component.getString();
            width = Math.max(width, font.width(text) + 10);
        }

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();

        for (int i = 0; i < list.size(); i++) {
            String text = list.get(i).getString();
            graphics.drawString(font, text,
                    x - font.width(text) / 2,
                    y + ((list.size() / 2) * space + (space * i)),
                    color, renderShadow);
        }
        RenderSystem.enableDepthTest();
    }
    /**
     * Draw the given list of components to the screen, each on their own line.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param list The list of components to draw.
     * @param color The color of the drawn text.
     */
    public static void renderLabel(@Nonnull Font font, @Nonnull GuiGraphics graphics, @Nonnull ScreenCoordinate pos, @Nonnull List<Component> list, int color) {
        renderLabel(font, graphics, pos.getX(), pos.getY(), list, color, true);
    }
    /**
     * Draw the given list of components to the screen, each on their own line.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param list The list of components to draw.
     * @param color The color of the drawn text.
     * @param renderShadow Whether to draw the text's drop shadow.
     */
    public static void renderLabel(@Nonnull Font font, @Nonnull GuiGraphics graphics, @Nonnull ScreenCoordinate pos, @Nonnull List<Component> list, int color, boolean renderShadow) {
        renderLabel(font, graphics, pos.getX(), pos.getY(), list, color, renderShadow);
    }
    /**
     * Draw text to the screen.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param x The X position to draw at.
     * @param y The Y position to draw at.
     * @param text The text to draw.
     * @param color The color of the drawn text.
     */
    public static void renderLabel(@Nonnull Font font, @Nonnull GuiGraphics graphics, int x, int y, String text, int color) {
        renderLabel(font, graphics, x, y, text, color, true);
    }
    /**
     * Draw text to the screen.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param x The X position to draw at.
     * @param y The Y position to draw at.
     * @param text The text to draw.
     * @param color The color of the drawn text.
     * @param renderShadow Whether to draw the text's drop shadow.
     */
    public static void renderLabel(@Nonnull Font font, @Nonnull GuiGraphics graphics, int x, int y, String text, int color, boolean renderShadow) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();

        graphics.drawString(font, text,
                x - font.width(text) / 2,
                y,
                color, renderShadow);

        RenderSystem.enableDepthTest();
    }
    /**
     * Draw text to the screen.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param text The text to draw.
     * @param color The color of the drawn text.
     */
    public static void renderLabel(@Nonnull Font font, @Nonnull GuiGraphics graphics, @Nonnull ScreenCoordinate pos, @Nonnull String text, int color) {
        renderLabel(font, graphics, pos.getX(), pos.getY(), text, color, true);
    }
    /**
     * Draw text to the screen.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param text The text to draw.
     * @param color The color of the drawn text.
     * @param renderShadow Whether to draw the text's drop shadow.
     */
    public static void renderLabel(@Nonnull Font font, @Nonnull GuiGraphics graphics, @Nonnull ScreenCoordinate pos, @Nonnull String text, int color, boolean renderShadow) {
        renderLabel(font, graphics, pos.getX(), pos.getY(), text, color, renderShadow);
    }


    /**
     * Draw text to the screen and wrap it to fit within the given width.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param x The X position to draw at.
     * @param y The Y position to draw at.
     * @param text The text to draw.
     * @param maxWidth The maximum width to conform the text to.
     * @param color The color of the drawn text.
     */
    public static void drawParagraph(@Nonnull Font font, @Nonnull GuiGraphics graphics, int x, int y, @Nonnull String text, int maxWidth, int color) {
        drawParagraph(font, graphics, x, y, text, maxWidth, color, true);
    }
    /**
     * Draw text to the screen and wrap it to fit within the given width.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param x The X position to draw at.
     * @param y The Y position to draw at.
     * @param text The text to draw.
     * @param maxWidth The maximum width to conform the text to.
     * @param color The color of the drawn text.
     * @param renderShadow Whether to draw the text's drop shadow.
     */
    public static void drawParagraph(@Nonnull Font font, @Nonnull GuiGraphics graphics, int x, int y, @Nonnull String text, int maxWidth, int color, boolean renderShadow) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();

        FormattedText para = FormattedText.of(text);
        graphics.drawWordWrap(font, para, x, y, maxWidth, color);

        RenderSystem.enableDepthTest();
    }
    /**
     * Draw text to the screen and wrap it to fit within the given blit coordinates.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param text The text to draw.
     * @param color The color of the drawn text.
     */
    public static void drawParagraph(@Nonnull Font font, @Nonnull GuiGraphics graphics, @Nonnull ScreenCoordinate pos, @Nonnull String text, int color) {
        drawParagraph(font, graphics, pos.getX(), pos.getY(), text, pos.getWidth(), color, true);
    }
    /**
     * Draw text to the screen and wrap it to fit within the given blit coordinates.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param text The text to draw.
     * @param color The color of the drawn text.
     * @param renderShadow Whether to draw the text's drop shadow.
     */
    public static void drawParagraph(@Nonnull Font font, @Nonnull GuiGraphics graphics, @Nonnull ScreenCoordinate pos, @Nonnull String text, int color, boolean renderShadow) {
        drawParagraph(font, graphics, pos.getX(), pos.getY(), text, pos.getWidth(), color, renderShadow);
    }
    /**
     * Draw a component to the screen and wrap it to fit within the given width.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param x The X position to draw at.
     * @param y The Y position to draw at.
     * @param component The text to draw.
     * @param maxWidth The maximum width to conform the text to.
     * @param color The color of the drawn text.
     */
    public static void drawParagraph(@Nonnull Font font, @Nonnull GuiGraphics graphics, int x, int y, @Nonnull Component component, int maxWidth, int color) {
        drawParagraph(font, graphics, x, y, component, maxWidth, color, true);
    }
    /**
     * Draw a component to the screen and wrap it to fit within the given width.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param x The X position to draw at.
     * @param y The Y position to draw at.
     * @param component The text to draw.
     * @param maxWidth The maximum width to conform the text to.
     * @param color The color of the drawn text.
     * @param renderShadow Whether to draw the text's drop shadow.
     */
    public static void drawParagraph(@Nonnull Font font, @Nonnull GuiGraphics graphics, int x, int y, @Nonnull Component component, int maxWidth, int color, boolean renderShadow) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();

        FormattedText para = FormattedText.composite(component);
        graphics.drawWordWrap(font, para, x, y, maxWidth, color);

        RenderSystem.enableDepthTest();
    }
    /**
     * Draw a component to the screen and wrap it to fit within the given blit coordinates.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param component The text to draw.
     * @param color The color of the drawn text.
     */
    public static void drawParagraph(@Nonnull Font font, @Nonnull GuiGraphics graphics, @Nonnull ScreenCoordinate pos, @Nonnull Component component, int color) {
        drawParagraph(font, graphics, pos.getX(), pos.getY(), component, pos.getWidth(), color, true);
    }
    /**
     * Draw a component to the screen and wrap it to fit within the given blit coordinates.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param component The text to draw.
     * @param color The color of the drawn text.
     * @param renderShadow Whether to draw the text's drop shadow.
     */
    public static void drawParagraph(@Nonnull Font font, @Nonnull GuiGraphics graphics, @Nonnull ScreenCoordinate pos, @Nonnull Component component, int color, boolean renderShadow) {
        drawParagraph(font, graphics, pos.getX(), pos.getY(), component, pos.getWidth(), color, renderShadow);
    }
    /**
     * Draw a list of components to the screen, each on their own line.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param x The X position to draw at.
     * @param y The Y position to draw at.
     * @param list The list of components to draw.
     * @param maxWidth The maximum width to conform the text to.
     * @param color The color of the drawn text.
     */
    public static void drawParagraph(@Nonnull Font font, @Nonnull GuiGraphics graphics, int x, int y, @Nonnull List<Component> list, int maxWidth, int color) {
        drawParagraph(font, graphics, x, y, list, maxWidth, color, true);
    }
    /**
     * Draw a list of components to the screen, each on their own line.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param x The X position to draw at.
     * @param y The Y position to draw at.
     * @param list The list of components to draw.
     * @param maxWidth The maximum width to conform the text to.
     * @param color The color of the drawn text.
     * @param renderShadow Whether to draw the text's drop shadow.
     */
    public static void drawParagraph(@Nonnull Font font, @Nonnull GuiGraphics graphics, int x, int y, @Nonnull List<Component> list, int maxWidth, int color, boolean renderShadow) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();

        FormattedText text = FormattedText.composite(list);
        graphics.drawWordWrap(font, text, x, y, maxWidth, color);

        RenderSystem.enableDepthTest();
    }
    /**
     * Draw a list of components to the screen, each on their own line.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param list The list of components to draw.
     * @param color The color of the drawn text.
     */
    public static void drawParagraph(@Nonnull Font font, @Nonnull GuiGraphics graphics, @Nonnull ScreenCoordinate pos, @Nonnull List<Component> list, int color) {
        drawParagraph(font, graphics, pos.getX(), pos.getY(), list, pos.getWidth(), color, true);
    }
    /**
     * Draw a list of components to the screen, each on their own line.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param list The list of components to draw.
     * @param color The color of the drawn text.
     * @param renderShadow Whether to draw the text's drop shadow.
     */
    public static void drawParagraph(@Nonnull Font font, @Nonnull GuiGraphics graphics, @Nonnull ScreenCoordinate pos, @Nonnull List<Component> list, int color, boolean renderShadow) {
        drawParagraph(font, graphics, pos.getX(), pos.getY(), list, pos.getWidth(), color, renderShadow);
    }


    /**
     * Draw text to the screen.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param x The X position to draw at.
     * @param y The Y position to draw at.
     * @param text The text to draw.
     * @param color The color of the drawn text.
     */
    public static void drawText(@Nonnull Font font, @Nonnull GuiGraphics graphics, int x, int y, @Nonnull String text, int color) {
        drawText(font, graphics, x, y, text, color, true);
    }
    /**
     * Draw text to the screen.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param x The X position to draw at.
     * @param y The Y position to draw at.
     * @param text The text to draw.
     * @param color The color of the drawn text.
     * @param renderShadow Whether to draw the text's drop shadow.
     */
    public static void drawText(@Nonnull Font font, @Nonnull GuiGraphics graphics, int x, int y, @Nonnull String text, int color, boolean renderShadow) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();

        graphics.drawString(font, text, x, y, color, renderShadow);

        RenderSystem.enableDepthTest();
    }
    /**
     * Draw text to the screen.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param x The X position to draw at.
     * @param y The Y position to draw at.
     * @param component The text to draw.
     * @param color The color of the drawn text.
     */
    public static void drawText(@Nonnull Font font, @Nonnull GuiGraphics graphics, int x, int y, @Nonnull Component component, int color) {
        drawText(font, graphics, x, y, component, color, true);
    }
    /**
     * Draw text to the screen.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param x The X position to draw at.
     * @param y The Y position to draw at.
     * @param component The text to draw.
     * @param color The color of the drawn text.
     * @param renderShadow Whether to draw the text's drop shadow.
     */
    public static void drawText(@Nonnull Font font, @Nonnull GuiGraphics graphics, int x, int y, @Nonnull Component component, int color, boolean renderShadow) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();

        graphics.drawString(font, component, x, y, color, renderShadow);

        RenderSystem.enableDepthTest();
    }
    /**
     * Draw text to the screen.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param text The text to draw.
     * @param color The color of the drawn text.
     */
    public static void drawText(@Nonnull Font font, @Nonnull GuiGraphics graphics, @Nonnull ScreenCoordinate pos, @Nonnull String text, int color) {
        drawText(font, graphics, pos, text, color, true);
    }
    /**
     * Draw text to the screen.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param text The text to draw.
     * @param color The color of the drawn text.
     * @param renderShadow Whether to draw the text's drop shadow.
     */
    public static void drawText(@Nonnull Font font, @Nonnull GuiGraphics graphics, @Nonnull ScreenCoordinate pos, @Nonnull String text, int color, boolean renderShadow) {
        pos = pos.withHeight(font.lineHeight).withWidth(font.width(text));

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();

        graphics.drawString(font, text, pos.getX(), pos.getY(), color, renderShadow);

        RenderSystem.enableDepthTest();
    }
    /**
     * Draw text to the screen.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param component The text to draw.
     * @param color The color of the drawn text.
     */
    public static void drawText(@Nonnull Font font, @Nonnull GuiGraphics graphics, @Nonnull ScreenCoordinate pos, @Nonnull Component component, int color) {
        drawText(font, graphics, pos, component, color, true);
    }
    /**
     * Draw text to the screen.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param component The text to draw.
     * @param color The color of the drawn text.
     * @param renderShadow Whether to draw the text's drop shadow.
     */
    public static void drawText(@Nonnull Font font, @Nonnull GuiGraphics graphics, @Nonnull ScreenCoordinate pos, @Nonnull Component component, int color, boolean renderShadow) {
        pos = pos.withHeight(font.lineHeight).withWidth(font.width(component));

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();

        graphics.drawString(font, component, pos.getX(), pos.getY(), color, renderShadow);

        RenderSystem.enableDepthTest();
    }


    /**
     * Draw a link text to the screen that changes color when highlighted.
     * <br/><br/>
     *     This function returns <code>true</code> when the link has been clicked.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param text The link text to draw.
     * @param color The color of the drawn link text.
     * @param highlightedColor The highlighted color of the link text.
     * @param mouseX The current mouse X.
     * @param mouseY The current mouse Y.
     * @param mouseClicked Whether the left mouse button is currently clicked.
     * @return <code>true</code> when the mouse if over this link and the left mouse button is down.
     */
    public static boolean drawLink(@Nonnull Font font, @Nonnull GuiGraphics graphics, @Nonnull ScreenCoordinate pos, @Nonnull String text, int color, int highlightedColor, double mouseX, double mouseY, boolean mouseClicked) {
        return drawLink(font, graphics, pos, text, color, highlightedColor, true, mouseX, mouseY, mouseClicked);
    }
    /**
     * Draw a link text to the screen that changes color when highlighted.
     * <br/><br/>
     *     This function returns <code>true</code> when the link has been clicked.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param text The link text to draw.
     * @param color The color of the drawn link text.
     * @param highlightedColor The highlighted color of the link text.
     * @param renderShadow Whether to draw the link text's drop shadow.
     * @param mouseX The current mouse X.
     * @param mouseY The current mouse Y.
     * @param mouseClicked Whether the left mouse button is currently clicked.
     * @return <code>true</code> when the mouse if over this link and the left mouse button is down.
     */
    public static boolean drawLink(@Nonnull Font font, @Nonnull GuiGraphics graphics, @Nonnull ScreenCoordinate pos, @Nonnull String text, int color, int highlightedColor, boolean renderShadow, double mouseX, double mouseY, boolean mouseClicked) {
        int areaWidth = font.width(text);
        int areaHeight = font.lineHeight;
        boolean highlighted = ScreenUtilities.mouseInArea(mouseX, mouseY,
                pos.withWidth(areaWidth).withHeight(areaHeight).toScreenRect());

        drawText(font, graphics, pos, text, highlighted ? highlightedColor : color, renderShadow);

        return highlighted && mouseClicked;
    }
    /**
     * Draw a link text to the screen that changes color when highlighted.
     * <br/><br/>
     *     This function returns <code>true</code> when the link has been clicked.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param component The link text to draw.
     * @param color The color of the drawn link text.
     * @param highlightedColor The highlighted color of the link text.
     * @param mouseX The current mouse X.
     * @param mouseY The current mouse Y.
     * @param mouseClicked Whether the left mouse button is currently clicked.
     * @return <code>true</code> when the mouse if over this link and the left mouse button is down.
     */
    public static boolean drawLink(@Nonnull Font font, @Nonnull GuiGraphics graphics, @Nonnull ScreenCoordinate pos, @Nonnull Component component, int color, int highlightedColor, double mouseX, double mouseY, boolean mouseClicked) {
        return drawLink(font, graphics, pos, component, color, highlightedColor, true, mouseX, mouseY, mouseClicked);
    }
    /**
     * Draw a link text to the screen that changes color when highlighted.
     * <br/><br/>
     *     This function returns <code>true</code> when the link has been clicked.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param component The link text to draw.
     * @param color The color of the drawn link text.
     * @param highlightedColor The highlighted color of the link text.
     * @param renderShadow Whether to draw the link text's drop shadow.
     * @param mouseX The current mouse X.
     * @param mouseY The current mouse Y.
     * @param mouseClicked Whether the left mouse button is currently clicked.
     * @return <code>true</code> when the mouse if over this link and the left mouse button is down.
     */
    public static boolean drawLink(@Nonnull Font font, @Nonnull GuiGraphics graphics, @Nonnull ScreenCoordinate pos, @Nonnull Component component, int color, int highlightedColor, boolean renderShadow, double mouseX, double mouseY, boolean mouseClicked) {
        int areaWidth = font.width(component);
        int areaHeight = font.lineHeight;
        boolean highlighted = ScreenUtilities.mouseInArea(mouseX, mouseY,
                pos.withWidth(areaWidth).withHeight(areaHeight).toScreenRect());

        drawText(font, graphics, pos, component, highlighted ? highlightedColor : color, renderShadow);

        return highlighted && mouseClicked;
    }


    /**
     * Draw a link text to the screen that wraps to fit within the provided blit coordinates.
     * <br/><br/>
     *     This function returns <code>true</code> when the link has been clicked.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param text The link text to draw.
     * @param color The color of the drawn link text.
     * @param highlightedColor The highlighted color of the link text.
     * @param mouseX The current mouse X.
     * @param mouseY The current mouse Y.
     * @param mouseClicked Whether the left mouse button is currently clicked.
     * @return <code>true</code> when the mouse if over this link and the left mouse button is down.
     */
    public static boolean drawLinkWrapped(@Nonnull Font font, @Nonnull GuiGraphics graphics, @Nonnull ScreenCoordinate pos, @Nonnull String text, int color, int highlightedColor, double mouseX, double mouseY, boolean mouseClicked) {
        return drawLinkWrapped(font, graphics, pos, text, color, highlightedColor, true, mouseX, mouseY, mouseClicked);
    }
    /**
     * Draw a link text to the screen that wraps to fit within the provided blit coordinates.
     * <br/><br/>
     *     This function returns <code>true</code> when the link has been clicked.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param text The link text to draw.
     * @param color The color of the drawn link text.
     * @param highlightedColor The highlighted color of the link text.
     * @param renderShadow Whether to draw the link text's drop shadow.
     * @param mouseX The current mouse X.
     * @param mouseY The current mouse Y.
     * @param mouseClicked Whether the left mouse button is currently clicked.
     * @return <code>true</code> when the mouse if over this link and the left mouse button is down.
     */
    public static boolean drawLinkWrapped(@Nonnull Font font, @Nonnull GuiGraphics graphics, @Nonnull ScreenCoordinate pos, @Nonnull String text, int color, int highlightedColor, boolean renderShadow, double mouseX, double mouseY, boolean mouseClicked) {
        int areaWidth = pos.getWidth();
        int areaHeight = font.wordWrapHeight(text, areaWidth);
        boolean highlighted = ScreenUtilities.mouseInArea(mouseX, mouseY,
                pos.withWidth(areaWidth).withHeight(areaHeight).toScreenRect());

        drawParagraph(font, graphics, pos, text, highlighted ? highlightedColor : color, renderShadow);

        return highlighted && mouseClicked;
    }
    /**
     * Draw a link text to the screen that wraps to fit within the provided blit coordinates.
     * <br/><br/>
     *     This function returns <code>true</code> when the link has been clicked.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param component The link text to draw.
     * @param color The color of the drawn link text.
     * @param highlightedColor The highlighted color of the link text.
     * @param mouseX The current mouse X.
     * @param mouseY The current mouse Y.
     * @param mouseClicked Whether the left mouse button is currently clicked.
     * @return <code>true</code> when the mouse if over this link and the left mouse button is down.
     */
    public static boolean drawLinkWrapped(@Nonnull Font font, @Nonnull GuiGraphics graphics, @Nonnull ScreenCoordinate pos, @Nonnull Component component, int color, int highlightedColor, double mouseX, double mouseY, boolean mouseClicked) {
        return drawLinkWrapped(font, graphics, pos, component, color, highlightedColor, true, mouseX, mouseY, mouseClicked);
    }
    /**
     * Draw a link text to the screen that wraps to fit within the provided blit coordinates.
     * <br/><br/>
     *     This function returns <code>true</code> when the link has been clicked.
     * @param font The font to use.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param component The link text to draw.
     * @param color The color of the drawn link text.
     * @param highlightedColor The highlighted color of the link text.
     * @param renderShadow Whether to draw the link text's drop shadow.
     * @param mouseX The current mouse X.
     * @param mouseY The current mouse Y.
     * @param mouseClicked Whether the left mouse button is currently clicked.
     * @return <code>true</code> when the mouse if over this link and the left mouse button is down.
     */
    public static boolean drawLinkWrapped(@Nonnull Font font, @Nonnull GuiGraphics graphics, @Nonnull ScreenCoordinate pos, @Nonnull Component component, int color, int highlightedColor, boolean renderShadow, double mouseX, double mouseY, boolean mouseClicked) {
        int areaWidth = pos.getWidth();
        int areaHeight = font.wordWrapHeight(component, areaWidth);
        boolean highlighted = ScreenUtilities.mouseInArea(mouseX, mouseY,
                pos.withWidth(areaWidth).withHeight(areaHeight).toScreenRect());

        drawParagraph(font, graphics, pos, component, highlighted ? highlightedColor : color, renderShadow);

        return highlighted && mouseClicked;
    }
}
