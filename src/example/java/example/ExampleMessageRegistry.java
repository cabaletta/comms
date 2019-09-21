package example;

import cabaletta.comms.AbstractMessageRegistry;
import example.downward.MessagePong;
import example.upward.MessagePing;

/**
 * @author Brady
 */
public class ExampleMessageRegistry extends AbstractMessageRegistry {

    static final ExampleMessageRegistry INSTANCE = new ExampleMessageRegistry();

    private ExampleMessageRegistry() {
        this.register(MessagePing.class, MessagePing::new);
        this.register(MessagePong.class, MessagePong::new);
    }
}
