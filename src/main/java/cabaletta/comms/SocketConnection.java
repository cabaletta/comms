package cabaletta.comms;

import java.io.IOException;
import java.net.Socket;

/**
 * Implementation of a {@link SerializedConnection} that uses the input and output streams provided by a {@link Socket}
 *
 * @author leijurv
 */
public class SocketConnection extends SerializedConnection {

    public SocketConnection(Socket s) throws IOException {
        super(s.getInputStream(), s.getOutputStream());
    }
}
