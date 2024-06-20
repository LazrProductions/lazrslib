package com.lazrproductions.lazrslib.ui.element;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.screen.ScreenUtilities;
import com.lazrproductions.lazrslib.screen.base.ScreenRect;
import com.lazrproductions.lazrslib.screen.base.ScreenTexture;

import net.minecraft.client.Minecraft;
import com.lazrproductions.lazrslib.gui.GuiGraphics;
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
