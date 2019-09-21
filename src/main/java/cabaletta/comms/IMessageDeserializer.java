package cabaletta.comms;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author leijurv
 */
public interface IMessageDeserializer {

    IMessage<?> deserialize(DataInputStream in) throws IOException;
}
