package de.ruegnerlukas.simpleapplication.common.events;

import de.ruegnerlukas.simpleapplication.common.events.listeners.EventListener;

public interface EventBus {


	/**
	 * Subscribe the given listener to the given channels.
	 *
	 * @param channels the names of the channels
	 * @param listener the {@link EventListener}
	 */
	void subscribe(String[] channels, EventListener listener);

	/**
	 * Subscribe the given listener to the given channel.
	 *
	 * @param channel  the name of the channel
	 * @param listener the {@link EventListener}
	 */
	void subscribe(String channel, EventListener listener);

	/**
	 * Unsubscribe the given listener from the given channels.
	 *
	 * @param channels the names of the channels
	 * @param listener the {@link EventListener}
	 */
	void unsubscribe(String[] channels, EventListener listener);

	/**
	 * Unsubscribe the given listener from the given channel.
	 *
	 * @param channel  the name of the channel
	 * @param listener the {@link EventListener}
	 */
	void unsubscribe(String channel, EventListener listener);

	/**
	 * Publish a new event in the given channels.
	 *
	 * @param channels the names of the channels
	 * @param event    the {@link Event}
	 * @return the number of listeners that received the given event
	 */
	void publish(String[] channels, Event event);

	/**
	 * Publish a new event in the given channel.
	 *
	 * @param channel the name of the channel
	 * @param event   the {@link Event}
	 * @return the number of listeners that received the given event
	 */
	int publish(String channel, Event event);

	/**
	 * @param channel the name of the channel
	 * @return the number of subscribers of the given channel
	 */
	int getSubscriberCount(String channel);

}
