package cabaletta.comms;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Brady
 */
public interface MessageSerializer {

    void write(DataOutputStream out, iMessage message) throws IOException;
}
