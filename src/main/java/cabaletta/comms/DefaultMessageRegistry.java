package cabaletta.comms;

import cabaletta.comms.downward.MessagePong;
import cabaletta.comms.upward.MessagePing;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brady
 */
public enum DefaultMessageRegistry {
    INSTANCE;

    private final List<Class<? extends iMessage>> MSGS;

    DefaultMessageRegistry() {
        MSGS = new ArrayList<>();
        MSGS.add(MessagePing.class);
        MSGS.add(MessagePong.class);
    }

    public int getId(Class<? extends iMessage> klass) {
        return MSGS.indexOf(klass) & 0xffff;
    }

    public Class<? extends iMessage> getById(int id) {
        return MSGS.get(id);
    }
}
