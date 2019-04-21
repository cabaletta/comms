package cabaletta.comms.upward;

import cabaletta.comms.IMessageListener;
import cabaletta.comms.iMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Example message
 *
 * @author leijurv
 */
public class MessagePing implements iMessage {

    public final int sequence;

    public MessagePing(DataInputStream in) throws IOException {
        sequence = in.readInt();
    }

    public MessagePing(int sequence) {
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
