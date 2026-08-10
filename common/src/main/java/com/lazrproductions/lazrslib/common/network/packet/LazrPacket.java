package com.lazrproductions.lazrslib.common.network.packet;

import com.lazrproductions.lazrslib.common.network.handling.IPacketContext;
import com.lazrproductions.lazrslib.common.network.handling.LazrNetworkedParameterHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import org.jetbrains.annotations.NotNull;

public abstract class LazrPacket implements ILazrPacket {
    Type<? extends ILazrPacket> type;

    Object[] parameters;

    public LazrPacket(FriendlyByteBuf buffer) {
        LazrNetworkedParameterHandler.readPacket(this, buffer);
    }
    public LazrPacket(Object... p) {
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

    public void handle(IPacketContext ctx) {
        ctx.enqueueWork(() -> {
            if(ctx.side().isServerSide())
                handleServerside(ctx);
            else
                handleClientside(ctx);
        });
        ctx.setHandled(true);
    }

    @Override
    public @NotNull Type<? extends CustomPacketPayload> type() {
        return type;
    }
    @Override
    public void setType(Type<? extends ILazrPacket> type) {
        this.type = type;
    }

    public abstract void handleClientside(IPacketContext context);
    public abstract void handleServerside(IPacketContext context);
}