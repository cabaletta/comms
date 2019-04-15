package cabaletta.comms;

import java.io.DataInputStream;
import java.io.IOException;

public interface MessageDeserializer {
    iMessage deserialize(DataInputStream in) throws IOException;
}
