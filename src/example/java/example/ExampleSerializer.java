package example;

import cabaletta.comms.IMessageSerializer;
import cabaletta.comms.iMessage;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * The default {@link IMessageSerializer} implementation, which writes a unsigned short
 * as a "header" before writing a message, which indicates the ID of the message.
 *
 * @author Brady
 */
public enum ExampleSerializer implements IMessageSerializer {
    INSTANCE;

    @Override
    public void write(DataOutputStream out, iMessage message) throws IOException {
        out.writeShort(ExampleMessageRegistry.INSTANCE.getId(message.getClass()));
        message.write(out);
    }
}
