package cabaletta.comms;

import cabaletta.comms.downward.MessagePong;
import cabaletta.comms.upward.MessagePing;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author Brady
 */
public enum DefaultMessageRegistry {
    INSTANCE;

    private final List<Class<? extends iMessage>> msgs = new ArrayList<>();
    private final Int2ObjectMap<iMessageConstructor<?>> constructors = new Int2ObjectOpenHashMap<>();

    DefaultMessageRegistry() {
        register(MessagePing.class, MessagePing::new);
        register(MessagePong.class, MessagePong::new);
    }

    public int getId(Class<? extends iMessage> clazz) {
        return this.msgs.indexOf(clazz) & 0xffff;
    }

    public Class<? extends iMessage> getById(int id) {
        return this.msgs.get(id);
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
        iMessageConstructor<?> constructor = constructors.get(id);
        if (constructor == null) {
            throw new IllegalArgumentException("Unknown packet type " + id);
        }

        // noinspection unchecked
        return (T) constructor.instantiate(in);
    }

    private void register(Class<? extends iMessage> clazz, iMessageConstructor constructor) {
        this.msgs.add(clazz);
        this.constructors.put(getId(clazz), constructor);
    }

    @FunctionalInterface
    private interface iMessageConstructor<T extends iMessage> {

        T instantiate(DataInputStream in) throws IOException;
    }
}
