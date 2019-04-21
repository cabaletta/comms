package cabaletta.comms;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author leijurv
 */
public interface MessageDeserializer {

    iMessage deserialize(DataInputStream in) throws IOException;

    void writeHeader(DataOutputStream out, iMessage message) throws IOException;
}
