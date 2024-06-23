package com.lazrproductions.lazrslib.client.screen.base;

public class BlitCoordinates {
    private int x, y;
    private int width, height;
    private Alignment alignment;

    public BlitCoordinates(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.alignment = Alignment.DEFAULT;
    }

    public int getX() {
        switch (alignment) {
            case CENTER:
                return x + (width / 2);
            default:
                return x;
        }
    }

    public int getY() {
        switch (alignment) {
            case CENTER:
                return y + (height / 2);
            default:
                return y;
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BlitCoordinates move(int offsetX, int offsetY) {
        return new BlitCoordinates(getX() + offsetX, getY() + offsetY, getWidth(), getHeight());
    }

    public BlitCoordinates withX(int value) {
        this.x = value;
        return this;
    }

    public BlitCoordinates withY(int value) {
        this.y = value;
        return this;
    }

    public BlitCoordinates withWidth(int value) {
        this.width = value;
        return this;
    }

    public BlitCoordinates withHeight(int value) {
        this.height = value;
        return this;
    }

    public ScreenRect toRect() {
        switch (alignment) {
            case CENTER:
                return ScreenRect.fromWidthCentered(x, y, width, height);
            default:
                return ScreenRect.fromWidth(x, y, width, height);
        }
    }

    static enum Alignment {
        DEFAULT,
        CENTER
    }

    public static BlitCoordinates fromRect(ScreenRect rect) {
        return rect.toBlitCoordinates();
    }

    public static final BlitCoordinates DEFAULT = new BlitCoordinates(0, 0, 1, 1);
}
