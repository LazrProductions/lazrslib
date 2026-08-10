package com.lazrproductions.lazrslib.client.screen;

import com.lazrproductions.lazrslib.client.screen.base.ScreenCoordinate;
import com.lazrproductions.lazrslib.client.screen.base.ScreenRect;
import com.lazrproductions.lazrslib.client.screen.base.ScreenTexture;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.math.Axis;

import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;

public class ScreenUtilities {
    /**
     * Draw a texture to the screen.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param texture The texture to draw.
     */
    public static void drawTexture(@NotNull GuiGraphics graphics, @NotNull ScreenCoordinate pos, @NotNull ScreenTexture texture) {
        graphics.blit(texture.getResourceLocation(), pos.getX(), pos.getY(), pos.getWidth(), pos.getHeight(),
                texture.getU(), texture.getV(), texture.getBoundsX(), texture.getBoundsY(), texture.getWidth(),
                texture.getHeight());
    }
    /**
     * Draw a texture to the screen.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param rotation The rotation to draw the texture at.
     * @param texture The texture to draw.
     */
    public static void drawTexture(@NotNull GuiGraphics graphics, @NotNull ScreenCoordinate pos, float rotation, @NotNull ScreenTexture texture) {
        drawTexture(graphics, pos, rotation, ((float) pos.getWidth() / 2), ((float) pos.getHeight() / 2), texture);
    }
    /**
     * Draw a texture to the screen.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param rotation The rotation to draw the texture at.
     * @param rotateAroundX The X offset of the rotation pivot point, in pixels.
     * @param rotateAroundY The Y offset of the rotation pivot point, in pixels.
     * @param texture The texture to draw.
     */
    public static void drawTexture(@NotNull GuiGraphics graphics, @NotNull ScreenCoordinate pos, float rotation, float rotateAroundX, float rotateAroundY, @NotNull ScreenTexture texture) {
        graphics.pose().pushPose();
        graphics.pose().rotateAround(Axis.ZP.rotationDegrees(rotation), pos.getX() + rotateAroundX, pos.getY() + rotateAroundY, 0);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();

        graphics.blit(texture.getResourceLocation(), pos.getX(), pos.getY(), pos.getWidth(), pos.getHeight(),
                texture.getU(), texture.getV(), texture.getBoundsX(), texture.getBoundsY(), texture.getWidth(),
                texture.getHeight());

        RenderSystem.enableDepthTest();
        graphics.pose().popPose();
    }


    /**
     * Draw a progress bar with the given sprite map.
     * <br/>
     * Will sample the sprite map from top-left to bottom-right, sampling horizontally first before moving down a row.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param texture The sprite map texture to sample from as the progress bar's texture.
     * @param progress The current progress of the bar as a percentage between 0 and 1.
     * @param totalFramesHorizontal The total amount of frames stacked horizontally.
     * @param totalFramesVertically The total amount of frames stacked vertically.
     */
    public static void drawProgressBar(@NotNull GuiGraphics graphics, @NotNull ScreenCoordinate pos, @NotNull ScreenTexture texture, float progress, int totalFramesHorizontal, int totalFramesVertically) {

        int frames = (totalFramesHorizontal * totalFramesVertically);
        int frameIndex = Mth.floor((float) frames * progress);
        int column = Mth.floor((float) frameIndex % (float) totalFramesHorizontal);
        int row = Mth.floor((float) frameIndex / (float) totalFramesHorizontal);

        float uvXF = texture.getU() + (column * texture.getBoundsX());
        float uvYF = texture.getV() + (row * texture.getBoundsY());

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();

        graphics.blit(texture.getResourceLocation(), pos.getX(), pos.getY(), pos.getWidth(), pos.getHeight(), uvXF, uvYF, texture.getBoundsX(), texture.getBoundsY(), texture.getWidth(), texture.getHeight());

        RenderSystem.enableDepthTest();
    }
    /**
     * Draw a generic progress bar, similar in look to the durability bar.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param progress The current progress of the bar as a percentage between 0 and 1.
     */
    public static void drawGenericProgressBar(@NotNull GuiGraphics graphics, @NotNull ScreenCoordinate pos, float progress) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();

        graphics.fill(pos.getX(), pos.getY(), pos.getX() + pos.getWidth(), pos.getY() + 2, 1325400064);
        int i = Mth.hsvToRgb(progress / 3.0F, 1.0F, 1.0F);
        graphics.fill(pos.getX(), pos.getY(), pos.getX() + (int) (pos.getWidth() * progress), pos.getY() + 1, i | -16777216);

