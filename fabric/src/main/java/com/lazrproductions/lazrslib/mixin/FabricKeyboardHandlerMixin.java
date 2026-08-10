package com.lazrproductions.lazrslib.mixin;

import com.lazrproductions.lazrslib.events.LazrsLibClientEvents;
import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class FabricKeyboardHandlerMixin {
    @Inject(method = "keyPress", at = @At("HEAD"))
    public void keyPress(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo callback) {
        LazrsLibClientEvents.onKeyInput(key, action, modifiers);
    }
}
