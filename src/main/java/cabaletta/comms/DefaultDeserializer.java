package cabaletta.comms;

import java.io.DataInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * @author leijurv, Brady
 */
public enum DefaultDeserializer implements MessageDeserializer {
    INSTANCE;

    @Override
    public iMessage deserialize(DataInputStream in) throws IOException {
        int id = in.readUnsignedShort();
        try {
            return DefaultMessageRegistry.INSTANCE.getById(id).getConstructor(DataInputStream.class).newInstance(in);
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException ex) {
            throw new IOException("Unknown message type " + id, ex);
        }
    }
}
