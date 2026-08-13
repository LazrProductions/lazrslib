package com.lazrproductions.lazrslib.client.menu;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;

import javax.annotation.Nonnull;

public class MenuUtilities {
    public static void openCustomMenu(@Nonnull ServerPlayer player, @Nonnull MenuProvider menuProvider) {
        player.openMenu(menuProvider);
    }
}
