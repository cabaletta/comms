package cabaletta.comms;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * hell yeah
 * <p>
 * <p>
 * dumb android users cant read this file
 * <p>
 *
 * @author leijurv
 */
public interface iMessage {
    void write(DataOutputStream out) throws IOException;

    default void writeHeader(DataOutputStream out) throws IOException {
        out.writeShort(getHeader());
    }

    default int getHeader() {
        return ConstructingDeserializer.INSTANCE.getHeader(getClass());
    }

    void handle(IMessageListener listener);
}
