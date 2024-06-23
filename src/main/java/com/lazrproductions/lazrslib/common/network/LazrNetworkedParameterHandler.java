package com.lazrproductions.lazrslib.common.network;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.annotation.Nonnull;

import org.apache.commons.io.output.ByteArrayOutputStream;

import com.lazrproductions.lazrslib.LazrsLibMod;
import com.lazrproductions.lazrslib.common.network.exception.InvalidLazrNetworkParameterException;
import com.lazrproductions.lazrslib.common.network.packet.ParameterizedLazrPacket;

import net.minecraft.network.FriendlyByteBuf;

public class LazrNetworkedParameterHandler {
    public static final <T extends ParameterizedLazrPacket> void writePacketParameters(Object[] parameters,
            FriendlyByteBuf buffer) {
        buffer.writeInt(parameters.length);
        for (Object p : parameters)
            buffer.writeByteArray(serialize(p));
    }

    public static final <T extends ParameterizedLazrPacket> void readPacket(T packet, FriendlyByteBuf buffer) {
        ArrayList<Object> list = new ArrayList<>(0);
        int length = buffer.readInt();

        for (int i = 0; i < length; i++) {
            Object obj = deserialize(buffer.readByteArray());
            list.add(obj);
        }
        packet.setFrom(list.toArray());
    }

    static final byte[] serialize(@Nonnull Object obj) {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        try (ObjectOutputStream out = new ObjectOutputStream(bos)) {
            out.writeObject(obj);
            out.flush();
            out.close();
            return bos.toByteArray();
        } catch (Exception ex) {
            throw new InvalidLazrNetworkParameterException(
                    "Could not serialize network parameter of type " + obj.getClass().getName(), ex);
        }
    }

    static final Object deserialize(byte[] bytes) {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);

        

        try (ObjectInput in = new ObjectInputStream(bis)) {
            Object o = in.readObject();
            in.close();
            return o;
        } catch (Exception ex) {
            InvalidLazrNetworkParameterException e = new InvalidLazrNetworkParameterException("Could not deserialize network parameter! " + ex.getMessage(), ex);
            LazrsLibMod.LOGGER.error(e);
            throw e;
        }
    }
}
