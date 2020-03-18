package de.ruegnerlukas.simpleapplication.core.events;


import de.ruegnerlukas.simpleapplication.common.events.EventListener;

public interface EventService {


	/**
	 * Subscribe the given listener to the given channels with the given priority.
	 *
	 * @param channel  the name of the channel
	 * @param listener the {@link EventListener}
	 * @param priority the priority of the given listener. Listeners with higher priority will get called first.
	 */
	void subscribe(String channel, int priority, EventListener<? extends Publishable> listener);

	/**
	 * Subscribe the given listener to the given channels with the default priority of '0'.
	 *
	 * @param channel  the name of the channel
	 * @param listener the {@link EventListener}
	 */
	void subscribe(String channel, EventListener<? extends Publishable> listener);

	/**
	 * Subscribe the given listener to all channels. The listener will get called after all listeners subscribed to specific channels.
	 *
	 * @param listener the {@link EventListener}
	 */
	void subscribe(EventListener<? extends Publishable> listener);

	/**
	 * Unsubscribe the given listener from the given channel.
	 *
	 * @param channel  the name of the channel
	 * @param listener the {@link EventListener}
	 */
	void unsubscribe(String channel, EventListener<? extends Publishable> listener);


	/**
	 * Publish the given {@link Publishable}
	 *
	 * @param publishable the object to publish
	 * @return the metadata about the published {@link Publishable}
	 */
	PublishableMeta publish(Publishable publishable);


}
