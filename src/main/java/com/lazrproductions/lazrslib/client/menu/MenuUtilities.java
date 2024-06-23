package com.lazrproductions.lazrslib.client.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;

public class MenuUtilities {
    public static void openCustomMenu(ServerPlayer player, MenuProvider menuProvider, BlockPos pos) {
        player.openMenu(menuProvider, pos);
    }
}
