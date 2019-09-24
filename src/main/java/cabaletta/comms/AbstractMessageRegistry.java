package cabaletta.comms;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Brady
 */
public abstract class AbstractMessageRegistry {

    private final List<Class<? extends IMessage<?>>> messages = new ArrayList<>();
    private final Int2ObjectMap<IMessageConstructor<?>> constructors = new Int2ObjectOpenHashMap<>();

    /**
     * Returns the ID of a message given the message's class.
     *
     * @param clazz The class of the message
     * @return The ID for the message
     */
    public int getId(Class<? extends IMessage<?>> clazz) {
        return this.messages.indexOf(clazz) & 0xFFFF;
    }

    /**
     * Returns the class of the message associated with the specified id.
     * The return value will be {@code null} if the ID does not have a
     * corresponding message.
     *
     * @param id The ID of the message
     * @return The class for the message ID
     */
    public Class<? extends IMessage<?>> getById(int id) {
        return this.messages.get(id);
    }

    /**
     * @param id The id of the message
     * @param in The input stream that the message will read from
     * @param <T> The type of message
     * @return A newly constructed message
     * @throws IllegalArgumentException If the specified id was invalid
     * @throws IOException If some exception occurred within the message constructor
     */
    public <T extends IMessage<?>> T construct(int id, DataInputStream in) throws IllegalArgumentException, IOException {
        IMessageConstructor<?> constructor = this.constructors.get(id);
        if (constructor == null) {
            throw new IllegalArgumentException("Unknown packet type " + id);
        }
        // noinspection unchecked
        return (T) constructor.instantiate(in);
    }

    /**
     * Registers a new message to this {@link AbstractMessageRegistry}. If the specified class already
     * exists in the {@link #messages} list, then the new constructor will not override the old one, and
     * {@code false} will be returned. Otherwise, {@code true} will be returned.
     *
     * @param clazz The message type
     * @param constructor A constructor for the message
     * @return Whether or not the message was successfully registered
     */
    protected <T extends IMessage<?>> boolean register(Class<T> clazz, IMessageConstructor<T> constructor) {
        if (!this.messages.contains(clazz)) {
            this.messages.add(clazz);
            this.constructors.put(this.getId(clazz), constructor);
            return true;
        }
        return false;
    }
}
