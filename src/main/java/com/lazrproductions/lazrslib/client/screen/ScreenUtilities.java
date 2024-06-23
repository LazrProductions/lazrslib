package com.lazrproductions.lazrslib.client.screen;

import java.util.List;

import javax.annotation.Nonnull;

import org.joml.Matrix4f;

import com.lazrproductions.lazrslib.LazrsLibMod;
import com.lazrproductions.lazrslib.client.screen.base.BlitCoordinates;
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
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class ScreenUtilities {
    static final ResourceLocation GENERIC_PROGRESS_BAR_LOCATION = new ResourceLocation(LazrsLibMod.MODID, "textures/gui/progress_bar.png");

    
    public static void drawTexture(GuiGraphics graphics, BlitCoordinates pos, ScreenTexture texture) {
        graphics.blit(texture.getResourceLocation(), pos.getX(), pos.getY(), pos.getWidth(), pos.getHeight(),
                texture.getU(), texture.getV(), texture.getBoundsX(), texture.getBoundsY(), texture.getWidth(),
                texture.getHeight());
    }
    public static void drawTexture(GuiGraphics graphics, BlitCoordinates pos, float rotation, ScreenTexture texture) {
        drawTexture(graphics, pos, rotation, (pos.getWidth() / 2), (pos.getHeight() / 2), texture);
    }
    public static void drawTexture(GuiGraphics graphics, BlitCoordinates pos, float rotation, float rotateAroundX, float rotateAroundY, ScreenTexture texture) {
        graphics.pose().pushPose();
        graphics.pose().rotateAround(Axis.ZP.rotationDegrees(rotation), pos.getX() + rotateAroundX, pos.getY() + rotateAroundY, 0);

        graphics.blit(texture.getResourceLocation(), pos.getX(), pos.getY(), pos.getWidth(), pos.getHeight(),
                texture.getU(), texture.getV(), texture.getBoundsX(), texture.getBoundsY(), texture.getWidth(),
                texture.getHeight());

        graphics.pose().popPose();
    }


    public static void drawProgressBar(GuiGraphics graphics, BlitCoordinates pos, ScreenTexture texture, float progress, int totalFramesHorizontal, int totalFramesVertically) {

        int frames = (totalFramesHorizontal * totalFramesVertically);
        int frameIndex = Mth.floor((float) frames * progress);
        int column = Mth.floor((float) frameIndex % (float) totalFramesHorizontal);
        int row = Mth.floor((float) frameIndex / (float) totalFramesHorizontal);

        float uvXF = texture.getU() + (column * texture.getBoundsX());
        float uvYF = texture.getV() + (row * texture.getBoundsY());

        graphics.blit(texture.getResourceLocation(), pos.getX(), pos.getY(), pos.getWidth(), pos.getHeight(), uvXF, uvYF, texture.getBoundsX(), texture.getBoundsY(), texture.getWidth(), texture.getHeight());
    }
    public static void drawGenericProgressBar(GuiGraphics graphics, BlitCoordinates pos, float progress) {
        graphics.fill(pos.getX(), pos.getY(), pos.getX() + pos.getWidth(), pos.getY() + 2, 1325400064);
        int i = Mth.hsvToRgb(progress / 3.0F, 1.0F, 1.0F);
        graphics.fill(pos.getX(), pos.getY(), pos.getX() + (int) (pos.getWidth() * progress), pos.getY() + 1, i | -16777216);
    }
    public static void drawGenericProgressBarUpright(GuiGraphics graphics, BlitCoordinates pos, float progress, float partialTick) {
        progress = Mth.clamp(progress, 0, 2);

        int shakeX = 0;
        if(progress > 1)
            shakeX = Mth.floor(Mth.sin(partialTick*(100f*(progress - 1)) * 2f)-1);

        graphics.fill(pos.getX() + shakeX, pos.getY(), pos.getX() + 2 + shakeX, pos.getY() + pos.getHeight(), 1325400064);
        int i = Mth.hsvToRgb(progress / 3.0F, 1.0F, 1.0F);
        progress = Mth.clamp(progress, 0, 1f);
        graphics.fill(pos.getX() + shakeX, pos.getY() + pos.getHeight(), pos.getX() + 1 + shakeX, pos.getY() + pos.getHeight() - (int) (pos.getHeight() * progress), i | -16777216);
    }


    public static void renderLabel(Minecraft instance, GuiGraphics graphics, int x, int y, List<Component> list, int color, boolean renderShadow) {
        int space = 15;
        int width = 0;
        for (int i = 0; i < list.size(); i++) {
            String text = list.get(i).getString();
            width = Math.max(width, instance.font.width(text) + 10);
        }

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();

        for (int i = 0; i < list.size(); i++) {
            String text = list.get(i).getString();
            graphics.drawString(instance.font, text,
                    x - instance.font.width(text) / 2,
                    y + ((list.size() / 2) * space + (space * i)),
                    color, renderShadow);
        }
        RenderSystem.enableDepthTest();
    }
    public static void renderLabel(Minecraft instance, GuiGraphics graphics, int x, int y, List<Component> list, int color) {
        renderLabel(instance, graphics, x, y, list, color, true);
    }


    public static void drawParagraph(Minecraft instance, GuiGraphics graphics, int x, int y, List<Component> list, int maxWidth, int color, boolean renderShadow) {        
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableDepthTest();
        RenderSystem.disableBlend();
        
        FormattedText text = FormattedText.composite(list);
        graphics.drawWordWrap(instance.font, text, x, y, maxWidth, color);

        RenderSystem.enableDepthTest();
    }
    public static void drawParagraph(Minecraft instance, GuiGraphics graphics, int x, int y, List<Component> list, int maxWidth, int color) {
        drawParagraph(instance, graphics, x, y, list, maxWidth, color, true);
    }
    public static void drawParagraph(Minecraft instance, GuiGraphics graphics, BlitCoordinates pos, List<Component> list, int color) {
        drawParagraph(instance, graphics, pos.getX(), pos.getY(), list, pos.getWidth(), color, true);
    }
    public static void drawParagraph(Minecraft instance, GuiGraphics graphics, BlitCoordinates pos, List<Component> list, int color, boolean renderShadow) {
        drawParagraph(instance, graphics, pos.getX(), pos.getY(), list, pos.getWidth(), color, renderShadow);
    }


    public static void drawItemStack(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics, @Nonnull ItemStack stack, int x, int y) {
        drawItemStack(instance, graphics, stack, x, y, 16);
    }
    public static void drawItemStack(@Nonnull Minecraft instance, @Nonnull GuiGraphics graphics, @Nonnull ItemStack stack, int x, int y, int size) {
        if (!stack.isEmpty()) {
            BakedModel bakedmodel = instance.getItemRenderer().getModel(stack, instance.level, null, 0);

            graphics.pose().pushPose();
            graphics.pose().translate((float) (x + (size / 2)), (float) (y + (size / 2)), (float) (150 + (bakedmodel.isGui3d() ? 0 : 0)));
            
            try {
                graphics.pose().mulPoseMatrix((new Matrix4f()).scaling(1.0F, -1.0F, 1.0F));
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
                    return String.valueOf((Object) stack.getItem());
                });
                crashreportcategory.setDetail("Registry Name", () -> String
                        .valueOf(net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(stack.getItem())));
                crashreportcategory.setDetail("Item Damage", () -> {
                    return String.valueOf(stack.getDamageValue());
                });
                crashreportcategory.setDetail("Item NBT", () -> {
                    return String.valueOf((Object) stack.getTag());
                });
                crashreportcategory.setDetail("Item Foil", () -> {
                    return String.valueOf(stack.hasFoil());
                });
                throw new ReportedException(crashreport);
            }

            graphics.pose().popPose();
        }
    }

    
    public static boolean mouseInArea(double mouseX, double mouseY, ScreenRect area) {
        return area.positionEnvlopes(mouseX, mouseY);
    }
}
