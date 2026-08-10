package com.lazrproductions.lazrslib.mixin;

import com.lazrproductions.lazrslib.client.overlay.base.InteractableOverlay;
import net.minecraft.client.DeltaTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;

@Mixin(Gui.class)
public class GuiMixin {
    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    public void renderCrosshair(GuiGraphics guiGraphics, DeltaTracker deltaTracker, CallbackInfo callback) {
        Minecraft inst = Minecraft.getInstance();
        if(inst.getOverlay() instanceof InteractableOverlay o)
            if(!o.getShowCrosshair())
                callback.cancel();
    }
}
