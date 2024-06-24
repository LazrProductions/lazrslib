package com.lazrproductions.lazrslib.client.gui;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

import javax.annotation.Nullable;

import com.lazrproductions.lazrslib.common.math.Divisor;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;

import it.unimi.dsi.fastutil.ints.IntIterator;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class GuiGraphics {
   public static final float MAX_GUI_Z = 10000.0F;
   public static final float MIN_GUI_Z = -10000.0F;
   private final Minecraft minecraft;
   private final PoseStack pose;
   private final MultiBufferSource.BufferSource bufferSource;
   private final GuiGraphics.ScissorStack scissorStack = new GuiGraphics.ScissorStack();
   private boolean managed;

   private GuiGraphics(Minecraft p_282144_, PoseStack p_281551_, MultiBufferSource.BufferSource p_281460_) {
      this.minecraft = p_282144_;
      this.pose = p_281551_;
      this.bufferSource = p_281460_;
   }

   public GuiGraphics(Minecraft p_283406_, MultiBufferSource.BufferSource p_282238_) {
      this(p_283406_, new PoseStack(), p_282238_);
   }

   /** @deprecated */
   @Deprecated
   public void drawManaged(Runnable p_286277_) {
      this.flush();
      this.managed = true;
      p_286277_.run();
      this.managed = false;
      this.flush();
   }

   /** @deprecated */
   @Deprecated
   private void flushIfUnmanaged() {
      if (!this.managed) {
         this.flush();
      }

   }

   /** @deprecated */
   @Deprecated
   private void flushIfManaged() {
      if (this.managed) {
         this.flush();
      }

   }

   public int guiWidth() {
      return this.minecraft.getWindow().getGuiScaledWidth();
   }

   public int guiHeight() {
      return this.minecraft.getWindow().getGuiScaledHeight();
   }

   public PoseStack pose() {
      return this.pose;
   }

   public MultiBufferSource.BufferSource bufferSource() {
      return this.bufferSource;
   }

   public void flush() {
      RenderSystem.disableDepthTest();
      this.bufferSource.endBatch();
      RenderSystem.enableDepthTest();
   }

   public void hLine(int fromX, int fromY, int toX, int toY) {
      if (fromY < fromX) {
         int i = fromX;
         fromX = fromY;
         fromY = i;
      }

      this.fill(fromX, toX, fromY + 1, toX + 1, toY);
   }

   public void vLine(int fromX, int fromY, int toX, int toY) {
      if (toX < fromY) {
         int i = fromY;
         fromY = toX;
         toX = i;
      }

      this.fill(fromX, fromY + 1, fromX + 1, toX, toY);
   }

   public void enableScissor(int fromX, int fromY, int toX, int toY) {
      this.applyScissor(this.scissorStack.push(new ScreenRectangle(fromX, fromY, toX - fromX, toY - fromY)));
   }

   public void disableScissor() {
      this.applyScissor(this.scissorStack.pop());
   }

   private void applyScissor(@Nullable ScreenRectangle rect) {
      this.flushIfManaged();
      if (rect != null) {
         Window window = Minecraft.getInstance().getWindow();
         int i = window.getHeight();
         double d0 = window.getGuiScale();
         double d1 = (double) rect.left() * d0;
         double d2 = (double) i - (double) rect.bottom() * d0;
         double d3 = (double) rect.width() * d0;
         double d4 = (double) rect.height() * d0;
         RenderSystem.enableScissor((int) d1, (int) d2, Math.max(0, (int) d3), Math.max(0, (int) d4));
      } else {
         RenderSystem.disableScissor();
      }

   }

   public void setColor(float r, float g, float b, float a) {
      this.flushIfManaged();
      RenderSystem.setShaderColor(r, g, b, a);
   }

   public void fill(int fromX, int fromY, int toX, int toY, int color) {
      this.fill(fromX, fromY, toX, toY, 0, color);
   }

   public void fill(int fromX, int fromY, int toX, int toY, int b, int color) {
      Matrix4f matrix4f = this.pose.last().pose();
      if (fromX < toX) {
         int i = fromX;
         fromX = toX;
         toX = i;
      }

      if (fromY < toY) {
         int j = fromY;
         fromY = toY;
         toY = j;
      }

      float f3 = (float) FastColor.ARGB32.alpha(color) / 255.0F;
      float f = (float) FastColor.ARGB32.red(color) / 255.0F;
      float f1 = (float) FastColor.ARGB32.green(color) / 255.0F;
      float f2 = (float) FastColor.ARGB32.blue(color) / 255.0F;

      RenderSystem.enableDepthTest();
      RenderSystem.disableTexture();
      RenderSystem.enableBlend();
      RenderSystem.defaultBlendFunc();
      RenderSystem.setShader(GameRenderer::getPositionColorShader);

      Tesselator tessellator = Tesselator.getInstance();
      BufferBuilder buffer = tessellator.getBuilder();
      buffer.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
      buffer.vertex(matrix4f, (float) fromX, (float) fromY, (float) b).color(f, f1, f2, f3).endVertex();
      buffer.vertex(matrix4f, (float) fromX, (float) toY, (float) b).color(f, f1, f2, f3).endVertex();
      buffer.vertex(matrix4f, (float) toX, (float) toY, (float) b).color(f, f1, f2, f3).endVertex();
      buffer.vertex(matrix4f, (float) toX, (float) fromY, (float) b).color(f, f1, f2, f3).endVertex();
      tessellator.end();

      RenderSystem.disableBlend();
      RenderSystem.enableTexture();
   }

   public void fillGradient(int fromX, int fromY, int toX, int toY, int startingColor, int endingColor) {
      this.fillGradient(fromX, fromY, toX, toY, 0, startingColor, endingColor);
   }

   public void fillGradient(int fromX, int fromY, int toX, int toY, int b, int startingColor, int endingColor) {
      this.fillGradient(RenderType.translucent(), fromX, fromY, toX, toY, startingColor, endingColor, b);
   }

   public void fillGradient(RenderType type, int fromX, int fromY, int toX, int toY, int startingColor, int endingColor,
         int b) {
      VertexConsumer vertexconsumer = this.bufferSource.getBuffer(type);
      this.fillGradient(vertexconsumer, fromX, fromY, toX, toY, b, startingColor, endingColor);
      this.flushIfUnmanaged();
   }

   private void fillGradient(VertexConsumer consumer, int fromX, int fromY, int toX, int toY, int b, int startingColor,
         int endingColor) {
      float f = (float) FastColor.ARGB32.alpha(startingColor) / 255.0F;
      float f1 = (float) FastColor.ARGB32.red(startingColor) / 255.0F;
      float f2 = (float) FastColor.ARGB32.green(startingColor) / 255.0F;
      float f3 = (float) FastColor.ARGB32.blue(startingColor) / 255.0F;
      float f4 = (float) FastColor.ARGB32.alpha(endingColor) / 255.0F;
      float f5 = (float) FastColor.ARGB32.red(endingColor) / 255.0F;
      float f6 = (float) FastColor.ARGB32.green(endingColor) / 255.0F;
      float f7 = (float) FastColor.ARGB32.blue(endingColor) / 255.0F;
      Matrix4f matrix4f = this.pose.last().pose();
      consumer.vertex(matrix4f, (float) fromX, (float) fromY, (float) b).color(f1, f2, f3, f).endVertex();
      consumer.vertex(matrix4f, (float) fromX, (float) toY, (float) b).color(f5, f6, f7, f4).endVertex();
      consumer.vertex(matrix4f, (float) toX, (float) toY, (float) b).color(f5, f6, f7, f4).endVertex();
      consumer.vertex(matrix4f, (float) toX, (float) fromY, (float) b).color(f1, f2, f3, f).endVertex();
   }

   public void drawCenteredString(Font p_282122_, String p_282898_, int p_281490_, int p_282853_, int p_281258_) {
      this.drawString(p_282122_, p_282898_, p_281490_ - p_282122_.width(p_282898_) / 2, p_282853_, p_281258_);
   }

   public void drawCenteredString(Font p_282901_, Component p_282456_, int p_283083_, int p_282276_, int p_281457_) {
      FormattedCharSequence formattedcharsequence = p_282456_.getVisualOrderText();
      this.drawString(p_282901_, formattedcharsequence, p_283083_ - p_282901_.width(formattedcharsequence) / 2,
            p_282276_, p_281457_);
   }

   public void drawCenteredString(Font p_282592_, FormattedCharSequence p_281854_, int p_281573_, int p_283511_,
         int p_282577_) {
      this.drawString(p_282592_, p_281854_, p_281573_ - p_282592_.width(p_281854_) / 2, p_283511_, p_282577_);
   }

   public int drawString(Font p_282003_, @Nullable String p_281403_, int p_282714_, int p_282041_, int p_281908_) {
      return this.drawString(p_282003_, p_281403_, p_282714_, p_282041_, p_281908_, true);
   }

   public int drawString(Font p_283343_, @Nullable String p_281896_, int p_283569_, int p_283418_, int p_281560_,
         boolean p_282130_) {
      return this.drawString(p_283343_, p_281896_, (float) p_283569_, (float) p_283418_, p_281560_, p_282130_);
   }

   public int drawString(Font font, @Nullable String text, float x, float y, int color, boolean drawShadow) {
      if (text == null) {
         return 0;
      } else {
         int i = font.drawInBatch(text, x, y, color, drawShadow, this.pose.last().pose(), this.bufferSource, true, 0,
               15728880);
         this.flushIfUnmanaged();
         return i;
      }
   }

   public int drawString(Font p_283019_, FormattedCharSequence p_283376_, int p_283379_, int p_283346_, int p_282119_) {
      return this.drawString(p_283019_, p_283376_, p_283379_, p_283346_, p_282119_, true);
   }

   public int drawString(Font p_282636_, FormattedCharSequence p_281596_, int p_281586_, int p_282816_, int p_281743_,
         boolean p_282394_) {
      return this.drawString(p_282636_, p_281596_, (float) p_281586_, (float) p_282816_, p_281743_, p_282394_);
   }

   public int drawString(Font font, FormattedCharSequence text, float x, float y, int color, boolean drawShadow) {
      int i = font.drawInBatch(text, x, y, color, drawShadow, this.pose.last().pose(), this.bufferSource, true, 0,
            15728880);
      this.flushIfUnmanaged();
      return i;
   }

   public int drawString(Font p_281653_, Component p_283140_, int p_283102_, int p_282347_, int p_281429_) {
      return this.drawString(p_281653_, p_283140_, p_283102_, p_282347_, p_281429_, true);
   }

   public int drawString(Font p_281547_, Component p_282131_, int p_282857_, int p_281250_, int p_282195_,
         boolean p_282791_) {
      return this.drawString(p_281547_, p_282131_.getVisualOrderText(), p_282857_, p_281250_, p_282195_, p_282791_);
   }

   public void drawWordWrap(Font font, FormattedText text, int x, int y, int width,
         int color) {
      this.drawWordWrap(font, text, x, y, width, color, false);
   }

   public void drawWordWrap(Font font, FormattedText text, int x, int y, int width,
         int color, boolean renderShadow) {
      for (FormattedCharSequence formattedcharsequence : font.split(text, width)) {
         this.drawString(font, formattedcharsequence, x, y, color, renderShadow);
         y += 9;
      }
   }

   public void blit(int f1, int f2, int f3, int f4, int f5, TextureAtlasSprite sprite) {
      this.innerBlit(sprite.atlas().location(), f1, f1 + f4, f2, f2 + f5, f3, sprite.getU0(), sprite.getU1(),
            sprite.getV0(), sprite.getV1());
   }

   public void blit(int f1, int f2, int f3, int f4, int f5, TextureAtlasSprite sprite, float fromU, float toU,
         float fromV, float toV) {
      this.innerBlit(sprite.atlas().location(), f1, f1 + f4, f2, f2 + f5, f3, sprite.getU0(), sprite.getU1(),
            sprite.getV0(), sprite.getV1(), fromU, toU, fromV, toV);
   }

   public void renderOutline(int fromX, int fromY, int toX, int toY, int color) {
      this.fill(fromX, fromY, fromX + toX, fromY + 1, color);
      this.fill(fromX, fromY + toY - 1, fromX + toX, fromY + toY, color);
      this.fill(fromX, fromY + 1, fromX + 1, fromY + toY - 1, color);
      this.fill(fromX + toX - 1, fromY + 1, fromX + toX, fromY + toY - 1, color);
   }

   public void blit(ResourceLocation location, int x, int y, int f1, int f2, int f3, int f4) {
      this.blit(location, x, y, 0, (float) f1, (float) f2, f3, f4, 256, 256);
   }

   public void blit(ResourceLocation location, int x, int y, int p_283545_, float p_283029_, float p_283061_,
         int p_282845_, int p_282558_, int p_282832_, int p_281851_) {
      this.blit(location, x, x + p_282845_, y, y + p_282558_, p_283545_, p_282845_, p_282558_, p_283029_, p_283061_,
            p_282832_, p_281851_);
   }

   public void blit(ResourceLocation location, int x, int y, int p_282058_, int p_281939_, float p_282285_,
         float p_283199_, int p_282186_, int p_282322_, int p_282481_, int p_281887_) {
      this.blit(location, x, x + p_282058_, y, y + p_281939_, 0, p_282186_, p_282322_, p_282285_, p_283199_, p_282481_,
            p_281887_);
   }

   public void blit(ResourceLocation location, int x, int y, float p_282809_, float p_282942_, int p_281922_,
         int p_282385_, int p_282596_, int p_281699_) {
      this.blit(location, x, y, p_281922_, p_282385_, p_282809_, p_282942_, p_281922_, p_282385_, p_282596_, p_281699_);
   }

   void blit(ResourceLocation location, int x, int y, int p_281760_, int p_283298_, int p_283429_, int p_282193_,
         int p_281980_, float p_282660_, float p_281522_, int p_282315_, int p_281436_) {
      this.innerBlit(location, x, y, p_281760_, p_283298_, p_283429_, (p_282660_ + 0.0F) / (float) p_282315_,
            (p_282660_ + (float) p_282193_) / (float) p_282315_, (p_281522_ + 0.0F) / (float) p_281436_,
            (p_281522_ + (float) p_281980_) / (float) p_281436_);
   }

   void innerBlit(ResourceLocation location, int x, int toX, int y, int toY, int f, float u, float toU, float v,
         float toV) {
      RenderSystem.setShaderTexture(0, location);
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      Matrix4f matrix4f = this.pose.last().pose();
      BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
      bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
      bufferbuilder.vertex(matrix4f, (float) x, (float) y, (float) f).uv(u, v).endVertex();
      bufferbuilder.vertex(matrix4f, (float) x, (float) toY, (float) f).uv(u, toV).endVertex();
      bufferbuilder.vertex(matrix4f, (float) toX, (float) toY, (float) f).uv(toU, toV).endVertex();
      bufferbuilder.vertex(matrix4f, (float) toX, (float) y, (float) f).uv(toU, v).endVertex();
      BufferUploader.drawWithShader(bufferbuilder.end());
   }

   void innerBlit(ResourceLocation location, int x, int toX, int y, int toY, int f, float u, float toU, float v,
         float toV, float red, float green, float blue, float alpha) {
      RenderSystem.setShaderTexture(0, location);
      RenderSystem.setShader(GameRenderer::getPositionColorTexShader);
      RenderSystem.enableBlend();
      Matrix4f matrix4f = this.pose.last().pose();
      BufferBuilder bufferbuilder = Tesselator.getInstance().getBuilder();
      bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR_TEX);
      bufferbuilder.vertex(matrix4f, (float) x, (float) y, (float) f).color(red, blue, blue, alpha).uv(u, v)
            .endVertex();
      bufferbuilder.vertex(matrix4f, (float) x, (float) toY, (float) f).color(red, blue, blue, alpha).uv(u, toV)
            .endVertex();
      bufferbuilder.vertex(matrix4f, (float) toX, (float) toY, (float) f).color(red, blue, blue, alpha).uv(toU, toV)
            .endVertex();
      bufferbuilder.vertex(matrix4f, (float) toX, (float) y, (float) f).color(red, blue, blue, alpha).uv(toU, v)
            .endVertex();
      BufferUploader.drawWithShader(bufferbuilder.end());
      RenderSystem.disableBlend();
   }

   public void blitNineSliced(ResourceLocation p_282546_, int p_282275_, int p_281581_, int p_283274_, int p_281626_,
         int p_283005_, int p_282047_, int p_282125_, int p_283423_, int p_281424_) {
      this.blitNineSliced(p_282546_, p_282275_, p_281581_, p_283274_, p_281626_, p_283005_, p_283005_, p_283005_,
            p_283005_, p_282047_, p_282125_, p_283423_, p_281424_);
   }

   public void blitNineSliced(ResourceLocation p_282543_, int p_281513_, int p_281865_, int p_282482_, int p_282661_,
         int p_282068_, int p_281294_, int p_281681_, int p_281957_, int p_282300_, int p_282769_) {
      this.blitNineSliced(p_282543_, p_281513_, p_281865_, p_282482_, p_282661_, p_282068_, p_281294_, p_282068_,
            p_281294_, p_281681_, p_281957_, p_282300_, p_282769_);
   }

   public void blitNineSliced(ResourceLocation p_282712_, int p_283509_, int p_283259_, int p_283273_, int p_282043_,
         int p_281430_, int p_281412_, int p_282566_, int p_281971_, int p_282879_, int p_281529_, int p_281924_,
         int p_281407_) {
      p_281430_ = Math.min(p_281430_, p_283273_ / 2);
      p_282566_ = Math.min(p_282566_, p_283273_ / 2);
      p_281412_ = Math.min(p_281412_, p_282043_ / 2);
      p_281971_ = Math.min(p_281971_, p_282043_ / 2);
      if (p_283273_ == p_282879_ && p_282043_ == p_281529_) {
         this.blit(p_282712_, p_283509_, p_283259_, p_281924_, p_281407_, p_283273_, p_282043_);
      } else if (p_282043_ == p_281529_) {
         this.blit(p_282712_, p_283509_, p_283259_, p_281924_, p_281407_, p_281430_, p_282043_);
         this.blitRepeating(p_282712_, p_283509_ + p_281430_, p_283259_, p_283273_ - p_282566_ - p_281430_, p_282043_,
               p_281924_ + p_281430_, p_281407_, p_282879_ - p_282566_ - p_281430_, p_281529_);
         this.blit(p_282712_, p_283509_ + p_283273_ - p_282566_, p_283259_, p_281924_ + p_282879_ - p_282566_,
               p_281407_, p_282566_, p_282043_);
      } else if (p_283273_ == p_282879_) {
         this.blit(p_282712_, p_283509_, p_283259_, p_281924_, p_281407_, p_283273_, p_281412_);
         this.blitRepeating(p_282712_, p_283509_, p_283259_ + p_281412_, p_283273_, p_282043_ - p_281971_ - p_281412_,
               p_281924_, p_281407_ + p_281412_, p_282879_, p_281529_ - p_281971_ - p_281412_);
         this.blit(p_282712_, p_283509_, p_283259_ + p_282043_ - p_281971_, p_281924_,
               p_281407_ + p_281529_ - p_281971_, p_283273_, p_281971_);
      } else {
         this.blit(p_282712_, p_283509_, p_283259_, p_281924_, p_281407_, p_281430_, p_281412_);
         this.blitRepeating(p_282712_, p_283509_ + p_281430_, p_283259_, p_283273_ - p_282566_ - p_281430_, p_281412_,
               p_281924_ + p_281430_, p_281407_, p_282879_ - p_282566_ - p_281430_, p_281412_);
         this.blit(p_282712_, p_283509_ + p_283273_ - p_282566_, p_283259_, p_281924_ + p_282879_ - p_282566_,
               p_281407_, p_282566_, p_281412_);
         this.blit(p_282712_, p_283509_, p_283259_ + p_282043_ - p_281971_, p_281924_,
               p_281407_ + p_281529_ - p_281971_, p_281430_, p_281971_);
         this.blitRepeating(p_282712_, p_283509_ + p_281430_, p_283259_ + p_282043_ - p_281971_,
               p_283273_ - p_282566_ - p_281430_, p_281971_, p_281924_ + p_281430_, p_281407_ + p_281529_ - p_281971_,
               p_282879_ - p_282566_ - p_281430_, p_281971_);
         this.blit(p_282712_, p_283509_ + p_283273_ - p_282566_, p_283259_ + p_282043_ - p_281971_,
               p_281924_ + p_282879_ - p_282566_, p_281407_ + p_281529_ - p_281971_, p_282566_, p_281971_);
         this.blitRepeating(p_282712_, p_283509_, p_283259_ + p_281412_, p_281430_, p_282043_ - p_281971_ - p_281412_,
               p_281924_, p_281407_ + p_281412_, p_281430_, p_281529_ - p_281971_ - p_281412_);
         this.blitRepeating(p_282712_, p_283509_ + p_281430_, p_283259_ + p_281412_, p_283273_ - p_282566_ - p_281430_,
               p_282043_ - p_281971_ - p_281412_, p_281924_ + p_281430_, p_281407_ + p_281412_,
               p_282879_ - p_282566_ - p_281430_, p_281529_ - p_281971_ - p_281412_);
         this.blitRepeating(p_282712_, p_283509_ + p_283273_ - p_282566_, p_283259_ + p_281412_, p_281430_,
               p_282043_ - p_281971_ - p_281412_, p_281924_ + p_282879_ - p_282566_, p_281407_ + p_281412_, p_282566_,
               p_281529_ - p_281971_ - p_281412_);
      }
   }

   public void blitRepeating(ResourceLocation p_283059_, int p_283575_, int p_283192_, int p_281790_, int p_283642_,
         int p_282691_, int p_281912_, int p_281728_, int p_282324_) {
      blitRepeating(p_283059_, p_283575_, p_283192_, p_281790_, p_283642_, p_282691_, p_281912_, p_281728_, p_282324_,
            256, 256);
   }

   public void blitRepeating(ResourceLocation p_283059_, int p_283575_, int p_283192_, int p_281790_, int p_283642_,
         int p_282691_, int p_281912_, int p_281728_, int p_282324_, int textureWidth, int textureHeight) {
      int i = p_283575_;

      int j;
      for (IntIterator intiterator = slices(p_281790_, p_281728_); intiterator.hasNext(); i += j) {
         j = intiterator.nextInt();
         int k = (p_281728_ - j) / 2;
         int l = p_283192_;

         int i1;
         for (IntIterator intiterator1 = slices(p_283642_, p_282324_); intiterator1.hasNext(); l += i1) {
            i1 = intiterator1.nextInt();
            int j1 = (p_282324_ - i1) / 2;
            this.blit(p_283059_, i, l, p_282691_ + k, p_281912_ + j1, j, i1, textureWidth, textureHeight);
         }
      }

   }

   private static IntIterator slices(int p_282197_, int p_282161_) {
      int i = Mth.positiveCeilDiv(p_282197_, p_282161_);
      return new Divisor(p_282197_, i);
   }

   public void renderItem(ItemStack p_281978_, int p_282647_, int p_281944_) {
      this.renderItem(this.minecraft.player, this.minecraft.level, p_281978_, p_282647_, p_281944_, 0);
   }

   public void renderItem(ItemStack p_282262_, int p_283221_, int p_283496_, int p_283435_) {
      this.renderItem(this.minecraft.player, this.minecraft.level, p_282262_, p_283221_, p_283496_, p_283435_);
   }

   public void renderItem(ItemStack p_282786_, int p_282502_, int p_282976_, int p_281592_, int p_282314_) {
      this.renderItem(this.minecraft.player, this.minecraft.level, p_282786_, p_282502_, p_282976_, p_281592_,
            p_282314_);
   }

   public void renderFakeItem(ItemStack p_281946_, int p_283299_, int p_283674_) {
      this.renderItem((LivingEntity) null, this.minecraft.level, p_281946_, p_283299_, p_283674_, 0);
   }

   public void renderItem(LivingEntity entity, ItemStack p_282777_, int p_282110_, int p_281371_, int p_283572_) {
      this.renderItem(entity, entity.getLevel(), p_282777_, p_282110_, p_281371_, p_283572_);
   }

   private void renderItem(@Nullable LivingEntity p_283524_, @Nullable Level p_282461_, ItemStack p_283653_,
         int p_283141_, int p_282560_, int p_282425_) {
      this.renderItem(p_283524_, p_282461_, p_283653_, p_283141_, p_282560_, p_282425_, 0);
   }

   private void renderItem(@Nullable LivingEntity p_282619_, @Nullable Level p_281754_, ItemStack p_281675_,
         int p_281271_, int p_282210_, int p_283260_, int p_281995_) {
      if (!p_281675_.isEmpty()) {
         BakedModel bakedmodel = this.minecraft.getItemRenderer().getModel(p_281675_, p_281754_, p_282619_, p_283260_);
         this.pose.pushPose();
         this.pose.translate((float) (p_281271_ + 8), (float) (p_282210_ + 8),
               (float) (150 + (bakedmodel.isGui3d() ? p_281995_ : 0)));

         try {
            this.pose.mulPoseMatrix(Matrix4f.createScaleMatrix(1.0F, -1.0F, 1.0F));
            this.pose.scale(16.0F, 16.0F, 16.0F);
            boolean flag = !bakedmodel.usesBlockLight();
            if (flag) {
               Lighting.setupForFlatItems();
            }

            this.minecraft.getItemRenderer().render(p_281675_, ItemTransforms.TransformType.GUI, false, this.pose,
                  this.bufferSource(), 15728880, OverlayTexture.NO_OVERLAY, bakedmodel);
            this.flush();
            if (flag) {
               Lighting.setupFor3DItems();
            }
         } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.forThrowable(throwable, "Rendering item");
            CrashReportCategory crashreportcategory = crashreport.addCategory("Item being rendered");
            crashreportcategory.setDetail("Item Type", () -> {
               return String.valueOf((Object) p_281675_.getItem());
            });
            crashreportcategory.setDetail("Registry Name", () -> String
                  .valueOf(net.minecraftforge.registries.ForgeRegistries.ITEMS.getKey(p_281675_.getItem())));
            crashreportcategory.setDetail("Item Damage", () -> {
               return String.valueOf(p_281675_.getDamageValue());
            });
            crashreportcategory.setDetail("Item NBT", () -> {
               return String.valueOf((Object) p_281675_.getTag());
            });
            crashreportcategory.setDetail("Item Foil", () -> {
               return String.valueOf(p_281675_.hasFoil());
            });
            throw new ReportedException(crashreport);
         }

         this.pose.popPose();
      }
   }

   @OnlyIn(Dist.CLIENT)
   static class ScissorStack {
      private final Deque<ScreenRectangle> stack = new ArrayDeque<>();

      public ScreenRectangle push(ScreenRectangle p_281812_) {
         ScreenRectangle screenrectangle = this.stack.peekLast();
         if (screenrectangle != null) {
            ScreenRectangle screenrectangle1 = Objects.requireNonNullElse(p_281812_.intersection(screenrectangle),
                  ScreenRectangle.empty());
            this.stack.addLast(screenrectangle1);
            return screenrectangle1;
         } else {
            this.stack.addLast(p_281812_);
            return p_281812_;
         }
      }

      public @Nullable ScreenRectangle pop() {
         if (this.stack.isEmpty()) {
            throw new IllegalStateException("Scissor stack underflow");
         } else {
            this.stack.removeLast();
            return this.stack.peekLast();
         }
      }
   }

   public static final GuiGraphics from(Minecraft instance) {
      return new GuiGraphics(instance, instance.renderBuffers().bufferSource());
   }
}