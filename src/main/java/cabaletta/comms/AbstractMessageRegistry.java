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

    private final List<Class<? extends IMessage>> msgs = new ArrayList<>();
    private final Int2ObjectMap<IMessageConstructor<?>> constructors = new Int2ObjectOpenHashMap<>();

    protected AbstractMessageRegistry() {
        this.registerMessages();
    }

    protected abstract void registerMessages();

    public int getId(Class<? extends IMessage> clazz) {
        return this.msgs.indexOf(clazz) & 0xFFFF;
    }

    public Class<? extends IMessage> getById(int id) {
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
    public <T extends IMessage> T construct(int id, DataInputStream in) throws IllegalArgumentException, IOException {
        IMessageConstructor<?> constructor = this.constructors.get(id);
        if (constructor == null) {
            throw new IllegalArgumentException("Unknown packet type " + id);
        }
        // noinspection unchecked
        return (T) constructor.instantiate(in);
    }

    protected <T extends IMessage> void register(Class<T> clazz, IMessageConstructor<T> constructor) {
        this.msgs.add(clazz);
        this.constructors.put(this.getId(clazz), constructor);
    }

    @FunctionalInterface
    public interface IMessageConstructor<T extends IMessage> {

        T instantiate(DataInputStream in) throws IOException;
    }
}
