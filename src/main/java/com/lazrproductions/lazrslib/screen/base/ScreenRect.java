package com.lazrproductions.lazrslib.screen.base;

import com.lazrproductions.lazrslib.math.Vector2i;

import net.minecraft.util.Mth;

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

    public static ScreenRect fromWidth(int x, int y, int width, int height) {
        return new ScreenRect(x, y, x + width, y + height);
    }

    public static ScreenRect fromWidthCentered(int x, int y, int width, int height) {
        return new ScreenRect(x - (width / 2), y - (height / 2), x + (width / 2), y + (height / 2));
    }

    public int getFromX() {
        return startX;
    }

    public int getFromY() {
        return startY;
    }

    public int getToX() {
        return endX;
    }

    public int getToY() {
        return endY;
    }

    public int getWidth() {
        return getToX() - getFromX();
    }

    public int getHeight() {
        return getToY() - getFromY();
    }

    public Vector2i getTopLeft() {
        return new Vector2i(getFromX(), getFromY());
    }

    public Vector2i getTopRight() {
        return new Vector2i(getToX(), getFromY());
    }

    public Vector2i getBottomLeft() {
        return new Vector2i(getFromX(), getToY());
    }

    public Vector2i getBottomRight() {
        return new Vector2i(getToX(), getToY());
    }

    public Vector2i getCenter() {
        return new Vector2i(Mth.floor(getFromX() + (getWidth() / 2)), Mth.floor(getFromY() + (getHeight() / 2)));
    }

    public boolean positionEnvlopes(double x, double y) {
        return (x >= getFromX() && x <= getToX() &&
                y >= getFromY() && y <= getToY());
    }

    public boolean positionEnvlopes(float x, float y) {
        return (x >= getFromX() && x <= getToX() &&
                y >= getFromY() && y <= getToY());
    }

    public boolean positionEnvlopes(int x, int y) {
        return (x >= getFromX() && x <= getToX() &&
                y >= getFromY() && y <= getToY());
    }

    public BlitCoordinates toBlitCoordinates() {
        return new BlitCoordinates(getFromX(), getFromY(), getWidth(), getHeight());
    }
}
