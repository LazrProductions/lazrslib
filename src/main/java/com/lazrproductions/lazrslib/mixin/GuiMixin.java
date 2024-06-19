package com.lazrproductions.lazrslib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.lazrproductions.lazrslib.overlay.base.InteractableOverlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;

@Mixin(Gui.class)
public class GuiMixin {
    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    public void renderCrosshairH(GuiGraphics graphics, CallbackInfo callback) {       
        Minecraft inst = Minecraft.getInstance();
        if(inst!=null){
            if(inst.getOverlay() instanceof InteractableOverlay o)
                if(!o.getShowCroshair())
                    callback.cancel();
        }
    }
}