package cabaletta.comms;

import java.io.IOException;
import java.util.List;

/**
 * @author Brady
 */
public interface IBufferedConnection<S, R> extends IConnection<S, R> {

    List<IMessage<R>> receiveMessagesNonBlocking() throws IOException;

    default void handleAllPendingMessages(R listener) throws IOException {
        receiveMessagesNonBlocking().forEach(msg -> msg.handle(listener));
    }
}
