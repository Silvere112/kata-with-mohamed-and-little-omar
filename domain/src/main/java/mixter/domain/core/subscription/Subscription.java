package mixter.domain.core.subscription;

import mixter.doc.Aggregate;
import mixter.doc.Projection;
import mixter.domain.DecisionProjectionBase;
import mixter.domain.Event;
import mixter.domain.EventPublisher;
import mixter.domain.core.message.MessageId;
import mixter.domain.core.subscription.events.FolloweeMessageQuacked;
import mixter.domain.core.subscription.events.UserFollowed;
import mixter.domain.core.subscription.events.UserUnfollowed;
import mixter.domain.identity.UserId;

import java.util.List;

@Aggregate
public class Subscription {

    private final SubscriptionProjection projection;

    public Subscription(List<Event> history) {
        this.projection = new SubscriptionProjection(history);
    }

    public static void follow(UserId follower, UserId followee, EventPublisher eventPublisher) {
        eventPublisher.publish(new UserFollowed(new SubscriptionId(follower, followee)));
    }

    public void unfollow(EventPublisher eventPublisher) {
        eventPublisher.publish(new UserUnfollowed(projection.id));
    }

    public void notifyFollower(MessageId messageId, EventPublisher eventPublisher) {
        if (!projection.canceled) {
            eventPublisher.publish(new FolloweeMessageQuacked(projection.id, messageId));
        }
    }
    @Projection
    private class SubscriptionProjection extends DecisionProjectionBase {
        private SubscriptionId id;
        private boolean canceled;
        public SubscriptionProjection(List<Event> history) {
            super.register(UserFollowed.class, this::apply);
            super.register(UserUnfollowed.class, this::apply);
            history.forEach(this::apply);
        }
        private void apply(UserFollowed event) {
            this.id = event.getSubscriptionId();
        }
        private void apply(UserUnfollowed event) {
            this.canceled = true;
        }
    }
}
