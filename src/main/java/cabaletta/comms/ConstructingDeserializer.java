package cabaletta.comms;

import cabaletta.comms.downward.MessagePingResponse;
import cabaletta.comms.upward.MessagePing;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public enum ConstructingDeserializer implements MessageDeserializer {
    INSTANCE;
    private final List<Class<? extends iMessage>> MSGS;

    ConstructingDeserializer() {
        MSGS = new ArrayList<>();
        MSGS.add(MessagePing.class);
        MSGS.add(MessagePingResponse.class);
    }

    @Override
    public synchronized iMessage deserialize(DataInputStream in) throws IOException {
        int type = ((int) in.readByte()) & 0xff;
        try {
            return MSGS.get(type).getConstructor(DataInputStream.class).newInstance(in);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException ex) {
            throw new IOException("Unknown message type " + type, ex);
        }
    }

    public byte getHeader(Class<? extends iMessage> klass) {
        return (byte) MSGS.indexOf(klass);
    }
}
