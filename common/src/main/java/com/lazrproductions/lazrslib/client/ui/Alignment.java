package com.lazrproductions.lazrslib.client.ui;

import com.lazrproductions.lazrslib.client.screen.base.ScreenCoordinate;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector2i;

import com.lazrproductions.lazrslib.client.screen.base.ScreenRect;

/**
 * An alignment used to justify elements within an area.
 */
public enum Alignment {
    /** Align to the top-left of the given area. */
    TOP_LEFT,
    /** Align to the top-middle of the given area. */
    TOP_MIDDLE,
    /** Align to the top-right of the given area. */
    TOP_RIGHT,
    /** Align to the center-left of the given area. */
    CENTER_LEFT,
    /** Align to the center of the given area. */
    CENTER,
    /** Align to the center-right of the given area. */
    CENTER_RIGHT,
    /** Align to the bottom-left of the given area. */
    BOTTOM_LEFT,
    /** Align to the bottom-middle of the given area. */
    BOTTOM_MIDDLE,
    /** Align to the bottom-right of the given area. */
    BOTTOM_RIGHT;

    /**
     * Get the {@link ScreenCoordinate} for an element with the given width and height aligned accordingly within the given area.
     * @param area The area to align within.
     * @param width The width of the element to align.
     * @param height The height of the element to align.
     * @return The aligned {@link ScreenCoordinate}.
     */
    public ScreenCoordinate fitToArea(@NotNull ScreenCoordinate area, int width, int height) {
        ScreenRect r = area.toScreenRect();
        Vector2i p = new Vector2i(0, 0);
        return switch (this) {
            case TOP_MIDDLE -> {
                p = r.getCenter();
                yield new ScreenCoordinate(p.x() - width / 2, area.getY(), width, height);
            }
            case TOP_RIGHT -> {
                p = r.getTopRight();
                yield new ScreenCoordinate(p.x() - width, p.y(), width, height);
            }
            case CENTER_LEFT -> {
                p = r.getCenter();
                yield new ScreenCoordinate(area.getX(), p.y() - (height / 2), width, height);
            }
            case CENTER -> {
                p = r.getCenter();
                yield new ScreenCoordinate(p.x() - (width / 2), p.y() - (height / 2), width, height);
            }
            case CENTER_RIGHT -> {
                p = r.getCenter();
                yield new ScreenCoordinate(r.getToX() - width, p.y() - (height / 2), width, height);
            }
            case BOTTOM_LEFT -> {
                p = r.getBottomLeft();
                yield new ScreenCoordinate(p.x(), p.y() - height, width, height);
            }
            case BOTTOM_MIDDLE -> {
                p = r.getCenter();
                yield new ScreenCoordinate(p.x() - (width / 2), r.getToY() - height, width, height);
            }
            case BOTTOM_RIGHT -> {
                p = r.getBottomRight();
                yield new ScreenCoordinate(p.x() - width, p.y() - height, width, height);
            }
            default -> {
                p = r.getTopLeft();
                yield new ScreenCoordinate(p.x(), p.y(), width, height);
            }
        };
    }
}
