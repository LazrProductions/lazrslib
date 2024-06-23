package com.lazrproductions.lazrslib.testing;

import java.util.List;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.LazrsLibMod;
import com.lazrproductions.lazrslib.client.font.FontUtilities;
import com.lazrproductions.lazrslib.client.screen.ScreenUtilities;
import com.lazrproductions.lazrslib.client.screen.base.BlitCoordinates;
import com.lazrproductions.lazrslib.client.screen.base.GenericScreen;
import com.lazrproductions.lazrslib.client.screen.base.ScreenTexture;
import com.lazrproductions.lazrslib.client.ui.Alignment;
import com.lazrproductions.lazrslib.client.ui.UIUtilities;
import com.lazrproductions.lazrslib.client.ui.element.HorizontalElement;
import com.lazrproductions.lazrslib.client.ui.element.ItemIconElement;
import com.lazrproductions.lazrslib.client.ui.element.TextElement;
import com.lazrproductions.lazrslib.client.ui.element.VerticalElement;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class TestScreen extends GenericScreen {
    public TestScreen(Minecraft instance) {
        super(instance);
    }

    @Override
    public void render(@Nonnull PoseStack stack, int mouseX, int mouseY, float partialTick) {       
        Minecraft instance = minecraft;
        if (instance != null) {
            ItemStack item = Items.ARROW.getDefaultInstance();
            item.setCount(3);
            ScreenUtilities.drawItemStack(instance, graphics, item, 0, 0);

            ScreenUtilities.drawTexture(graphics, new BlitCoordinates(0, 16, 32, 32),
                    Mth.sin((float)tick / 20f) * 45f,
                    new ScreenTexture(new ResourceLocation(LazrsLibMod.MODID, "icon.png"), 0, 0, 16, 16, 16, 16));

            FontUtilities.drawText(instance, graphics, new BlitCoordinates(0, 48, 32, 8), Component.literal("Testing drawText"), 0xed00ff);
            if(FontUtilities.drawLink(instance, graphics, new BlitCoordinates(0, 56, 32, 8), Component.literal("Testing drawLink"), 0x00adff, 0x004dff, mouseX, mouseY, lastMouseInput.getAction() == 1))
                LazrsLibMod.LOGGER.info("Link was clicked!");
            int h = FontUtilities.drawParagraph(instance, graphics, new BlitCoordinates(0, 64, 64, 10), List.of(Component.literal("Testing drawParagraph, this is a big long paragraph to show the wrapping ability of the FontUtilities.")), 0x49ff00);
            ScreenUtilities.drawGenericProgressBar(graphics, new BlitCoordinates(0, h + 64, 64, 2), 0.5f);
            UIUtilities.drawPage(instance, graphics, mouseX, mouseY, lastMouseInput.getAction() == 1, new VerticalElement(new BlitCoordinates(0, h + 68, 128, 0),
                new HorizontalElement(
                    new TextElement(instance, 128 - 16,
                        Component.translatable("item.minecraft.iron_sword").withStyle(ChatFormatting.BOLD), 0xfd5f00, false),
                    new ItemIconElement(instance, Items.IRON_SWORD.getDefaultInstance(), Alignment.CENTER_RIGHT, 16)),
                new HorizontalElement(
                    new TextElement(instance, 128,
                        Component.translatable("item.minecraft.iron_sword"+".desc"), 0xfd5f00))));
            if(FontUtilities.drawLink(instance, graphics, new BlitCoordinates(64, 0, 32, 8), Component.literal("Test Client->Server Packet"), 0x00adff, 0x004dff, mouseX, mouseY, lastMouseInput.getAction() == 1))
                        TestingAPI.sendTestPacketToServer("Testing Testing 1 2 4");
        }
        super.render(stack, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
