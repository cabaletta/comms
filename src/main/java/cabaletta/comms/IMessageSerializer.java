package cabaletta.comms;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Brady
 */
public interface IMessageSerializer {

    void write(DataOutputStream out, IMessage<?> message) throws IOException;
}
