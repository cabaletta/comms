package cabaletta.comms;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * The default {@link MessageSerializer} implementation, which writes a unsigned short
 * as a "header" before writing a message, which indicates the ID of the message.
 *
 * @author Brady
 */
public enum DefaultSerializer implements MessageSerializer {
    INSTANCE;

    @Override
    public void write(DataOutputStream out, iMessage message) throws IOException {
        out.writeShort(DefaultMessageRegistry.INSTANCE.getId(message.getClass()));
        message.write(out);
    }
}
