package cabaletta.comms;

import java.io.IOException;
import java.net.Socket;

/**
 * Implementation of a {@link SerializedConnection} that uses the input and output streams provided by a {@link Socket}
 *
 * @author leijurv
 */
public class SocketConnection<S, R> extends SerializedConnection<S, R> {

    public SocketConnection(IMessageDeserializer d, IMessageSerializer s, Socket socket) throws IOException {
        super(d, s, socket.getInputStream(), socket.getOutputStream());
    }
}
