package cabaletta.comms;

import java.io.IOException;

public interface IConnection {
    void sendMessage(iMessage message) throws IOException;

    iMessage receiveMessage() throws IOException;

    void close();
}
