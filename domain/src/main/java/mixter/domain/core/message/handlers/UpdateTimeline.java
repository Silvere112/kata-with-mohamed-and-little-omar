package mixter.domain.core.message.handlers;

import mixter.domain.core.message.TimelineMessageProjection;
import mixter.domain.core.message.TimelineMessageRepository;
import mixter.domain.core.message.events.MessageDeleted;
import mixter.domain.core.message.events.MessageQuacked;

public class UpdateTimeline {
    private TimelineMessageRepository timelineRepository;

    public UpdateTimeline(TimelineMessageRepository timelineRepository) {
        this.timelineRepository = timelineRepository;
    }

    public void apply(MessageQuacked messageQuacked) {
        TimelineMessageProjection timeLineMessage = new TimelineMessageProjection(messageQuacked.getAuthorId(), messageQuacked.getAuthorId(), messageQuacked.getMessage(), messageQuacked.getMessageId());
        timelineRepository.save(timeLineMessage);
    }

    public void apply(MessageDeleted messageDeleted) {
        timelineRepository.delete(messageDeleted.getMessageId());
    }
}
