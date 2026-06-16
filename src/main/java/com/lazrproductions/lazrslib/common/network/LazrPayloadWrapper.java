package com.lazrproductions.lazrslib.common.network;

import com.lazrproductions.lazrslib.common.network.base.ILazrPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class LazrPayloadWrapper<T extends ILazrPacket> implements CustomPacketPayload {
    private final Type<LazrPayloadWrapper<T>> type;
    private final T packet;

    public LazrPayloadWrapper(Type<LazrPayloadWrapper<T>> type, T packet) {
        this.type = type;
        this.packet = packet;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return this.type;
    }

    public T getPacket() {
        return this.packet;
    }
}
