package cabaletta.comms;

import java.io.IOException;
import java.util.List;

/**
 * @author Brady
 * @since 4/21/2019
 */
public interface IBufferedConnection extends IConnection {

    List<iMessage> receiveMessagesNonBlocking() throws IOException;

    default void handleAllPendingMessages(IMessageListener listener) throws IOException {
        receiveMessagesNonBlocking().forEach(msg -> msg.handle(listener));
    }
}
