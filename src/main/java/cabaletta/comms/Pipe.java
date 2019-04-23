package cabaletta.comms;

import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Do you want a socket to localhost without actually making a gross real socket to localhost?
 *
 * @author leijurv
 */
public class Pipe<T, U> {

    private final LinkedBlockingQueue<iMessage<T>> AtoB;
    private final LinkedBlockingQueue<iMessage<U>> BtoA;
    private final PipedConnection<T, U> A;
    private final PipedConnection<U, T> B;
    private volatile boolean closed;

    public Pipe() {
        this.AtoB = new LinkedBlockingQueue<>();
        this.BtoA = new LinkedBlockingQueue<>();
        this.A = new PipedConnection<>(BtoA, AtoB);
        this.B = new PipedConnection<>(AtoB, BtoA);
    }

    public PipedConnection<T, U> getA() {
        return A;
    }

    public PipedConnection<U, T> getB() {
        return B;
    }

    public class PipedConnection<S, R> implements IConnection<S, R> {
        private final LinkedBlockingQueue<iMessage<R>> in;
        private final LinkedBlockingQueue<iMessage<S>> out;

        private PipedConnection(LinkedBlockingQueue<iMessage<R>> in, LinkedBlockingQueue<iMessage<S>> out) {
            this.in = in;
            this.out = out;
        }

        @Override
        public void sendMessage(iMessage<S> message) throws IOException {
            if (Pipe.this.closed) {
                throw new EOFException("Closed");
            }
            try {
                this.out.put(message);
            } catch (InterruptedException e) {
                // this can never happen since the LinkedBlockingQueues are not constructed with a maximum capacity, see above
            }
        }

        @Override
        public iMessage<R> receiveMessage() throws IOException {
            if (Pipe.this.closed) {
                throw new EOFException("Closed");
            }
            try {
                iMessage<R> t = this.in.take();
                if (t instanceof TerminationMessage) {
                    throw new EOFException("Closed");
                }
                return t;
            } catch (InterruptedException e) {
                // again, cannot happen
                // but we have to throw something
                throw new IllegalStateException(e);
            }
        }

        @Override
        public void close() {
            Pipe.this.closed = true; // Prevent further messages from being sent and received
            try {
                // Un-stick the threads
                Pipe.this.AtoB.put(TerminationMessage.INSTANCE);
                Pipe.this.BtoA.put(TerminationMessage.INSTANCE);
            } catch (InterruptedException ignored) {}
        }
    }

    /**
     * A message passed to the internal receive queues to terminate their operation
     */
    private enum TerminationMessage implements iMessage {
        INSTANCE;

        @Override
        public void write(DataOutputStream out) {}

        @Override
        public void handle(Object listener) {}
    }
}
