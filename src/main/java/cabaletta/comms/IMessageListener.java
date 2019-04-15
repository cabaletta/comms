package cabaletta.comms;

import cabaletta.comms.downward.MessagePingResponse;
import cabaletta.comms.upward.MessagePing;

public interface IMessageListener {
    default void handle(MessagePing message) {
        unhandled(message);
    }

    default void handle(MessagePingResponse message) {
        unhandled(message);
    }

    default void unhandled(iMessage msg) {
        // can override this to throw UnsupportedOperationException, if you want to make sure you're handling everything
        // default is to silently ignore messages without handlers
    }
}