        RenderSystem.enableDepthTest();
    }
    /**
     * Draw a generic progress bar that fills vertically, from bottom to top.
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param progress The current progress of the bar as a percentage between 0 and 1.
     *
     */
    public static void drawGenericProgressBarVertical(@NotNull GuiGraphics graphics, @NotNull ScreenCoordinate pos, float progress) {
        progress = Mth.clamp(progress, 0, 2);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();

        graphics.fill(pos.getX(), pos.getY(), pos.getX() + 2, pos.getY() + pos.getHeight(), 1325400064);
        int i = Mth.hsvToRgb(progress / 3.0F, 1.0F, 1.0F);
        progress = Mth.clamp(progress, 0, 1f);
        graphics.fill(pos.getX(), pos.getY() + pos.getHeight(), pos.getX() + 1, pos.getY() + pos.getHeight() - (int) (pos.getHeight() * progress), i | -16777216);

        RenderSystem.enableDepthTest();
    }
    /**
     * Draw a generic progress bar that fills vertically and shakes more and more as it fills..
     * @param graphics The GuiGraphics instance to draw to.
     * @param pos The blit coordinates to draw at.
     * @param progress The current progress of the bar as a percentage between 0 and 1.
     * @param partialTick The current partial tick.
     */
    public static void drawShakingProgressBarVertical(@NotNull GuiGraphics graphics, @NotNull ScreenCoordinate pos, float progress, float partialTick) {
        progress = Mth.clamp(progress, 0, 2);

        int shakeX = 0;
        if(progress > 1)
            shakeX = Mth.floor(Mth.sin(partialTick*(100f*(progress - 1)) * 2f)-1);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();

        graphics.fill(pos.getX() + shakeX, pos.getY(), pos.getX() + 2 + shakeX, pos.getY() + pos.getHeight(), 1325400064);
        int i = Mth.hsvToRgb(progress / 3.0F, 1.0F, 1.0F);
        progress = Mth.clamp(progress, 0, 1f);
        graphics.fill(pos.getX() + shakeX, pos.getY() + pos.getHeight(), pos.getX() + 1 + shakeX, pos.getY() + pos.getHeight() - (int) (pos.getHeight() * progress), i | -16777216);

        RenderSystem.enableDepthTest();
    }


    /**
     * Draw an item stack to the screen.
     * @param instance The current Minecraft instance.
     * @param graphics The GuiGraphics instance to draw to.
     * @param stack The item stack to draw.
     * @param pos The blit coordinates to draw the item at.
     */
    public static void drawItemStack(@NotNull Minecraft instance, @NotNull GuiGraphics graphics, @NotNull ItemStack stack, ScreenCoordinate pos) {
        drawItemStack(instance, graphics, stack, pos.getX(), pos.getY());
    }
    /**
     * Draw an item stack to the screen.
     * @param instance The current Minecraft instance.
     * @param graphics The GuiGraphics instance to draw to.
     * @param stack The item stack to draw.
     * @param pos The blit coordinates to draw the item at.
     * @param size The width and height of the item stack to draw on screen, in pixels.
     */
    public static void drawItemStack(@NotNull Minecraft instance, @NotNull GuiGraphics graphics, @NotNull ItemStack stack, ScreenCoordinate pos, int size) {
        drawItemStack(instance, graphics, stack, pos.getX(), pos.getY(), size);
    }
    /**
     * Draw an item stack to the screen.
     * @param instance The current Minecraft instance.
     * @param graphics The GuiGraphics instance to draw to.
     * @param stack The item stack to draw.
     * @param pos The blit coordinates to draw the item at.
     * @param width The width of the item stack to draw on screen, in pixels.
     * @param height The height of the item stack to draw on screen, in pixels.
     */
    public static void drawItemStack(@NotNull Minecraft instance, @NotNull GuiGraphics graphics, @NotNull ItemStack stack, ScreenCoordinate pos, int width, int height) {
        drawItemStack(instance, graphics, stack, pos.getX(), pos.getY(), width, height);
    }
    /**
     * Draw an item stack to the screen.
     * @param instance The current Minecraft instance.
     * @param graphics The GuiGraphics instance to draw to.
     * @param stack The item stack to draw.
     * @param x The X position to draw at.
     * @param y The Y position to draw at.
     */
    public static void drawItemStack(@NotNull Minecraft instance, @NotNull GuiGraphics graphics, @NotNull ItemStack stack, int x, int y) {
        drawItemStack(instance, graphics, stack, x, y, 16);
    }
    /**
     * Draw an item stack to the screen.
     * @param instance The current Minecraft instance.
     * @param graphics The GuiGraphics instance to draw to.
     * @param stack The item stack to draw.
     * @param x The X position to draw at.
     * @param y The Y position to draw at.
     * @param size The width and height of the item stack to draw on screen, in pixels.
     */
    public static void drawItemStack(@NotNull Minecraft instance, @NotNull GuiGraphics graphics, @NotNull ItemStack stack, int x, int y, int size) {
        if (!stack.isEmpty()) {
            BakedModel bakedmodel = instance.getItemRenderer().getModel(stack, instance.level, null, 0);

            graphics.pose().pushPose();
            graphics.pose().translate((float) (x + (size / 2)), (float) (y + (size / 2)), (float) (150));

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableDepthTest();
            RenderSystem.disableBlend();

            try {
                graphics.pose().mulPose((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
                graphics.pose().scale(size, size, size);
                boolean flag = !bakedmodel.usesBlockLight();
                if (flag) {
                    Lighting.setupForFlatItems();
                }

                instance.getItemRenderer().render(stack, ItemDisplayContext.GUI, false, graphics.pose(),
                        graphics.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
                graphics.flush();
                if (flag) {
                    Lighting.setupFor3DItems();
                }
            } catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.forThrowable(throwable, "Rendering item");
                CrashReportCategory crashreportcategory = crashreport.addCategory("Item being rendered");
                crashreportcategory.setDetail("Item Type", () -> {
                    return String.valueOf(stack.getItem());
                });
                crashreportcategory.setDetail("Registry Name", () -> String
                        .valueOf(net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem())));
                crashreportcategory.setDetail("Item Damage", () -> {
                    return String.valueOf(stack.getDamageValue());
                });
                crashreportcategory.setDetail("Item NBT", () -> {
                    return String.valueOf(stack.getComponents());
                });
                crashreportcategory.setDetail("Item Foil", () -> {
                    return String.valueOf(stack.hasFoil());
                });
                throw new ReportedException(crashreport);
            }

            RenderSystem.enableDepthTest();
            graphics.pose().popPose();
        }
    }
    /**
     * Draw an item stack to the screen.
     * @param instance The current Minecraft instance.
     * @param graphics The GuiGraphics instance to draw to.
     * @param stack The item stack to draw.
     * @param x The X position to draw at.
     * @param y The Y position to draw at.
     * @param width The width of the item stack to draw on screen, in pixels.
     * @param height The height of the item stack to draw on screen, in pixels.
     */
    public static void drawItemStack(@NotNull Minecraft instance, @NotNull GuiGraphics graphics, @NotNull ItemStack stack, int x, int y, int width, int height) {
        if (!stack.isEmpty()) {
            BakedModel bakedmodel = instance.getItemRenderer().getModel(stack, instance.level, null, 0);

            graphics.pose().pushPose();
            graphics.pose().translate((float) (x + (width / 2)), (float) (y + (height / 2)), (float) (150));

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.defaultBlendFunc();
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.disableDepthTest();
            RenderSystem.disableBlend();

            try {
                graphics.pose().mulPose((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
                graphics.pose().scale(width, height, width);
                boolean flag = !bakedmodel.usesBlockLight();
                if (flag) {
                    Lighting.setupForFlatItems();
                }

                instance.getItemRenderer().render(stack, ItemDisplayContext.GUI, false, graphics.pose(),
                        graphics.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
                graphics.flush();
                if (flag) {
                    Lighting.setupFor3DItems();
                }
            } catch (Throwable throwable) {
                CrashReport crashreport = CrashReport.forThrowable(throwable, "Rendering item");
                CrashReportCategory crashreportcategory = crashreport.addCategory("Item being rendered");
                crashreportcategory.setDetail("Item Type", () -> {
                    return String.valueOf(stack.getItem());
                });
                crashreportcategory.setDetail("Registry Name", () -> String
                        .valueOf(net.minecraft.core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem())));
                crashreportcategory.setDetail("Item Damage", () -> {
                    return String.valueOf(stack.getDamageValue());
                });
                crashreportcategory.setDetail("Item NBT", () -> {
                    return String.valueOf(stack.getComponents());
                });
                crashreportcategory.setDetail("Item Foil", () -> {
                    return String.valueOf(stack.hasFoil());
                });
                throw new ReportedException(crashreport);
            }

            RenderSystem.enableDepthTest();
            graphics.pose().popPose();
        }
    }


    /**
     * Get whether the mouse is within the bounds of the given area.
     * @param mouseX The current mouse X.
     * @param mouseY The current mouse Y.
     * @param area The area to check within.
     * @return Whether the mouse is within the bound of the given area.
     */
    public static boolean mouseInArea(double mouseX, double mouseY, @NotNull ScreenRect area) {
        return area.positionEnvelopes(mouseX, mouseY);
    }
}
