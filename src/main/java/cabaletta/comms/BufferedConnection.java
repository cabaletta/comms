package cabaletta.comms;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Do you not like having a blocking "receiveMessage" thingy?
 * <p>
 * Do you prefer just being able to get a list of all messages received since the last tick?
 * <p>
 * If so, this class is for you!
 *
 * @author leijurv
 */
public class BufferedConnection<S, R> implements IBufferedConnection<S, R> {

    private final IConnection<S, R> wrapped;
    private final LinkedBlockingQueue<IMessage<R>> queue;
    private volatile transient IOException thrownOnRead;

    public BufferedConnection(IConnection<S, R> wrapped) {
        this(wrapped, Integer.MAX_VALUE); // LinkedBlockingQueue accepts this as "no limit"
    }

    public BufferedConnection(IConnection<S, R> wrapped, int maxInternalQueueSize) {
        this.wrapped = wrapped;
        this.queue = new LinkedBlockingQueue<>(maxInternalQueueSize);
        this.thrownOnRead = null;
        new Thread(() -> {
            try {
                while (thrownOnRead == null) {
                    queue.put(wrapped.receiveMessage());
                }
            } catch (IOException e) {
                thrownOnRead = e;
            } catch (InterruptedException e) {
                thrownOnRead = new IOException("Interrupted while enqueueing", e);
            }
        }).start();
    }

    @Override
    public void sendMessage(IMessage<S> message) throws IOException {
        wrapped.sendMessage(message);
    }

    @Override
    public IMessage<R> receiveMessage() {
        throw new UnsupportedOperationException("BufferedConnection can only be read from non-blockingly");
    }

    @Override
    public void close() {
        wrapped.close();
        thrownOnRead = new EOFException("Closed");
    }

    @Override
    public List<IMessage<R>> receiveMessagesNonBlocking() throws IOException {
        List<IMessage<R>> msgs = new ArrayList<>();
        queue.drainTo(msgs); // preserves order -- first message received will be first in this arraylist
        if (msgs.isEmpty() && thrownOnRead != null) {
            throw new IOException("BufferedConnection wrapped", thrownOnRead);
        }
        return msgs;
    }

    public static BufferedConnection makeBuffered(IConnection<?, ?> conn) {
        if (conn instanceof BufferedConnection) {
            return (BufferedConnection) conn;
        } else {
            return new BufferedConnection<>(conn);
        }
    }
}
