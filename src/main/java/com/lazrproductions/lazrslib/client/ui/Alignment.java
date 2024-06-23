package com.lazrproductions.lazrslib.client.ui;

import com.lazrproductions.lazrslib.client.screen.base.BlitCoordinates;
import com.lazrproductions.lazrslib.client.screen.base.ScreenRect;
import com.lazrproductions.lazrslib.common.math.Vector2i;

public enum Alignment {
    TOP_LEFT,
    TOP_MIDLE,
    TOP_RIGHT,
    CENTER_LEFT,
    CENTER,
    CENTER_RIGHT,
    BOTTOM_LEFT,
    BOTTOM_MIDDLE,
    BOTTOM_RIGHT;

    public BlitCoordinates fitToArea(BlitCoordinates area, int width, int height) {
        ScreenRect r = area.toRect();
        Vector2i p = new Vector2i(0, 0);
        switch (this) {
            default:
                p = r.getTopLeft();
                return new BlitCoordinates(p.x(), p.y(), width, height);
            case TOP_MIDLE:
                p = r.getCenter();
                return new BlitCoordinates(p.x() - width / 2, area.getY(), width, height);
            case TOP_RIGHT:
                p = r.getTopRight();
                return new BlitCoordinates(p.x() - width, p.y(), width, height);
            case CENTER_LEFT:
                p = r.getCenter();
                return new BlitCoordinates(area.getX(), p.y() - (height / 2), width, height);
            case CENTER:
                p = r.getCenter();
                return new BlitCoordinates(p.x() - (width / 2), p.y() - (height / 2), width, height);
            case CENTER_RIGHT:
                p = r.getCenter();
                return new BlitCoordinates(r.getToX() - width, p.y() - (height / 2), width, height);
            case BOTTOM_LEFT:
                p = r.getBottomLeft();
                return new BlitCoordinates(p.x(), p.y() - height, width, height);
            case BOTTOM_MIDDLE:
                p = r.getCenter();
                return new BlitCoordinates(p.x() - (width / 2), r.getToY() - height, width, height);
            case BOTTOM_RIGHT:
                p = r.getBottomRight();
                return new BlitCoordinates(p.x() - width, p.y() - height, width, height);
        }
    }
}
