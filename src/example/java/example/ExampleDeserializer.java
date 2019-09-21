package example;

import cabaletta.comms.IMessageDeserializer;
import cabaletta.comms.IMessage;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * The default {@link IMessageDeserializer} implementation, which first reads the input
 * stream for an unsigned short, indicating the packet id, then attempts to resolve and
 * construct the packet.
 *
 * @author leijurv, Brady
 */
public enum ExampleDeserializer implements IMessageDeserializer {
    INSTANCE;

    @Override
    public IMessage deserialize(DataInputStream in) throws IOException {
        int id = in.readUnsignedShort();
        try {
            return ExampleMessageRegistry.INSTANCE.construct(id, in);
        } catch (IllegalArgumentException ex) {
            throw new IOException(ex);
        }
    }
}
