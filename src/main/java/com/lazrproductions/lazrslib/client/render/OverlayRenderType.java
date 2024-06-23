package com.lazrproductions.lazrslib.client.render;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat.Mode;

import net.minecraft.client.renderer.RenderType;

public class OverlayRenderType extends RenderType {

    public OverlayRenderType(String nameIn, VertexFormat formatIn, Mode drawMode, int bufferSizeIn,
            boolean useDelegateIn, boolean needsSortingIn, Runnable setupTaskIn, Runnable clearTaskIn) {
        super(nameIn, formatIn, drawMode, bufferSizeIn, useDelegateIn, needsSortingIn, setupTaskIn, clearTaskIn);
    }

    public static RenderType overlaySolidRenderType() {
        return create("lines_no_depth",
                DefaultVertexFormat.POSITION_COLOR_NORMAL, Mode.TRIANGLE_STRIP, 256, false, false,
                CompositeState.builder()
                        .setShaderState(RENDERTYPE_LINES_SHADER)
                        .setLayeringState(VIEW_OFFSET_Z_LAYERING)
                        .setTransparencyState(NO_TRANSPARENCY)
                        .setOutputState(ITEM_ENTITY_TARGET)
                        .setWriteMaskState(COLOR_DEPTH_WRITE)
                        .setCullState(CULL)
                        .setDepthTestState(NO_DEPTH_TEST)
                        .createCompositeState(false));
    }
}