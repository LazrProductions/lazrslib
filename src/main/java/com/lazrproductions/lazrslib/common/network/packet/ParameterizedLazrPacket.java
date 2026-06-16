package com.lazrproductions.lazrslib.common.network.packet;

import java.util.function.Supplier;

import com.lazrproductions.lazrslib.common.network.LazrNetworkedParameterHandler;
import com.lazrproductions.lazrslib.common.network.base.ILazrPacket;

import net.minecraft.network.FriendlyByteBuf;
import net.neoforged.neoforge.network.handling.IPayloadContext;

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

    @Override
    public void handle(IPayloadContext context) {
        context.enqueueWork(() -> {
            if (context.flow() == net.minecraft.network.protocol.PacketFlow.SERVERBOUND)
                handleServerside(context);
            else
                handleClientside(context);
        });
    }


    public abstract void handleClientside(IPayloadContext context);
    public abstract void handleServerside(IPayloadContext context);
}
