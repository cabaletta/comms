package cabaletta.comms;

import java.io.IOException;
import java.net.Socket;

public class SocketConnection extends SerializedConnection {
    public SocketConnection(Socket s) throws IOException {
        super(s.getInputStream(), s.getOutputStream());
    }
}
