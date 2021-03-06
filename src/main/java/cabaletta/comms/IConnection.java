package cabaletta.comms;

import java.io.IOException;

/**
 * @param <S> The type of handler for outgoing packets
 * @param <R> The type of handler for incoming packets
 *
 * @author leijurv
 */
public interface IConnection<S, R> {

    void sendMessage(IMessage<S> message) throws IOException;

    IMessage<R> receiveMessage() throws IOException;

    void close();
}
