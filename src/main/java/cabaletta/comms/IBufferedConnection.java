package cabaletta.comms;

import java.io.IOException;
import java.util.List;

/**
 * @author Brady
 * @since 4/21/2019
 */
public interface IBufferedConnection<S, R> extends IConnection<S, R> {

    List<iMessage<R>> receiveMessagesNonBlocking() throws IOException;

    default void handleAllPendingMessages(R listener) throws IOException {
        receiveMessagesNonBlocking().forEach(msg -> msg.handle(listener));
    }
}
