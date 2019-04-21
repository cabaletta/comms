package cabaletta.comms;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * The default {@link IMessageSerializer} implementation, which writes a unsigned short
 * as a "header" before writing a message, which indicates the ID of the message.
 *
 * @author Brady
 */
public enum DefaultSerializer implements IMessageSerializer {
    INSTANCE;

    @Override
    public void write(DataOutputStream out, iMessage message) throws IOException {
        out.writeShort(DefaultMessageRegistry.INSTANCE.getId(message.getClass()));
        message.write(out);
    }
}
