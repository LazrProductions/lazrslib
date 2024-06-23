package com.lazrproductions.lazrslib.testing;

import com.lazrproductions.lazrslib.LazrsLibMod;
import com.lazrproductions.lazrslib.common.network.LazrNetwork;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class TestingAPI {
    public static final LazrNetwork NETWORK = new LazrNetwork(new ResourceLocation(LazrsLibMod.MODID, "main"), Integer.toString(1));

    public static final void registerPackets() {
        NETWORK.registerPacket(TestServerBoundPacket.class, TestServerBoundPacket::new);
        NETWORK.registerPacket(TestClientBoundPacket.class, TestClientBoundPacket::new);
    }

    public static final void sendTestPacketToServer(String message) {
        TestServerBoundPacket packet = new TestServerBoundPacket(message);
        NETWORK.sendToServer(packet);
    }
    public static final void sendTestPacketToClient(ServerPlayer client, String message) {
        TestServerBoundPacket packet = new TestServerBoundPacket(message);
        NETWORK.sendTo(packet, client);
    }
}
