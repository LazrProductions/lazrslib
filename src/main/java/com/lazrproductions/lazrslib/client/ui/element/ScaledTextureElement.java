package com.lazrproductions.lazrslib.client.ui.element;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.client.gui.GuiGraphics;
import com.lazrproductions.lazrslib.client.screen.ScreenUtilities;
import com.lazrproductions.lazrslib.client.screen.base.ScreenRect;
import com.lazrproductions.lazrslib.client.screen.base.ScreenTexture;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;

public class ScaledTextureElement extends AbstractElement {

            final ScreenTexture texture;
            final int width;

            public ScaledTextureElement(@Nonnull Minecraft instance, @Nonnull ScreenTexture texture, int maxHeight) {
                super(instance, maxHeight);
                this.texture = texture;
                this.width = Mth.floor(maxHeight * texture.getAspectRatio());
            }


            @Override
            public void draw(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics, @Nonnull ScreenRect area, int mouseX, int mouseY, boolean mouseDown) {
                int width = area.getWidth();
                int height = Mth.floor(width / texture.getAspectRatio());

                ScreenUtilities.drawTexture(graphics, area.toBlitCoordinates().withHeight(height), texture);
            }
}
