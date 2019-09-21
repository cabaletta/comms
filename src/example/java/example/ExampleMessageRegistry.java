package example;

import cabaletta.comms.AbstractMessageRegistry;
import example.downward.MessagePong;
import example.upward.MessagePing;

/**
 * @author Brady
 */
public class ExampleMessageRegistry extends AbstractMessageRegistry {

    static final ExampleMessageRegistry INSTANCE = new ExampleMessageRegistry();
    private ExampleMessageRegistry() {}

    @Override
    protected void registerMessages() {
        register(MessagePing.class, MessagePing::new);
        register(MessagePong.class, MessagePong::new);
    }
}
