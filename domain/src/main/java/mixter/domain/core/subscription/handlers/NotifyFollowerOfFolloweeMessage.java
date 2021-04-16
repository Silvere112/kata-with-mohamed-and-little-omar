package mixter.domain.core.subscription.handlers;

import mixter.doc.Handler;
import mixter.domain.EventPublisher;
import mixter.domain.core.message.events.MessageQuacked;
import mixter.domain.core.subscription.FollowerRepository;
import mixter.domain.core.subscription.SubscriptionId;
import mixter.domain.core.subscription.SubscriptionRepository;
import mixter.domain.core.subscription.events.FolloweeMessageQuacked;
import mixter.domain.identity.UserId;

import java.util.Set;

@Handler
public class NotifyFollowerOfFolloweeMessage {

    private FollowerRepository followerRepository;
    private EventPublisher eventPublisher;

    public NotifyFollowerOfFolloweeMessage(FollowerRepository followerRepository, SubscriptionRepository subscriptionRepository, EventPublisher eventPublisher) {
        this.followerRepository = followerRepository;
        this.eventPublisher = eventPublisher;
    }

    public void apply(MessageQuacked messageQuacked) {
        Set<UserId> followers = this.followerRepository.getFollowers(messageQuacked.getAuthorId());
        SubscriptionId subscriptionId = new SubscriptionId(followers.stream().findFirst().get(), messageQuacked.getAuthorId());
        this.eventPublisher.publish(new FolloweeMessageQuacked(subscriptionId, messageQuacked.getMessageId()));
    }
}
