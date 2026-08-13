package com.lazrproductions.lazrslib.client.screen.base;

import javax.annotation.Nonnull;

/**
 * A representation of texture coordinates on the screen.
 * <br/><br/>
 * The coordinates
 */
public class ScreenCoordinate {
    private int x, y;
    private int width, height;
    private Pivot pivot;

    public ScreenCoordinate(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.pivot = Pivot.DEFAULT;
    }
    public ScreenCoordinate(int x, int y, int width, int height, @Nonnull Pivot pivot) {
        if(pivot == Pivot.CENTER) {
            this.x = (int)(x - width / 2f);
            this.y = (int)(y - height / 2f);
        } else {
            this.x = x;
            this.y = y;
        }
        this.width = width;
        this.height = height;

        this.pivot = pivot;
    }

    /**
     * Get the X position of this coordinate on the screen, in pixels.
     */
    public int getX() {
        if (pivot == Pivot.CENTER)
            return x - (int)(width / 2f);
        return x;
    }

    /**
     * Get the Y position of this coordinate on the screen, in pixels.
     */
    public int getY() {
        if (pivot == Pivot.CENTER)
            return y - (int)(height / 2f);
        return y;
    }

    /**
     * Get the width of this coordinate on the screen, in pixels.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get the height of this coordinate on the screen, in pixels.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Get the pivot of this coordinate.
     */
    public @Nonnull Pivot getPivot() {
        return pivot;
    }

    /**
     * Get a copy of this coordinate moved by the given offset.
     * @param offsetX The X offset to add to this coordinate's position.
     * @param offsetY The Y offset to add to this coordinate's position.
     * @return A copy of this screen coordinate, offset by the given amount.
     */
    public @Nonnull ScreenCoordinate move(int offsetX, int offsetY) {
        return new ScreenCoordinate(getX() + offsetX, getY() + offsetY, getWidth(), getHeight());
    }

    /**
     * Set the X position of this coordinate.
     * @param value The new X position to set.
     * @return This screen coordinate, with the new X position.
     */
    public @Nonnull ScreenCoordinate withX(int value) {
        this.x = value;
        return this;
    }

    /**
     * Set the Y position of this coordinate.
     * @param value The new Y position to set.
     * @return This screen coordinate, with the new Y position.
     */
    public @Nonnull ScreenCoordinate withY(int value) {
        this.y = value;
        return this;
    }

    /**
     * Set the width of this coordinate.
     * @param value The new width to set.
     * @return This screen coordinate, with the new width.
     */
    public @Nonnull ScreenCoordinate withWidth(int value) {
        this.width = value;
        return this;
    }

    /**
     * Set the height of this coordinate.
     * @param value The new height to set.
     * @return This screen coordinate, with the new height.
     */
    public @Nonnull ScreenCoordinate withHeight(int value) {
        this.height = value;
        return this;
    }

    /**
     * Set the pivot of this coordinate.
     * @param value The new pivot to set.
     * @return This screen coordinate, with the new pivot.
     */
    public @Nonnull ScreenCoordinate withAlignment(@Nonnull Pivot value) {
        this.pivot = value;
        return this;
    }

    /**
     * Convert this coordinate coordinates to a {@link ScreenRect}
     * @return The {@link ScreenRect} equivalent of this screen coordinate.
     */
    public @Nonnull ScreenRect toScreenRect() {
        if (pivot == Pivot.CENTER) {
            return ScreenRect.fromWidthCentered(x, y, width, height);
        }
        return ScreenRect.fromWidth(x, y, width, height);
    }

    public enum Pivot {
        DEFAULT,
        CENTER
    }

    /**
     * Convert the given {@link ScreenRect} to a {@link ScreenCoordinate}
     * @param rect The screen rect to convert.
     * @return The {@link ScreenCoordinate} equivalent of the given {@link ScreenRect}
     */
    public static @Nonnull ScreenCoordinate fromRect(@Nonnull ScreenRect rect) {
        return rect.toScreenCoordinate();
    }

    /**
     * A default screen coordinate set at 0,0 with a width and height of 1.
     */
    public static final ScreenCoordinate DEFAULT = new ScreenCoordinate(0, 0, 1, 1);
}
