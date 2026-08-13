package com.lazrproductions.lazrslib.mixin;

import com.lazrproductions.lazrslib.events.LazrsLibClientEvents;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class FabricMouseHandlerMixin {
    @Inject(method = "onPress", at = @At("HEAD"))
    public void onPress(long windowId, int button, int action, int modifiers, CallbackInfo callback) {
        LazrsLibClientEvents.onMouseInput(button, action, modifiers);
    }
}
