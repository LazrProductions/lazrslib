package com.lazrproductions.lazrslib.client.ui.element;

import com.lazrproductions.lazrslib.client.screen.ScreenUtilities;
import com.lazrproductions.lazrslib.client.screen.base.ScreenCoordinate;
import com.lazrproductions.lazrslib.client.screen.base.ScreenRect;
import com.lazrproductions.lazrslib.client.screen.base.ScreenTexture;
import com.lazrproductions.lazrslib.client.ui.Alignment;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;

import javax.annotation.Nonnull;

public class TextureElement extends AbstractElement {
    final ScreenTexture texture;
    final int width;
    final Alignment align;
    
    public TextureElement(@Nonnull Minecraft instance, @Nonnull ScreenTexture texture, Alignment align, @Nonnull ScreenCoordinate fillToArea) {
        super(instance, Mth.floor(fillToArea.getWidth() / texture.getAspectRatio()));
        this.texture = texture;
        this.width = fillToArea.getWidth();
        this.align = align;
    }
    public TextureElement(@Nonnull Minecraft instance, @Nonnull ScreenTexture texture, Alignment align, int height) {
        super(instance, height);
        this.texture = texture;
        this.width = Mth.floor(height * texture.getAspectRatio());
        this.align = align;
    }
    
    
    @Override
    public void draw(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics,  @Nonnull ScreenRect area, int mouseX, int mouseY, boolean mouseDown) {
        ScreenUtilities.drawTexture(graphics, align.fitToArea(area.toScreenCoordinate(), width, fixedHeight), texture);
    }
}
