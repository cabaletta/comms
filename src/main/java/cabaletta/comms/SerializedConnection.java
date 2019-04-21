package cabaletta.comms;

import java.io.*;

/**
 * @author leijurv
 */
public class SerializedConnection implements IConnection {

    private final DataInputStream in;
    private final DataOutputStream out;
    private final MessageDeserializer deserializer;

    public SerializedConnection(InputStream in, OutputStream out) {
        this(ConstructingDeserializer.INSTANCE, in, out);
    }

    public SerializedConnection(MessageDeserializer d, InputStream in, OutputStream out) {
        this.in = new DataInputStream(in);
        this.out = new DataOutputStream(out);
        this.deserializer = d;
    }

    @Override
    public synchronized void sendMessage(iMessage message) throws IOException {
        deserializer.writeHeader(out, message);
        message.write(out);
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