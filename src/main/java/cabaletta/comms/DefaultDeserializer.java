package cabaletta.comms;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * The default {@link MessageDeserializer} implementation, which first reads the input
 * stream for an unsigned short, indicating the packet id, then attempts to resolve and
 * construct the packet.
 *
 * @author leijurv, Brady
 */
public enum DefaultDeserializer implements MessageDeserializer {
    INSTANCE;

    @Override
    public iMessage deserialize(DataInputStream in) throws IOException {
        int id = in.readUnsignedShort();
        try {
            return DefaultMessageRegistry.INSTANCE.construct(id, in);
        } catch (IllegalArgumentException ex) {
            throw new IOException(ex);
        }
    }
}
