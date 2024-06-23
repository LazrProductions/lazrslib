package com.lazrproductions.lazrslib.client.overlay;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.client.render.OverlayRenderType;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Quaternion;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.RenderBuffers;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class OverlayUtilities {
    /**
     * Draw a label in the world.
     */
    public static void drawLabel(@Nonnull OverlayProperties p, Vec3 pos, Component text, float textScale,
            boolean drawShadow) {
        Minecraft inst = Minecraft.getInstance();
        if (inst != null) {
            PoseStack stack = p.getPoseStack();
            Vec3 cameraPos = p.getCameraPos();

            Quaternion cameraRot = p.getCameraRotation();
            Font font = inst.font;
            float scale = textScale / 50;
            BufferSource bufferSource = p.getBufferSource();
            p.getConsumerForLines();
            stack.pushPose();
            stack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
            stack.translate(pos.x(), pos.y() + (font.lineHeight / 2) * scale, pos.z());
            stack.mulPose(cameraRot);
            stack.scale(-scale, -scale, -scale);
            stack.translate(-font.width(text) / 2f, 0, 0);

            Matrix4f pose = stack.last().pose();
            TextColor col = text.getStyle().getColor();
            int color = col != null ? col.getValue() : 0xFFFFFFFF;
            font.drawInBatch(text, 0, 0, color, drawShadow, pose, bufferSource, false, 0, 15728880);

            p.getBufferSource().endBatch();
            stack.popPose();
        }
    }

    /**
     * Draw a horizontal grid with the given width and length.
     */
    public static void drawGrid(@Nonnull OverlayProperties p, Vec3 pos, int width, int length, float cellSize,
            int color) {
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < length; z++) {
                float pX = x * cellSize;
                float pZ = z * cellSize;
                drawWireSquare(p, pos.add(pX, 0, pZ), cellSize, color);
            }
        }
    }

    /**
     * Draw a horizontal grid with the given width and length, centered on a point,
     */
    public static void drawGridCentered(@Nonnull OverlayProperties p, Vec3 center, int width, int length,
            float cellSize, int color) {
        drawGrid(p, center.subtract(width * cellSize, 0, length * cellSize), width, length, cellSize, color);
    }

    /**
     * Draw a 3 dimensional grid with the given dimensions and cell dimensions.
     */
    public static void drawGrid(@Nonnull OverlayProperties p, Vec3 pos, Vec3i dimensions, Vec3 cellDimensions,
            int color) {
        double pX = pos.x();
        double pY = pos.y();
        double pZ = pos.z();
        for (int cellX = 0; cellX < dimensions.getX(); cellX++) {
            for (int cellY = 0; cellY < dimensions.getY(); cellY++) {
                for (int cellZ = 0; cellZ < dimensions.getZ(); cellZ++) {
                    drawWireBox(p, new Vec3(pX, pY, pZ), cellDimensions, color);
                    pZ += cellDimensions.z();
                }
                pY += cellDimensions.y();
                pZ = pos.z();
            }
            pX += cellDimensions.x();
            pY = pos.y();
        }
    }

    /**
     * Draw a cube with the given size.
     */
    public static void drawWireCube(@Nonnull OverlayProperties p, Vec3 pos, float size, int color) {
        // bottom
        drawWireSquare(p,
                pos,
                size,
                color);

        // top
        drawWireSquare(p,
                pos.add(0, size, 0),
                size,
                color);

        // North East
        drawLine(p,
                pos.add(0, 0, size),
                pos.add(0, size, size),
                color);

        // North West
        drawLine(p,
                pos.add(size, 0, size),
                pos.add(size, size, size),
                color);

        // South East
        drawLine(p,
                pos.add(0, 0, 0),
                pos.add(0, size, 0),
                color);

        // South West
        drawLine(p,
                pos.add(size, 0, 0),
                pos.add(size, size, 0),
                color);
    }

    /**
     * Draw a box with the given dimensions.
     */
    public static void drawWireBox(@Nonnull OverlayProperties p, Vec3 pos, Vec3 dimensions, int color) {
        double width = dimensions.x();
        double height = dimensions.y();
        double length = dimensions.z();

        // bottom face
        if (width > 0)
            drawLine(p,
                    pos.add(0, 0, 0),
                    pos.add(width, 0, 0),
                    color);

        if (length > 0) {
            if (width > 0) {
                drawLine(p,
                        pos.add(width, 0, 0),
                        pos.add(width, 0, length),
                        color);
                drawLine(p,
                        pos.add(0, 0, length),
                        pos.add(width, 0, length),
                        color);
            }
            drawLine(p,
                    pos.add(0, 0, 0),
                    pos.add(0, 0, length),
                    color);
        }

        // top face
        if (height > 0) {
            if (width > 0)
                drawLine(p,
                        pos.add(0, height, 0),
                        pos.add(width, height, 0),
                        color);

            if (length > 0) {
                if (width > 0) {
                    drawLine(p,
                            pos.add(width, height, 0),
                            pos.add(width, height, length),
                            color);
                    drawLine(p,
                            pos.add(0, height, length),
                            pos.add(width, height, length),
                            color);
                }

                drawLine(p,
                        pos.add(0, height, 0),
                        pos.add(0, height, length),
                        color);
            }

            // North East
            if (length > 0)
                drawLine(p,
                        pos.add(0, 0, length),
                        pos.add(0, height, length),
                        color);

            // North West
            if (width > 0 && length > 0)
                drawLine(p,
                        pos.add(width, 0, length),
                        pos.add(width, height, length),
                        color);

            // South East
            drawLine(p,
                    pos.add(0, 0, 0),
                    pos.add(0, height, 0),
                    color);

            // South West
            if (width > 0)
                drawLine(p,
                        pos.add(width, 0, 0),
                        pos.add(width, height, 0),
                        color);
        }

    }

    /**
     * Draw a square with the given size.
     */
    public static void drawWireSquare(@Nonnull OverlayProperties p, Vec3 pos, float size, int color) {
        drawLine(p,
                pos.add(0, 0, 0),
                pos.add(size, 0, 0),
                color);
        drawLine(p,
                pos.add(size, 0, 0),
                pos.add(size, 0, size),
                color);
        drawLine(p,
                pos.add(0, 0, 0),
                pos.add(0, 0, size),
                color);
        drawLine(p,
                pos.add(0, 0, size),
                pos.add(size, 0, size),
                color);
    }

    /**
     * Draw a square with the given size, centered on a point.
     */
    public static void drawWireSquareCentered(@Nonnull OverlayProperties p, Vec3 pos, float size, int color) {
        drawWireSquare(p, pos.subtract(size / 2, 0, size / 2), size, color);

    }

    /**
     * Draw a line from one point to another.
     */
    public static void drawLine(@Nonnull OverlayProperties p, Vec3 from, Vec3 to, int color) {
        drawLine(p,
                (float) from.x(), (float) from.y(), (float) from.z(),
                (float) to.x(), (float) to.y(), (float) to.z(),
                color);
    }

    /**
     * Draw a line from one point to another.
     */
    public static void drawLine(@Nonnull OverlayProperties p, float fromX, float fromY, float fromZ, float toX,
            float toY,
            float toZ, int color) {
        PoseStack stack = p.getPoseStack();
        Vec3 cameraPos = p.getCameraPos();
        VertexConsumer consumer = p.getConsumerForLines();

        stack.pushPose();
        stack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        Matrix4f model = stack.last().pose();
        if (model != null) {
            float red = (float) FastColor.ARGB32.red(color) / 255.0F;
            float green = (float) FastColor.ARGB32.green(color) / 255.0F;
            float blue = (float) FastColor.ARGB32.blue(color) / 255.0F;

            consumer.vertex(model, fromX, fromY, fromZ).color(red, green, blue, 1)
                    .endVertex();
            consumer.vertex(model, toX, toY, toZ).color(red, green, blue, 1)
                    .endVertex();
        }

        p.getBufferSource().endBatch();
        stack.popPose();
    }

    public static class OverlayProperties {
        final Camera camera;
        final RenderBuffers buffers;
        final PoseStack stack;

        public OverlayProperties(@Nonnull Camera camera, @Nonnull PoseStack stack, @Nonnull RenderBuffers buffers) {
            this.camera = camera;
            this.buffers = buffers;
            this.stack = stack;
        }

        public Vec3 getCameraPos() {
            return camera.getPosition();
        }

        public Quaternion getCameraRotation() {
            return camera.rotation();
        }

        public Entity getCameraEntity() {
            return camera.getEntity();
        }

        public VertexConsumer getConsumerForLines() {
            final RenderType r = OverlayRenderType.overlayLineRenderType(4);
            return buffers.bufferSource().getBuffer(r);
        }

        public VertexConsumer getConsumerForSolid() {
            final RenderType r = OverlayRenderType.overlaySolidRenderType();
            return buffers.bufferSource().getBuffer(r);
        }

        public BufferSource getBufferSource() {
            return buffers.bufferSource();
        }

        public PoseStack getPoseStack() {
            return stack;
        }
    }
}
