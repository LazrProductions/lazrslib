package com.lazrproductions.lazrslib.common.network.packet;

import java.util.function.Supplier;

import com.lazrproductions.lazrslib.common.network.LazrNetworkedParameterHandler;
import com.lazrproductions.lazrslib.common.network.base.ILazrPacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.fmllegacy.network.NetworkDirection;
import net.minecraftforge.fmllegacy.network.NetworkEvent;
import net.minecraftforge.fmllegacy.network.NetworkEvent.Context;

public abstract class ParameterizedLazrPacket implements ILazrPacket {

    Object[] parameters;

    public ParameterizedLazrPacket(FriendlyByteBuf buffer) {
        LazrNetworkedParameterHandler.readPacket(this, buffer);
    }
    public ParameterizedLazrPacket(Object... p) {
        this.parameters = p;
    }

    @Override
    public void encode(FriendlyByteBuf buffer) {
        LazrNetworkedParameterHandler.writePacketParameters(parameters, buffer);
    }


    public void setFrom(Object[] parameters) {
        this.parameters = parameters;
        loadValues(parameters);
    }
    public abstract void loadValues(Object[] parameters);

    public void handle(Supplier<Context> context) {
        NetworkEvent.Context ctx = context.get();
        ctx.enqueueWork(() -> {
            NetworkDirection dir = ctx.getDirection();
            if(dir == NetworkDirection.PLAY_TO_SERVER || dir == NetworkDirection.LOGIN_TO_SERVER)
                handleServerside(context);
            else
                handleClientside(context);
        });
        ctx.setPacketHandled(true);
    }


    public abstract void handleClientside(Supplier<Context> supplier);
    public abstract void handleServerside(Supplier<Context> supplier);

}
