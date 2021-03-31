package mixter.domain.core.message.events;

import mixter.domain.AggregateId;
import mixter.domain.Event;
import mixter.domain.core.message.MessageId;
import mixter.domain.identity.UserId;

import java.util.Objects;

public class MessageQuacked implements Event {
    private final MessageId messageId;
    private final String message;
    private final UserId authorId;

    public MessageQuacked(MessageId messageId, String message, UserId authorId) {
        this.messageId = messageId;
        this.message = message;
        this.authorId = authorId;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageQuacked that = (MessageQuacked) o;

        if (!Objects.equals(messageId, that.messageId)) return false;
        if (!Objects.equals(message, that.message)) return false;
        return Objects.equals(authorId, that.authorId);

    }

    @Override
    public int hashCode() {
        int result = messageId != null ? messageId.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (authorId != null ? authorId.hashCode() : 0);
        return result;
    }

    public MessageId getMessageId() {
        return messageId;
    }

    public UserId getAuthorId() {
        return authorId;
    }

    @Override
    public AggregateId getId() {
        return messageId;
    }
}
