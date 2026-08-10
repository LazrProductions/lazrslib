package com.lazrproductions.lazrslib.client.render;

import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import com.lazrproductions.lazrslib.client.overlay.OverlayUtilities.OverlayProperties;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.RenderType;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.phys.Vec3;

public class RenderUtilities {
    public static void drawViewstrumCube(@NotNull OverlayProperties p, float size, int color, float alpha,
                                         RenderType renderType) {
        drawVerticalSquare(p,
                new Vector3f(-size, -size, -size),
                new Vector3f(size, size, -size), color, alpha, renderType);
        drawVerticalSquare(p,
                new Vector3f(size, -size, -size),
                new Vector3f(size, size, size), color, alpha, renderType);
        drawVerticalSquareUp(p, size, color, alpha, renderType);

        drawInvertedVerticalSquare(p,
                new Vector3f(-size, -size, size),
                new Vector3f(size, size, size), color, alpha, renderType);
        drawInvertedVerticalSquare(p,
                new Vector3f(-size, -size, -size),
                new Vector3f(-size, size, size), color, alpha, renderType);
        drawVerticalSquareDown(p, size, color, alpha, renderType);
    }

    public static void drawVerticalSquareUp(@NotNull OverlayProperties p, float size, int color, float alpha,
                                            RenderType renderType) {
        PoseStack stack = p.getPoseStack();
        VertexConsumer consumer = p.getBufferSource().getBuffer(renderType);
        stack.pushPose();

        Matrix4f model = stack.last().pose();
        if (model != null) {
            float r = (float) ARGB32.red(color) / 255.0F;
            float g = (float) ARGB32.green(color) / 255.0F;
            float b = (float) ARGB32.blue(color) / 255.0F;

            consumer.addVertex(model, -size, -size, size)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, size, -size, size)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, size, -size, -size)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, -size, -size, -size)
                    .setColor(r, g, b, alpha);
        }

        p.getBufferSource().endBatch(renderType);
        stack.popPose();
    }

    public static void drawVerticalSquareDown(@NotNull OverlayProperties p, float size, int color, float alpha,
                                              RenderType renderType) {
        PoseStack stack = p.getPoseStack();
        VertexConsumer consumer = p.getBufferSource().getBuffer(renderType);
        stack.pushPose();

        Matrix4f model = stack.last().pose();
        if (model != null) {
            float r = (float) ARGB32.red(color) / 255.0F;
            float g = (float) ARGB32.green(color) / 255.0F;
            float b = (float) ARGB32.blue(color) / 255.0F;

            consumer.addVertex(model, -size, size, -size)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, size, size, -size)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, size, size, size)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, -size, size, size)
                    .setColor(r, g, b, alpha);
        }

        p.getBufferSource().endBatch(renderType);
        stack.popPose();
    }

    public static void drawVerticalSquare(@NotNull OverlayProperties p, Vector3f from, Vector3f to, int color,
                                          float alpha,
                                          RenderType renderType) {
        PoseStack stack = p.getPoseStack();
        VertexConsumer consumer = p.getBufferSource().getBuffer(renderType);
        stack.pushPose();

        Matrix4f model = stack.last().pose();
        if (model != null) {
            float r = (float) ARGB32.red(color) / 255.0F;
            float g = (float) ARGB32.green(color) / 255.0F;
            float b = (float) ARGB32.blue(color) / 255.0F;

            consumer.addVertex(model, from.x, from.y, from.z)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, to.x, from.y, to.z)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, to.x, to.y, to.z)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, from.x, to.y, from.z)
                    .setColor(r, g, b, alpha);
        }

        p.getBufferSource().endBatch(renderType);
        stack.popPose();
    }

    public static void drawInvertedVerticalSquare(@NotNull OverlayProperties p, Vector3f from, Vector3f to,
                                                  int color,
                                                  float alpha,
                                                  RenderType renderType) {
        PoseStack stack = p.getPoseStack();
        VertexConsumer consumer = p.getBufferSource().getBuffer(renderType);
        stack.pushPose();

        Matrix4f model = stack.last().pose();
        if (model != null) {
            float r = (float) ARGB32.red(color) / 255.0F;
            float g = (float) ARGB32.green(color) / 255.0F;
            float b = (float) ARGB32.blue(color) / 255.0F;

            consumer.addVertex(model, from.x, to.y, from.z)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, to.x, to.y, to.z)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, to.x, from.y, to.z)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, from.x, from.y, from.z)
                    .setColor(r, g, b, alpha);
        }

        p.getBufferSource().endBatch(renderType);
        stack.popPose();
    }

    public static void drawWorldSquareSide(@NotNull OverlayProperties p, Vector3f from, Vector3f to, int color,
                                           float alpha,
                                           RenderType renderType) {
        PoseStack stack = p.getPoseStack();

        Vec3 cameraPos = p.getCameraPos();
        VertexConsumer consumer = p.getBufferSource().getBuffer(renderType);
        stack.pushPose();

        stack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        Matrix4f model = stack.last().pose();
        if (model != null) {
            float r = (float) ARGB32.red(color) / 255.0F;
            float g = (float) ARGB32.green(color) / 255.0F;
            float b = (float) ARGB32.blue(color) / 255.0F;

            consumer.addVertex(model, from.x, from.y, from.z)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, to.x, from.y, to.z)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, to.x, to.y, to.z)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, from.x, to.y, from.z)
                    .setColor(r, g, b, alpha);
        }

        p.getBufferSource().endBatch(renderType);
        stack.popPose();
    }

    public static void drawWorldSquareSideInverted(@NotNull OverlayProperties p, Vector3f from, Vector3f to,
                                                   int color,
                                                   float alpha,
                                                   RenderType renderType) {
        PoseStack stack = p.getPoseStack();

        Vec3 cameraPos = p.getCameraPos();
        VertexConsumer consumer = p.getBufferSource().getBuffer(renderType);
        stack.pushPose();

        stack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        Matrix4f model = stack.last().pose();
        if (model != null) {
            float r = (float) ARGB32.red(color) / 255.0F;
            float g = (float) ARGB32.green(color) / 255.0F;
            float b = (float) ARGB32.blue(color) / 255.0F;

            consumer.addVertex(model, from.x, to.y, from.z)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, to.x, to.y, to.z)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, to.x, from.y, to.z)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, from.x, from.y, from.z)
                    .setColor(r, g, b, alpha);
        }

        p.getBufferSource().endBatch(renderType);
        stack.popPose();
    }

    public static void drawWorldSquareSideUp(@NotNull OverlayProperties p, Vector3f from, Vector3f to, int color,
                                             float alpha,
                                             RenderType renderType) {
        PoseStack stack = p.getPoseStack();

        Vec3 cameraPos = p.getCameraPos();
        VertexConsumer consumer = p.getBufferSource().getBuffer(renderType);
        stack.pushPose();

        stack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        Matrix4f model = stack.last().pose();
        if (model != null) {
            float r = (float) ARGB32.red(color) / 255.0F;
            float g = (float) ARGB32.green(color) / 255.0F;
            float b = (float) ARGB32.blue(color) / 255.0F;

            consumer.addVertex(model, from.x, from.y, to.z)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, to.x, from.y, to.z)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, to.x, from.y, from.z)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, from.x, from.y, from.z)
                    .setColor(r, g, b, alpha);
        }

        p.getBufferSource().endBatch(renderType);
        stack.popPose();
    }

    public static void drawWorldSquareSideDown(@NotNull OverlayProperties p, Vector3f from, Vector3f to, int color,
                                               float alpha,
                                               RenderType renderType) {
        PoseStack stack = p.getPoseStack();

        Vec3 cameraPos = p.getCameraPos();
        VertexConsumer consumer = p.getBufferSource().getBuffer(renderType);
        stack.pushPose();

        stack.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);

        Matrix4f model = stack.last().pose();
        if (model != null) {
            float r = (float) ARGB32.red(color) / 255.0F;
            float g = (float) ARGB32.green(color) / 255.0F;
            float b = (float) ARGB32.blue(color) / 255.0F;

            consumer.addVertex(model, from.x, from.y, from.z)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, to.x, from.y, from.z)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, to.x, from.y, to.z)
                    .setColor(r, g, b, alpha);
            consumer.addVertex(model, from.x, from.y, to.z)
                    .setColor(r, g, b, alpha);
        }

        p.getBufferSource().endBatch(renderType);
        stack.popPose();
    }

    public static void drawWorldCube(@NotNull OverlayProperties p, Vector3f from, Vector3f to, int color,
                                     float alpha,
                                     RenderType renderType) {
        drawWorldSquareSide(p, new Vector3f(from.x(), from.y(), to.z()), new Vector3f(to.x(), to.y(), to.z()),
                0xFFFFFF,
                1,
                RenderType.gui());
        drawWorldSquareSideInverted(p, new Vector3f(from.x(), from.y(), from.z()),
                new Vector3f(to.x(), to.y(), from.z()), 0xFFFFFF,
                1,
                RenderType.gui());
        drawWorldSquareSide(p, new Vector3f(from.x(), from.y(), from.z()),
                new Vector3f(from.x(), to.y(), to.z()),
                0xFFFFFF,
                1,
                RenderType.gui());
        drawWorldSquareSideInverted(p, new Vector3f(to.x(), from.y(), from.z()),
                new Vector3f(to.x(), to.y(), to.z()),
                0xFFFFFF,
                1,
                RenderType.gui());

        drawWorldSquareSideUp(p, new Vector3f(from.x(), to.y(), from.z()), new Vector3f(to.x(), to.y(), to.z()),
                0xFFFFFF,
                1,
                RenderType.gui());

        drawWorldSquareSideDown(p, new Vector3f(from.x(), from.y(), from.z()),
                new Vector3f(to.x(), from.y(), to.z()),
                0xFFFFFF,
                1,
                RenderType.gui());
    }
}
