package com.lazrproductions.lazrslib.client.ui.element;

import com.lazrproductions.lazrslib.client.screen.ScreenUtilities;
import com.lazrproductions.lazrslib.client.screen.base.ScreenRect;
import com.lazrproductions.lazrslib.client.screen.base.ScreenTexture;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

public class ScaledTextureElement extends AbstractElement {

            final ScreenTexture texture;
            final int width;

            public ScaledTextureElement(@NotNull Minecraft instance, @NotNull ScreenTexture texture, int maxHeight) {
                super(instance, maxHeight);
                this.texture = texture;
                this.width = Mth.floor(maxHeight * texture.getAspectRatio());
            }


            @Override
            public void draw(@NotNull Minecraft instance, @NotNull GuiGraphics graphics, @NotNull ScreenRect area, int mouseX, int mouseY, boolean mouseDown) {
                int width = area.getWidth();
                int height = Mth.floor(width / texture.getAspectRatio());

                ScreenUtilities.drawTexture(graphics, area.toScreenCoordinate().withHeight(height), texture);
            }
}
