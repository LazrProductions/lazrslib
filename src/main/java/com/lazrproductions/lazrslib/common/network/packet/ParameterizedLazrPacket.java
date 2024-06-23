package com.lazrproductions.lazrslib.common.network.packet;

import javax.annotation.Nonnull;

import com.lazrproductions.lazrslib.LazrsLibMod;
import com.lazrproductions.lazrslib.common.network.LazrNetworkedParameterHandler;
import com.lazrproductions.lazrslib.common.network.base.ILazrPacket;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.network.CustomPayloadEvent;

public abstract class ParameterizedLazrPacket implements ILazrPacket {

    Object[] parameters;

    public ParameterizedLazrPacket(FriendlyByteBuf buffer) {
        read(buffer);
    }
    public ParameterizedLazrPacket(Object... p) {
        this.parameters = p;
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        LazrNetworkedParameterHandler.writePacketParameters(parameters, buffer);
    }

    @Override
    public void read(FriendlyByteBuf buffer) {
        LazrNetworkedParameterHandler.readPacket(this, buffer);
    }

    public void setFrom(Object[] parameters) {
        this.parameters = parameters;
        loadValues(parameters);
    }
    public abstract void loadValues(Object[] parameters);

    @Override
    public void handle(CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            Minecraft inst = Minecraft.getInstance();
            Player player = ctx.getSender() == null ? inst.player : ctx.getSender();
            if(player != null)
                if(player.level().isClientSide())
                    handeClientside(player);
                else
                    handleServerside((ServerPlayer)player);
            else
                LazrsLibMod.LOGGER.error("Couldn't execute parameterized packet because player is null.");
        });
        ctx.setPacketHandled(true);
    }

    public abstract void handeClientside(@Nonnull Player player);
    public abstract void handleServerside(@Nonnull ServerPlayer player);
}
