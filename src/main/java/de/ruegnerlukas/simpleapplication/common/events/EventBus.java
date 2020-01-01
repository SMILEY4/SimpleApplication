package de.ruegnerlukas.simpleapplication.common.events;


import de.ruegnerlukas.simpleapplication.common.events.listeners.EventListener;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class EventBus {


	/**
	 * The instance.
	 */
	private static final EventBus INSTANCE = new EventBus();




	/**
	 * @return the instance of {@link EventBus}.
	 */
	public static EventBus get() {
		return INSTANCE;
	}




	/**
	 * The map of subscribed listeners. Key is the name of the channel.
	 */
	private final Map<String, List<EventListener>> subscribers = new HashMap<>();




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
