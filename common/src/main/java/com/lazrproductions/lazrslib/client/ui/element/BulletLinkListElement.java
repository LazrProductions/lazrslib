package com.lazrproductions.lazrslib.client.ui.element;

import java.util.List;

import com.lazrproductions.lazrslib.client.font.FontUtilities;
import com.lazrproductions.lazrslib.client.screen.ScreenUtilities;
import com.lazrproductions.lazrslib.client.screen.base.ScreenRect;
import com.lazrproductions.lazrslib.client.screen.base.ScreenTexture;
import com.lazrproductions.lazrslib.client.ui.IOnClickFunction;
import com.mojang.datafixers.util.Pair;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import javax.annotation.Nonnull;

/**
 * An extension of the {@link BulletListElement} that draws a list of links that can be clicked to run functions.
 */
public class BulletLinkListElement extends AbstractElement {

    static final int LIST_ICON_SIZE = 16;
    static final int LIST_ITEM_PADDING = 7;

    final int width;
    final ScreenTexture bulletTexture;
    final List<Pair<Component, IOnClickFunction>> itemList;

    final int textColor;
    final int highlightedColor;
    final boolean renderShadow;

    public BulletLinkListElement(@Nonnull Minecraft instance, int width, @Nonnull ScreenTexture bulletTexture,
                                 @Nonnull List<Pair<Component, IOnClickFunction>> list, int textColor, int highlightedColor) {
        super(instance, getTotalHeight(instance, width, list));
        this.width = width;
        this.bulletTexture = bulletTexture;
        this.itemList = list;

        this.textColor = textColor;
        this.highlightedColor = highlightedColor;
        this.renderShadow = true;
    }

    public BulletLinkListElement(@Nonnull Minecraft instance, int width, @Nonnull ScreenTexture bulletTexture,
                                 @Nonnull List<Pair<Component, IOnClickFunction>> list, int textColor, int highlightedColor,
                                 boolean renderShadow) {
        super(instance, getTotalHeight(instance, width, list));
        this.width = width;
        this.bulletTexture = bulletTexture;
        this.itemList = list;

        this.textColor = textColor;
        this.highlightedColor = highlightedColor;
        this.renderShadow = renderShadow;
    }

    @Override
    public void draw(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics, @Nonnull ScreenRect area, int mouseX, int mouseY, boolean mouseDown) {

        int clicked = -1;

        int totalHeight = 0;
        for (int i = 0; i < itemList.size(); i++) {
            ScreenUtilities.drawTexture(graphics,
                    area.toScreenCoordinate().move(-4, totalHeight).withWidth(LIST_ICON_SIZE).withHeight(LIST_ICON_SIZE),
                    bulletTexture);

            int localHeight = instance.font.wordWrapHeight(itemList.get(i).getFirst(), width - (LIST_ICON_SIZE - 4));

            if (FontUtilities.drawLinkWrapped(instance.font, graphics,
                    area.toScreenCoordinate().move((LIST_ICON_SIZE - 4), totalHeight)
                            .withWidth(area.toScreenCoordinate().getWidth() - (LIST_ICON_SIZE - 4))
                            .withHeight(localHeight),
                    itemList.get(i).getFirst(),
                    textColor,
                    highlightedColor, renderShadow,
                    mouseX, mouseY, mouseDown))
                clicked = i;

            totalHeight += localHeight + LIST_ITEM_PADDING;
        }

        if (clicked > -1)
            itemList.get(clicked).getSecond().call();
    }

    static int getTotalHeight(@Nonnull Minecraft instance, int width,
            @Nonnull List<Pair<Component, IOnClickFunction>> list) {
        int totalHeight = 0;
        for (Pair<Component, IOnClickFunction> componentIOnClickFunctionPair : list)
            totalHeight += instance.font.wordWrapHeight(componentIOnClickFunctionPair.getFirst(), width - (LIST_ICON_SIZE - 4))
                    + LIST_ITEM_PADDING;
        return totalHeight;
    }
}