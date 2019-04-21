package cabaletta.comms;

import java.io.*;

/**
 * A serializer with the capability of utilizing a {@link MessageDeserializer} and
 * {@link MessageSerializer} to read and write an {@link iMessage}, respectively.
 *
 * @author leijurv
 */
public class SerializedConnection implements IConnection {

    private final DataInputStream in;
    private final DataOutputStream out;
    private final MessageDeserializer deserializer;
    private final MessageSerializer serializer;

    public SerializedConnection(InputStream in, OutputStream out) {
        this(DefaultDeserializer.INSTANCE, DefaultSerializer.INSTANCE, in, out);
    }

    public SerializedConnection(MessageDeserializer d, MessageSerializer s, InputStream in, OutputStream out) {
        this.in = new DataInputStream(in);
        this.out = new DataOutputStream(out);
        this.deserializer = d;
        this.serializer = s;
    }

    @Override
    public synchronized void sendMessage(iMessage message) throws IOException {
        serializer.write(out, message);
    }

    @Override
    public iMessage receiveMessage() throws IOException {
        return deserializer.deserialize(in);
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