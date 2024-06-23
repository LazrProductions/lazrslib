package com.lazrproductions.lazrslib.testing;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.LazrsLibMod;
import com.lazrproductions.lazrslib.common.network.packet.ParameterizedLazrPacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;

public class TestClientBoundPacket extends ParameterizedLazrPacket {

    String message;

    public TestClientBoundPacket(FriendlyByteBuf buffer) {
        super(buffer);
    }
    public TestClientBoundPacket(String message) {
        super(message);
        this.message = message; 
    }

    @Override
    public void loadValues(Object[] parameters) {
        this.message = (String)parameters[0];
    }

    @Override
    public void handeClientside(@Nonnull Player player) {
        LazrsLibMod.LOGGER.info("receiving test2 packet clientside -> " + player.getDisplayName().getString());
        player.playSound(SoundEvents.NOTE_BLOCK_PLING);
    }

    @Override
    public void handleServerside(@Nonnull ServerPlayer player) {
        LazrsLibMod.LOGGER.info("receiving test2 packet serverside -> " + player.getDisplayName().getString());
        player.playSound(SoundEvents.NOTE_BLOCK_PLING);
    }

}
