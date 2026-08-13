package com.lazrproductions.lazrslib.client.screen.base;

import net.minecraft.util.Mth;
import org.joml.Vector2i;

import javax.annotation.Nonnull;

public class ScreenRect {
    int startX, startY, endX, endY;

    public ScreenRect(int fromX, int fromY, int toX, int toY) {
        this.startX = fromX;
        this.startY = fromY;
        this.endX = toX;
        this.endY = toY;

        if (this.startY > this.endY) {
            int f = this.endY;
            this.endY = this.startY;
            this.startY = f;
        }

        if (this.startX > this.endX) {
            int f = this.endX;
            this.endX = this.startX;
            this.startX = f;
        }
    }

    /**
     * Create a rect from a bottom-left position and width and height.
     */
    public static @Nonnull ScreenRect fromWidth(int x, int y, int width, int height) {
        return new ScreenRect(x, y, x + width, y + height);
    }

    /**
     * Create a rect centered on the given position with the given width and height.
     */
    public static @Nonnull ScreenRect fromWidthCentered(int x, int y, int width, int height) {
        return new ScreenRect(x - (width / 2), y - (height / 2), x + (width / 2), y + (height / 2));
    }

    /**
     * Get the "from" X position of this rect.
     */
    public int getFromX() {
        return startX;
    }

    /**
     * Get the "from" Y position of this rect.
     */
    public int getFromY() {
        return startY;
    }

    /**
     * Get the "to" X position of this rect.
     */
    public int getToX() {
        return endX;
    }

    /**
     * Get the "to" Y position of this rect.
     */
    public int getToY() {
        return endY;
    }

    /**
     * Get the width of this rect.
     */
    public int getWidth() {
        return getToX() - getFromX();
    }

    /**
     * Get the height of this rect.
     */
    public int getHeight() {
        return getToY() - getFromY();
    }

    /**
     * Get the top left position of this rect.
     */
    public @Nonnull Vector2i getTopLeft() {
        return new Vector2i(getFromX(), getFromY());
    }

    /**
     * Get the top right position of this rect.
     */
    public @Nonnull Vector2i getTopRight() {
        return new Vector2i(getToX(), getFromY());
    }

    /**
     * Get the bottom left position of this rect.
     */
    public @Nonnull Vector2i getBottomLeft() {
        return new Vector2i(getFromX(), getToY());
    }

    /**
     * Get the bottom right position of this rect.
     */
    public @Nonnull Vector2i getBottomRight() {
        return new Vector2i(getToX(), getToY());
    }

    /**
     * Get the center position of this rect.
     */
    public @Nonnull Vector2i getCenter() {
        return new Vector2i(Mth.floor(getFromX() + (getWidth() / 2f)), Mth.floor(getFromY() + (getHeight() / 2f)));
    }

    /**
     * Get whether the given position is within the bounds of this rect.
     */
    public boolean positionEnvelopes(double x, double y) {
        return (x >= getFromX() && x <= getToX() &&
                y >= getFromY() && y <= getToY());
    }

    /**
     * Get whether the given position is within the bounds of this rect.
     */
    public boolean positionEnvelopes(float x, float y) {
        return (x >= getFromX() && x <= getToX() &&
                y >= getFromY() && y <= getToY());
    }

    /**
     * Get whether the given position is within the bounds of this rect.
     */
    public boolean positionEnvelopes(int x, int y) {
        return (x >= getFromX() && x <= getToX() &&
                y >= getFromY() && y <= getToY());
    }

    /**
     * Convert this rect to a {@link ScreenCoordinate}
     * @return The {@link ScreenCoordinate} equivalent of this screen coordinate.
     */
    public @Nonnull ScreenCoordinate toScreenCoordinate() {
        return new ScreenCoordinate(getFromX(), getFromY(), getWidth(), getHeight());
    }

    /**
     * Convert the given {@link ScreenCoordinate} to a {@link ScreenRect}
     * @param coordinate The screen coordinate to convert.
     * @return The {@link ScreenRect} equivalent of the given {@link ScreenCoordinate}
     */
    public static @Nonnull ScreenRect fromScreenCoordinate(@Nonnull ScreenCoordinate coordinate) {
        return coordinate.toScreenRect();
    }

    /**
     * A default screen rect set from 0,0 to 1,1
     */
    public static final ScreenRect DEFAULT = new ScreenRect(0, 0, 1, 1);
}
