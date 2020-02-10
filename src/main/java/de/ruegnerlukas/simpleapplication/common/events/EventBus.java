package de.ruegnerlukas.simpleapplication.common.events;


import de.ruegnerlukas.simpleapplication.common.events.specializedevents.EventBusListener;

public interface EventBus {


	/**
	 * Subscribe the given listener to the given channels.
	 *
	 * @param channels the names of the channels
	 * @param listener the {@link EventBusListener}
	 */
	void subscribe(String[] channels, EventBusListener<?> listener);


	/**
	 * Subscribe the given listener to the given channel.
	 *
	 * @param channel  the name of the channel
	 * @param listener the {@link EventBusListener}
	 */
	void subscribe(String channel, EventBusListener<?> listener);


	/**
	 * Subscribe the given listener to all channels.
	 *
	 * @param listener the {@link EventBusListener}
	 */
	void subscribe(EventBusListener<?> listener);


	/**
	 * Unsubscribe the given listener from the given channels.
	 *
	 * @param channels the names of the channels
	 * @param listener the {@link EventBusListener}
	 */
	void unsubscribe(String[] channels, EventBusListener<?> listener);


	/**
	 * Unsubscribe the given listener from the given channel.
	 *
	 * @param channel  the name of the channel
	 * @param listener the {@link EventBusListener}
	 */
	void unsubscribe(String channel, EventBusListener<?> listener);


	/**
	 * Unsubscribe the given listener from all channels.
	 *
	 * @param listener the {@link EventBusListener}
	 */
	void unsubscribe(EventBusListener<?> listener);


	/**
	 * Publish a new {@link de.ruegnerlukas.simpleapplication.common.events.specializedevents.EmptyEvent} in the given channels.
	 *
	 * @param channels the names of the channels
	 */
	void publish(String[] channels);


	/**
	 * Publish a new {@link de.ruegnerlukas.simpleapplication.common.events.specializedevents.EmptyEvent} in the given channel.
	 *
	 * @param channel the name of the channel
	 * @return the number of listeners that received the given empty event
	 */
	int publish(String channel);


	/**
	 * Publish a new event in the given channels.
	 *
	 * @param channels the names of the channels
	 * @param event    the {@link EventPackage}
	 */
	void publish(String[] channels, EventPackage<?> event);


	/**
	 * Publish a new event in the given channel.
	 *
	 * @param channel the name of the channel
	 * @param event   the {@link EventPackage}
	 * @return the number of listeners that received the given event
	 */
	int publish(String channel, EventPackage<?> event);


	/**
	 * @param channel the name of the channel
	 * @return the number of subscribers of the given channel
	 */
	int getSubscriberCount(String channel);

}
