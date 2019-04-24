package example.downward;

import example.MessageListener;
import cabaletta.comms.iMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Example response
 *
 * @author leijurv
 */
public class MessagePong implements iMessage<MessageListener> {

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
    public void handle(MessageListener listener) {
        listener.handle(this);
    }
}
