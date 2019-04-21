package cabaletta.comms;

import cabaletta.comms.downward.MessagePong;
import cabaletta.comms.upward.MessagePing;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author leijurv, Brady
 */
public enum DefaultDeserializer implements MessageDeserializer {
    INSTANCE;

    private final List<Class<? extends iMessage>> MSGS;

    DefaultDeserializer() {
        MSGS = new ArrayList<>();
        MSGS.add(MessagePing.class);
        MSGS.add(MessagePong.class);
    }

    @Override
    public iMessage deserialize(DataInputStream in) throws IOException {
        int id = in.readUnsignedShort();
        try {
            return MSGS.get(id).getConstructor(DataInputStream.class).newInstance(in);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException ex) {
            throw new IOException("Unknown message type " + id, ex);
        }
    }

    public int getId(Class<? extends iMessage> klass) {
        return MSGS.indexOf(klass) & 0xffff;
    }
}
