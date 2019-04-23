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
 * @param <T> The handler type of this message
 *
 * @author leijurv, Brady
 */
public interface iMessage<T> {

    void write(DataOutputStream out) throws IOException;

    void handle(T listener);
}
