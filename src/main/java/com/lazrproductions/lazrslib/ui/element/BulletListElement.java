package com.lazrproductions.lazrslib.ui.element;

import java.util.List;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.screen.ScreenUtilities;
import com.lazrproductions.lazrslib.screen.base.ScreenRect;
import com.lazrproductions.lazrslib.screen.base.ScreenTexture;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class BulletListElement extends AbstractElement {

            static final int LIST_ICON_SIZE = 16;
            static final int LIST_ITEM_PADDING = 7;

            final int width;
            final ScreenTexture bulletTexture;
            final List<Component> itemList;
            final int textColor;

            public BulletListElement(@Nonnull Minecraft instance, int width, @Nonnull ScreenTexture bulletTexture, @Nonnull List<Component> list, int textColor) {
                super(instance, getTotalHeight(instance, width, list));
                this.width = width;
                this.bulletTexture = bulletTexture;
                this.itemList = list;
                this.textColor = textColor;
            }

            @Override
            public void draw(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics, @Nonnull ScreenRect area, int mouseX, int mouseY, boolean mouseDown) {

                int totalHeight = 0;
                for (int i = 0; i < itemList.size(); i++) {
                    ScreenUtilities.drawTexture(graphics, 
                        area.toBlitCoordinates().move(-4, totalHeight).withWidth(LIST_ICON_SIZE).withHeight(LIST_ICON_SIZE), 
                        bulletTexture);

                    int localHeight = instance.font.wordWrapHeight(itemList.get(i), width - (LIST_ICON_SIZE - 4));

                    ScreenUtilities.drawParagraph(instance, graphics, 
                        area.toBlitCoordinates().move((LIST_ICON_SIZE - 4), totalHeight).withWidth(area.toBlitCoordinates().getWidth() - (LIST_ICON_SIZE - 4)).withHeight(localHeight), 
                        List.of(itemList.get(i)),
                        textColor, false);

                    totalHeight += localHeight + + LIST_ITEM_PADDING;
                }
            }

            static int getTotalHeight(@Nonnull Minecraft instance, int width, @Nonnull List<Component> list) {
                int totalHeight = 0;
                for (int i = 0; i < list.size(); i++)
                    totalHeight += instance.font.wordWrapHeight(list.get(i), width - (LIST_ICON_SIZE - 4)) + LIST_ITEM_PADDING;
                return totalHeight;
            }
}
