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
public class Pipe<T extends iMessage> {

    private final LinkedBlockingQueue<iMessage> AtoB;
    private final LinkedBlockingQueue<iMessage> BtoA;
    private final PipedConnection A;
    private final PipedConnection B;
    private volatile boolean closed;

    public Pipe() {
        this.AtoB = new LinkedBlockingQueue<>();
        this.BtoA = new LinkedBlockingQueue<>();
        this.A = new PipedConnection(BtoA, AtoB);
        this.B = new PipedConnection(AtoB, BtoA);
    }

    public PipedConnection getA() {
        return A;
    }

    public PipedConnection getB() {
        return B;
    }

    public class PipedConnection implements IConnection {
        private final LinkedBlockingQueue<iMessage> in;
        private final LinkedBlockingQueue<iMessage> out;

        private PipedConnection(LinkedBlockingQueue<iMessage> in, LinkedBlockingQueue<iMessage> out) {
            this.in = in;
            this.out = out;
        }

        @Override
        public void sendMessage(iMessage message) throws IOException {
            if (closed) {
                throw new EOFException("Closed");
            }
            try {
                out.put(message);
            } catch (InterruptedException e) {
                // this can never happen since the LinkedBlockingQueues are not constructed with a maximum capacity, see above
            }
        }

        @Override
        public iMessage receiveMessage() throws IOException {
            if (closed) {
                throw new EOFException("Closed");
            }
            try {
                iMessage t = in.take();
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
            closed = true; // Prevent further messages from being sent and received
            try {
                // Un-stick the threads
                AtoB.put(TerminationMessage.INSTANCE);
                BtoA.put(TerminationMessage.INSTANCE);
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
        public void handle(IMessageListener listener) {}
    }
}
