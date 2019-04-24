package example;

import cabaletta.comms.iMessage;
import example.downward.MessagePong;
import example.upward.MessagePing;

/**
 * @author leijurv
 */
public class MessageListener {

    public void handle(MessagePing message) {
        unhandled(message);
    }

    public void handle(MessagePong message) {
        unhandled(message);
    }

    private void unhandled(iMessage<?> msg) {
        // Message isn't handled or doesn't have a handler
    }
}