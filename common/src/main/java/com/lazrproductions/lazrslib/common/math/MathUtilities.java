package com.lazrproductions.lazrslib.common.math;

import java.util.Random;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class MathUtilities {
    /**
     * Get a position at an angle (in radians) and distance from another position.
     * @param from The position to get a point from
     * @param yaw The yaw angle (y angle) <b>(in radians)</b> from which to get the point
     * @param pitch The pitch angle (x angle) <b>(in radians)</b> from which to get the point
     * @param distance The distance from the from position to get the point
     */
    public static Vec3 getPositionFromTowardsRotation(Vec3 from, double yaw, double pitch, double distance) {
        yaw = Mth.RAD_TO_DEG*yaw;
        pitch = Mth.RAD_TO_DEG*pitch;
        return getPositionFromTowardsRotationInDegrees(from, yaw, pitch, distance);
    }

    /**
     * Get a position at an angle (in degrees) and distance from another position.
     * @param from The position to get a point from
     * @param yaw The yaw angle (y angle) <b>(in degrees)</b> from which to get the point
     * @param pitch The pitch angle (x angle) <b>(in degrees)</b> from which to get the point
     * @param distance The distance from the from position to get the point
     */
    public static Vec3 getPositionFromTowardsRotationInDegrees(Vec3 from, double yaw, double pitch, double distance) {
        float f = Mth.cos(-(float)yaw * ((float)Math.PI / 180F) - (float)Math.PI);
        float f1 = Mth.sin(-(float)yaw * ((float)Math.PI / 180F) - (float)Math.PI);
        float f2 = -Mth.cos(-(float)pitch * ((float)Math.PI / 180F));
        float f3 = Mth.sin(-(float)pitch * ((float)Math.PI / 180F));
        return new Vec3((double)(f1 * f2) * distance, (double)f3 * distance, (double)(f * f2) * distance).add(from);
    }

    /**
     * Get a vector from a position towards a position.
     * @param from The position to get a point from
     * @param to The position to get a point to
     */
    public static Vec3 getVectorTowards(Vec3 from, Vec3 to) {
        return to.subtract(from).normalize();
    }

    /**
     * Get the yaw angle of a vector in radians
     */
    public static double getYawFromDelta(Vec3 delta) {
        return Math.atan2(delta.z, delta.x);

    }
    /**
     * Get the pitch of a vector in radians
     */
    public static double getPitchFromDelta(Vec3 delta) {
        return Math.atan2(Math.sqrt(delta.z * delta.z + delta.x * delta.x), delta.y) + Math.PI;
    }

    /**
     * Inverts a number in a range of min to max.
     * <br/>Example:
     * <code>
     *     invertRange(v = 4, min = 3, max = 7) = 6
     * </code>
     * @param v The value to invert
     * @param min The minimum value in the range
     * @param max The maximum value in the range
     */
    public static double invertRange(double v, double min, double max) {
        return -((v-min)-(max-min));
    }

    /**
     * Inverts a number in a range of 0 to 1.
     * @param v The value to invert
     */
    public static double invert01(double v) {
        return -(v-1);
    }

    /**
     * Get a random position in a 2D circle around a position.
     * @param pos The center of the position
     * @param radius The radius of the circle around which to get a postion.
     * @return A random position on the circle.
     */
    public static Vec3 getRandomPositionAroundPos(Vec3 pos, float radius) {
        Random r = new Random();
        float yaw = r.nextFloat()*360f;
        float distance = r.nextFloat()*radius;
        return getPositionFromTowardsRotation(pos, yaw, 0, distance);
    }
    /**
     * Get a random position in a 23 circle around a position.
     * @param pos The center of the position
     * @param radius The radius of the circle around which to get a postion.
     * @return A random position on the circle.
     */
    public static Vec3 getRandomPositionSphericallyAroundPos(Vec3 pos, float radius) {
        Random r = new Random();
        float yaw = r.nextFloat()*360f;
        float pitch = r.nextFloat()*360f;
        float distance = r.nextFloat()*radius;
        return getPositionFromTowardsRotation(pos, yaw, pitch, distance);
    }

    /**
     * Interpolate between two values by time
     * @param start The starting value to interpolate from.
     * @param end The ending value to interpolate to.
     * @param time The value between 0 and 1 to interpolate with.
     * @return The interpolated value between the two values.
     */
    public static float lerp(float start, float end, float time) {
        return start + (end - start) * time;
    }
    /**
     * Interpolate between two values by time
     * @param start The starting value to interpolate from.
     * @param end The ending value to interpolate to.
     * @param time The value between 0 and 1 to interpolate with.
     * @return The interpolated value between the two values.
     */
    public static double lerp(double start, double end, double time) {
        return start + (end - start) * time;
    }
    /**
     * Shorthand for <code>lerp(0f, 1f, time)</code>
     * @param time The value between 0 and 1 to interpolate with.
     * @return The interpolated value between the two values.
     */
    public static float lerp01(float time) {
        return lerp(0f, 1f, time);
    }
    /**
     * Shorthand for <code>lerp(0d, 1d, time)</code>
     * @param time The value between 0 and 1 to interpolate with.
     * @return The interpolated value between the two values.
     */
    public static double lerp01(double time) {
        return lerp(0d, 1d, time);
    }
}