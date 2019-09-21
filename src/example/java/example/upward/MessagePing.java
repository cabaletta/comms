package example.upward;

import example.MessageListener;
import cabaletta.comms.IMessage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Example message
 *
 * @author leijurv
 */
public class MessagePing implements IMessage<MessageListener> {

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
    public void handle(MessageListener listener) {
        listener.handle(this);
    }
}
