package cabaletta.comms.downward;

import cabaletta.comms.IMessageListener;
import cabaletta.comms.iMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Example response
 *
 * @author leijurv
 */
public class MessagePong implements iMessage {

    public final int sequence;

    public MessagePong(DataInputStream in) throws IOException {
        sequence = in.readInt();
    }

    public MessagePong(int sequence) {
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
