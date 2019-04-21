package cabaletta.comms;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Brady
 * @since 4/20/2019
 */
public interface MessageSerializer {

    void write(DataOutputStream out, iMessage message) throws IOException;
}
