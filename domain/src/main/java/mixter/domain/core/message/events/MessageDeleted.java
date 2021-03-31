package mixter.domain.core.message.events;

import mixter.domain.AggregateId;
import mixter.domain.Event;
import mixter.domain.core.message.MessageId;

import java.util.Objects;

public class MessageDeleted implements Event {
    private final MessageId messageId;

    public MessageDeleted(MessageId messageId) {
        this.messageId = messageId;
    }

    @Override
    public AggregateId getId() {
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageDeleted that = (MessageDeleted) o;
        return Objects.equals(messageId, that.messageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId);
    }
}
