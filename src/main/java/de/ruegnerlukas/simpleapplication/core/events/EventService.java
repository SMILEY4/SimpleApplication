package de.ruegnerlukas.simpleapplication.core.events;


import de.ruegnerlukas.simpleapplication.common.events.EventListener;

public interface EventService {


	/**
	 * Subscribe the given listener to the given channels.
	 *
	 * @param channel  the name of the channel
	 * @param listener the {@link EventListener}
	 */
	void subscribe(String channel, EventListener<Publishable> listener);

	/**
	 * Subscribe the given listener to all channels.
	 *
	 * @param listener the {@link EventListener}
	 */
	void subscribe(EventListener<Publishable> listener);

	/**
	 * Unsubscribe the given listener from the given channel.
	 *
	 * @param channel  the name of the channel
	 * @param listener the {@link EventListener}
	 */
	void unsubscribe(String channel, EventListener<Publishable> listener);


	/**
	 * Publish the given {@link Publishable}
	 *
	 * @param publishable the object to publish
	 */
	void publish(Publishable publishable);


}
