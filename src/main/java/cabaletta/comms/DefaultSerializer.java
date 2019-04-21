package cabaletta.comms;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Brady
 */
public enum DefaultSerializer implements MessageSerializer {
    INSTANCE;

    @Override
    public void write(DataOutputStream out, iMessage message) throws IOException {
        out.writeShort(DefaultDeserializer.INSTANCE.getId(message.getClass()));
        message.write(out);
    }
}
