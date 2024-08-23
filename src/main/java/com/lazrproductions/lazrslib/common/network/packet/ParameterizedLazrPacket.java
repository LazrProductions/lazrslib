package com.lazrproductions.lazrslib.common.network.packet;

import com.lazrproductions.lazrslib.common.network.LazrNetworkedParameterHandler;
import com.lazrproductions.lazrslib.common.network.base.ILazrPacket;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.event.network.CustomPayloadEvent;

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

    public void handle(CustomPayloadEvent.Context ctx) {
        ctx.enqueueWork(() -> {
            if(ctx.isServerSide())
                handleServerside(ctx);
            else
                handleClientside(ctx);
        });
        ctx.setPacketHandled(true);
    }


    public abstract void handleClientside(CustomPayloadEvent.Context supplier);
    public abstract void handleServerside(CustomPayloadEvent.Context supplier);
}
