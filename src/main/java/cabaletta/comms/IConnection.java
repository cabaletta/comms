package cabaletta.comms;

import java.io.IOException;

/**
 * @author leijurv
 */
public interface IConnection {

    void sendMessage(iMessage message) throws IOException;

    iMessage receiveMessage() throws IOException;

    void close();
}
