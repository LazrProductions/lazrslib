package com.lazrproductions.lazrslib.client.menu;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import org.jetbrains.annotations.NotNull;

public class MenuUtilities {
    public static void openCustomMenu(@NotNull ServerPlayer player, @NotNull MenuProvider menuProvider) {
        player.openMenu(menuProvider);
    }
}
