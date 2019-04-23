package cabaletta.comms;

import java.io.*;

/**
 * A serializer with the capability of utilizing a {@link IMessageDeserializer} and
 * {@link IMessageSerializer} to read and write an {@link iMessage}, respectively.
 *
 * @author leijurv
 */
public class SerializedConnection<S, R> implements IConnection<S, R> {

    private final DataInputStream in;
    private final DataOutputStream out;
    private final IMessageDeserializer deserializer;
    private final IMessageSerializer serializer;

    public SerializedConnection(InputStream in, OutputStream out) {
        this(DefaultDeserializer.INSTANCE, DefaultSerializer.INSTANCE, in, out);
    }

    public SerializedConnection(IMessageDeserializer d, IMessageSerializer s, InputStream in, OutputStream out) {
        this.in = new DataInputStream(in);
        this.out = new DataOutputStream(out);
        this.deserializer = d;
        this.serializer = s;
    }

    @Override
    public synchronized void sendMessage(iMessage<S> message) throws IOException {
        serializer.write(out, message);
    }

    @Override
    public iMessage<R> receiveMessage() throws IOException {
        // noinspection unchecked
        return (iMessage<R>) deserializer.deserialize(in);
    }

    @Override
    public void close() {
        try {
            in.close();
        } catch (IOException ignored) {}
        try {
            out.close();
        } catch (IOException ignored) {}
    }
}