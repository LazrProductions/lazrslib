package com.lazrproductions.lazrslib.math;

public class Vector2i {
    public int x;
    public int y;

    public Vector2i() {
    }

    public Vector2i(int s) {
        this.x = s;
        this.y = s;
    }

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2i(float x, float y) {
        this.x = Math.round(x);
        this.y = Math.round(y);
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public Vector2i set(int s) {
        this.x = s;
        this.y = s;
        return this;
    }

    public Vector2i set(int x, int y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2i set(int[] xy) {
        this.x = xy[0];
        this.y = xy[1];
        return this;
    }

    public int get(int component) throws IllegalArgumentException {
        switch (component) {
            case 0:
                return x;
            case 1:
                return y;
            default:
                throw new IllegalArgumentException();
        }
    }

    /**
     * Set the value of the specified component of this vector.
     *
     * @param component
     *                  the component whose value to set, within <code>[0..1]</code>
     * @param value
     *                  the value to set
     * @return this
     * @throws IllegalArgumentException if <code>component</code> is not within
     *                                  <code>[0..1]</code>
     */
    public Vector2i setComponent(int component, int value) throws IllegalArgumentException {
        switch (component) {
            case 0:
                x = value;
                break;
            case 1:
                y = value;
                break;
            default:
                throw new IllegalArgumentException();
        }
        return this;
    }

    public Vector2i sub(int x, int y) {
        this.x = this.x - x;
        this.y = this.y - y;
        return this;
    }

    public Vector2i sub(int x, int y, Vector2i dest) {
        dest.x = this.x - x;
        dest.y = this.y - y;
        return dest;
    }

    public long lengthSquared() {
        return x * x + y * y;
    }

    public static long lengthSquared(int x, int y) {
        return x * x + y * y;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public static double length(int x, int y) {
        return Math.sqrt(x * x + y * y);
    }

    public double distance(int x, int y) {
        int dx = this.x - x;
        int dy = this.y - y;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public long distanceSquared(int x, int y) {
        int dx = this.x - x;
        int dy = this.y - y;
        return dx * dx + dy * dy;
    }

    public long gridDistance(int x, int y) {
        return Math.abs(x - x()) + Math.abs(y - y());
    }

    public static double distance(int x1, int y1, int x2, int y2) {
        int dx = x1 - x2;
        int dy = y1 - y2;
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static long distanceSquared(int x1, int y1, int x2, int y2) {
        int dx = x1 - x2;
        int dy = y1 - y2;
        return dx * dx + dy * dy;
    }

    public Vector2i add(int x, int y) {
        this.x = this.x + x;
        this.y = this.y + y;
        return this;
    }

    public Vector2i add(int x, int y, Vector2i dest) {
        dest.x = this.x + x;
        dest.y = this.y + y;
        return dest;
    }

    public Vector2i mul(int scalar) {
        this.x = x * scalar;
        this.y = y * scalar;
        return this;
    }

    public Vector2i mul(int scalar, Vector2i dest) {
        dest.x = x * scalar;
        dest.y = y * scalar;
        return dest;
    }

    public Vector2i mul(int x, int y) {
        this.x = this.x * x;
        this.y = this.y * y;
        return this;
    }

    public Vector2i mul(int x, int y, Vector2i dest) {
        dest.x = this.x * x;
        dest.y = this.y * y;
        return dest;
    }

    public Vector2i div(float scalar) {
        float invscalar = 1.0f / scalar;
        this.x = (int) (x * invscalar);
        this.y = (int) (y * invscalar);
        return this;
    }

    public Vector2i div(float scalar, Vector2i dest) {
        float invscalar = 1.0f / scalar;
        dest.x = (int) (x * invscalar);
        dest.y = (int) (y * invscalar);
        return dest;
    }

    public Vector2i div(int scalar) {
        this.x = x / scalar;
        this.y = y / scalar;
        return this;
    }

    public Vector2i div(int scalar, Vector2i dest) {
        dest.x = x / scalar;
        dest.y = y / scalar;
        return dest;
    }

    public Vector2i zero() {
        this.x = 0;
        this.y = 0;
        return this;
    }

    public Vector2i negate() {
        this.x = -x;
        this.y = -y;
        return this;
    }

    public Vector2i negate(Vector2i dest) {
        dest.x = -x;
        dest.y = -y;
        return dest;
    }

    public int maxComponent() {
        int absX = Math.abs(x);
        int absY = Math.abs(y);
        if (absX >= absY)
            return 0;
        return 1;
    }

    public int minComponent() {
        int absX = Math.abs(x);
        int absY = Math.abs(y);
        if (absX < absY)
            return 0;
        return 1;
    }

    public Vector2i absolute() {
        this.x = Math.abs(this.x);
        this.y = Math.abs(this.y);
        return this;
    }

    public Vector2i absolute(Vector2i dest) {
        dest.x = Math.abs(this.x);
        dest.y = Math.abs(this.y);
        return dest;
    }

    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + x;
        result = prime * result + y;
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Vector2i other = (Vector2i) obj;
        if (x != other.x) {
            return false;
        }
        if (y != other.y) {
            return false;
        }
        return true;
    }

    public boolean equals(int x, int y) {
        if (this.x != x)
            return false;
        if (this.y != y)
            return false;
        return true;
    }
}
