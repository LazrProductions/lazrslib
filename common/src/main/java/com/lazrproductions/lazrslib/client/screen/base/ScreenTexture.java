package com.lazrproductions.lazrslib.client.screen.base;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public class ScreenTexture {
    private final ResourceLocation location;
    private final float u;
    private final float v;
    private final int boundsX;
    private final int boundsY;
    private final int width;
    private final int height;

    public ScreenTexture(@NotNull ResourceLocation location, float u, float v, int boundsX, int boundsY, int width, int height) {
        this.location = location;

        this.u = u;
        this.v = v;

        this.boundsX = boundsX;
        this.boundsY = boundsY;

        this.width = width;
        this.height = height;
    }

    public @NotNull ResourceLocation getResourceLocation() {
        return location;
    }

    public float getU() {
        return u;
    }

    public float getV() {
        return v;
    }

    public int getBoundsX() {
        return boundsX;
    }

    public int getBoundsY() {
        return boundsY;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /** Get the width to height ratio */
    public float getAspectRatio() {
        return getBoundsX() / (float) getBoundsY();
    }
}
