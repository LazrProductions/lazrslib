package com.lazrproductions.lazrslib.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.lazrproductions.lazrslib.client.overlay.base.InteractableOverlay;

import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    @Shadow
    private double accumulatedDX = 0.0D;
    @Shadow
    private double accumulatedDY = 0.0D;

    @Inject(method = "turnPlayer", at = @At("HEAD"), cancellable = true)
    public void turnPlayer(CallbackInfo callback) {
        Minecraft inst = Minecraft.getInstance();
        if(inst.getOverlay() instanceof InteractableOverlay o)
            if(o.getLockView()) {
                accumulatedDX = 0.0D;
                accumulatedDY = 0.0D;
                callback.cancel();
            }
    }
}
