package com.lazrproductions.lazrslib.testing;

import com.lazrproductions.lazrslib.LazrsLibMod;
import com.lazrproductions.lazrslib.client.overlay.OverlayUtilities;
import com.lazrproductions.lazrslib.client.overlay.OverlayUtilities.OverlayProperties;

import net.minecraft.client.Minecraft;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent.Stage;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LazrsLibMod.MODID, value = Dist.CLIENT)
public class TestClientEvents {
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        Minecraft inst = Minecraft.getInstance();

        if (inst != null)
            if (event.getKey() == 72 && inst.screen == null && inst.level != null)
                inst.setScreen(new TestScreen(inst));
    }

    @SubscribeEvent
    public static void onRenderLevel(RenderLevelStageEvent event) {
        if(event.getStage() == Stage.AFTER_CUTOUT_BLOCKS) {
            Minecraft instance = Minecraft.getInstance();
            if(instance != null) {
                OverlayProperties p = new OverlayProperties(event.getCamera(), event.getPoseStack(), instance.renderBuffers());

                OverlayUtilities.drawLabel(p, new Vec3(0.5, 1, 0.5), Component.literal("Gridding :)"), 1, false);
                OverlayUtilities.drawGrid(p, new Vec3(-1, -1, -1), new Vec3i(3, 1, 03), new Vec3(1, 1, 1), 0xFFFFFF);
            }
        }
    }
}
