package de.ruegnerlukas.simpleapplication.common.events;


import de.ruegnerlukas.simpleapplication.common.events.listeners.EventListener;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public final class EventBus {


	/**
	 * The map of subscribed listeners. Key is the name of the channel.
	 */
	private final Map<String, List<EventListener>> subscribers = new HashMap<>();




	/**
	 * Subscribe the given listener to the given channels.
	 *
	 * @param channels the names of the channels
	 * @param listener the {@link EventListener}
	 */
	public void subscribe(final String[] channels, final EventListener listener) {
		for (String channel : channels) {
			subscribe(channel, listener);
		}
	}




	/**
	 * Subscribe the given listener to the given channel.
	 *
	 * @param channel  the name of the channel
	 * @param listener the {@link EventListener}
	 */
	public void subscribe(final String channel, final EventListener listener) {
		Validations.INPUT.notBlank(channel, "The channel must not be null or empty.");
		Validations.INPUT.notNull(listener, "The listener must not be null.");
		final List<EventListener> listeners = subscribers.computeIfAbsent(channel, key -> new ArrayList<>());
		listeners.add(listener);
	}




	/**
	 * Unsubscribe the given listener from the given channels.
	 *
	 * @param channels the names of the channels
	 * @param listener the {@link EventListener}
	 */
	public void unsubscribe(final String[] channels, final EventListener listener) {
		for (String channel : channels) {
			unsubscribe(channel, listener);
		}
	}




	/**
	 * Unsubscribe the given listener from the given channel.
	 *
	 * @param channel  the name of the channel
	 * @param listener the {@link EventListener}
	 */
	public void unsubscribe(final String channel, final EventListener listener) {
		final List<EventListener> listeners = subscribers.get(channel);
		if (listener != null) {
			listeners.remove(listener);
		}
	}




	/**
	 * Publish a new event in the given channels.
	 *
	 * @param channels the names of the channels
	 * @param event    the {@link Event}
	 * @return the number of listeners that received the given event
	 */
	public void publish(final String[] channels, final Event event) {
		for (String channel : channels) {
			publish(channel, event);
		}
	}




	/**
	 * Publish a new event in the given channel.
	 *
	 * @param channel the name of the channel
	 * @param event   the {@link Event}
	 * @return the number of listeners that received the given event
	 */
	public int publish(final String channel, final Event event) {
		Validations.INPUT.notBlank(channel, "The channel must not be null or empty.");
		Validations.INPUT.notNull(event, "The event must not be null.");
		event.setChannels(new String[]{channel});
		event.setTimestamp(System.currentTimeMillis());
		event.setReceivers(0);
		final List<EventListener> listeners = subscribers.get(channel);
		int count = 0;
		log.debug("Publish event to {} listeners: {} in [{}].",
				(listeners != null ? listeners.size() : 0), event.getClass(), String.join(",", event.getChannels()));
		if (listeners != null) {
			count = listeners.size();
			event.setReceivers(count);
			listeners.forEach(listener -> listener.onEvent(event));
		}
		return count;
	}




	/**
	 * @param channel the name of the channel
	 * @return the number of subscribers of the given channel
	 */
	public int getSubscriberCount(final String channel) {
		final List<EventListener> listeners = subscribers.get(channel);
		int count = 0;
		if (listeners != null) {
			count = listeners.size();
		}
		return count;
	}


}
