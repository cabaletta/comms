package cabaletta.comms;

import cabaletta.comms.downward.MessagePong;
import cabaletta.comms.upward.MessagePing;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brady
 */
public enum DefaultMessageRegistry {
    INSTANCE;

    private final List<Class<? extends iMessage>> msgs;
    private final Map<Class<? extends iMessage>, iMessageConstructor> constructors;

    DefaultMessageRegistry() {
        msgs = new ArrayList<>();
        constructors = new LinkedHashMap<>();
        register(MessagePing.class, MessagePing::new);
        register(MessagePong.class, MessagePong::new);
    }

    public int getId(Class<? extends iMessage> klass) {
        return msgs.indexOf(klass) & 0xffff;
    }

    public Class<? extends iMessage> getById(int id) {
        return msgs.get(id);
    }

    /**
     * @param id The id of the message
     * @param in The input stream that the message will read from
     * @param <T> The type of message
     * @return A newly constructed message
     * @throws IllegalArgumentException If the specified id was invalid
     * @throws IOException If some exception occurred within the message constructor
     */
    public <T extends iMessage> T construct(int id, DataInputStream in) throws IllegalArgumentException, IOException {
        Class<? extends iMessage> msg = msgs.get(id);
        if (msg == null) {
            throw new IllegalArgumentException("Unknown packet type " + id);
        }

        // noinspection unchecked
        return (T) constructors.get(msgs.get(id)).instantiate(in);
    }

    private void register(Class<? extends iMessage> clazz, iMessageConstructor constructor) {
        this.msgs.add(clazz);
        this.constructors.put(clazz, constructor);
    }

    @FunctionalInterface
    private interface iMessageConstructor<T extends iMessage> {

        T instantiate(DataInputStream in) throws IOException;
    }
}
