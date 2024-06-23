package com.lazrproductions.lazrslib.testing;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.LazrsLibMod;
import com.lazrproductions.lazrslib.common.network.packet.ParameterizedLazrPacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;

public class TestServerBoundPacket extends ParameterizedLazrPacket {

    String message;

    public TestServerBoundPacket(FriendlyByteBuf buffer) {
        super(buffer);
    }
    public TestServerBoundPacket(String message) {
        super(message);
        this.message = message; 
    }

    @Override
    public void loadValues(Object[] parameters) {
        this.message = (String)parameters[0];
    }

    @Override
    public void handeClientside(@Nonnull Player player) {
        LazrsLibMod.LOGGER.info("receiving test packet clientside -> " + player.getDisplayName().getString());
        player.playSound(SoundEvents.NOTE_BLOCK_HARP);
    }

    @Override
    public void handleServerside(@Nonnull ServerPlayer player) {
        LazrsLibMod.LOGGER.info("receiving test packet serverside -> " + player.getDisplayName().getString());
        player.getLevel().playSound(null, player.blockPosition(), SoundEvents.NOTE_BLOCK_PLING, SoundSource.PLAYERS, 1 ,1);

        TestingAPI.sendTestPacketToClient(player, message);
    }

}
