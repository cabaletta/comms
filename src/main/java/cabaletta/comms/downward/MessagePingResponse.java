package cabaletta.comms.downward;

import cabaletta.comms.IMessageListener;
import cabaletta.comms.iMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Example message
 */
public class MessagePingResponse implements iMessage {
    public final int sequence;

    public MessagePingResponse(DataInputStream in) throws IOException {
        sequence = in.readInt();
    }

    public MessagePingResponse(int sequence) {
        this.sequence = sequence;
    }

    @Override
    public void write(DataOutputStream out) throws IOException {
        out.writeInt(sequence);
    }

    @Override
    public void handle(IMessageListener listener) {
        listener.handle(this);
    }
}
