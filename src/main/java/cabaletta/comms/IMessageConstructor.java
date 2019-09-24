package cabaletta.comms;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * @author Brady
 */
@FunctionalInterface
public interface IMessageConstructor<T extends IMessage> {

    T instantiate(DataInputStream in) throws IOException;
}
