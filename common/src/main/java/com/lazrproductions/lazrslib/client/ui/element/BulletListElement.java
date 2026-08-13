package com.lazrproductions.lazrslib.client.ui.element;

import java.util.List;

import com.lazrproductions.lazrslib.client.font.FontUtilities;
import com.lazrproductions.lazrslib.client.screen.ScreenUtilities;
import com.lazrproductions.lazrslib.client.screen.base.ScreenRect;
import com.lazrproductions.lazrslib.client.screen.base.ScreenTexture;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;

/**
 * A bulleted list element that draws a list of text components, each on their own line prefixed with a dot.
 */
public class BulletListElement extends AbstractElement {

    static final int LIST_ICON_SIZE = 16;
    static final int LIST_ITEM_PADDING = 7;

    final int width;
    final ScreenTexture bulletTexture;
    final List<Component> itemList;

    final int textColor;
    final boolean renderShadow;

    public BulletListElement(@Nonnull Minecraft instance, int width, @Nonnull ScreenTexture bulletTexture,
                             @Nonnull List<Component> list, int textColor) {
        super(instance, getTotalHeight(instance, width, list));
        this.width = width;
        this.bulletTexture = bulletTexture;
        this.itemList = list;

        this.textColor = textColor;
        this.renderShadow = true;
    }

    public BulletListElement(@Nonnull Minecraft instance, int width, @Nonnull ScreenTexture bulletTexture,
            @Nonnull List<Component> list, int textColor, boolean renderShadow) {
        super(instance, getTotalHeight(instance, width, list));
        this.width = width;
        this.bulletTexture = bulletTexture;
        this.itemList = list;

        this.textColor = textColor;
        this.renderShadow = renderShadow;
    }

    @Override
    public void draw(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics, @Nonnull ScreenRect area, int mouseX,
            int mouseY, boolean mouseDown) {

        int totalHeight = 0;
        for (Component component : itemList) {
            ScreenUtilities.drawTexture(graphics,
                    area.toScreenCoordinate().move(-4, totalHeight).withWidth(LIST_ICON_SIZE).withHeight(LIST_ICON_SIZE),
                    bulletTexture);

            int localHeight = instance.font.wordWrapHeight(component, width - (LIST_ICON_SIZE - 4));

            FontUtilities.drawParagraph(instance.font, graphics,
                    area.toScreenCoordinate().move((LIST_ICON_SIZE - 4), totalHeight)
                            .withWidth(area.toScreenCoordinate().getWidth() - (LIST_ICON_SIZE - 4))
                            .withHeight(localHeight),
                    List.of(component),
                    textColor, renderShadow);

            totalHeight += localHeight + LIST_ITEM_PADDING;
        }
    }

    static int getTotalHeight(@Nonnull Minecraft instance, int width, @Nonnull List<Component> list) {
        int totalHeight = 0;
        for (Component component : list)
            totalHeight += instance.font.wordWrapHeight(component, width - (LIST_ICON_SIZE - 4)) + LIST_ITEM_PADDING;
        return totalHeight;
    }
}