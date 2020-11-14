package de.ruegnerlukas.simpleapplication.common.eventbus;

import de.ruegnerlukas.simpleapplication.common.tags.Tags;

import java.util.function.Consumer;

public interface EventBus {

	/**
	 * Subscribes the given consumer to events of this event bus.
	 *
	 * @param subscriptionData information about the subscription
	 * @param subscriber       the consumer of the events
	 * @param <T>              the generic type of the events
	 */
	<T> void subscribe(SubscriptionData<T> subscriptionData, Consumer<T> subscriber);


	/**
	 * Removes the given consumer from this event bus. It will no longer receive events.
	 *
	 * @param subscriber the consumer to remove
	 */
	void unsubscribe(Consumer<?> subscriber);


	/**
	 * Publish the given object on the event bus.
	 *
	 * @param event the object to publish
	 */
	void publish(Object event);


	/**
	 * Publish the given object on the event bus with the given tags.
	 *
	 * @param tags  the tags of the event
	 * @param event the object to publish
	 */
	void publish(Tags tags, Object event);

}
