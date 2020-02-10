package de.ruegnerlukas.simpleapplication.common.events;

import de.ruegnerlukas.simpleapplication.common.events.specializedevents.EmptyEventPackage;
import de.ruegnerlukas.simpleapplication.common.events.specializedevents.EventBusListener;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EventBusImpl implements EventBus {


	/**
	 * The map of subscribed listeners. Key is the name of the channel.
	 */
	private final Map<String, List<EventBusListener>> subscribers = new HashMap<>();

	/**
	 * The set of listeners that subscribed to all channels.
	 */
	private final Set<EventBusListener> anySubscribers = new HashSet<>();




	@Override
	public void subscribe(final String[] channels, final EventBusListener<?> listener) {
		Validations.INPUT.notNull(channels).exception("The channels must not be null.");
		Validations.INPUT.notNull(listener).exception("The listener must not be null.");
		for (String channel : channels) {
			subscribe(channel, listener);
		}
	}




	@Override
	public void subscribe(final String channel, final EventBusListener<?> listener) {
		Validations.INPUT.notBlank(channel).exception("The channel must not be null or empty.");
		Validations.INPUT.notNull(listener).exception("The listener must not be null.");
		final List<EventBusListener> list = subscribers.computeIfAbsent(channel, k -> new ArrayList<>());
		list.add(listener);
	}




	@Override
	public void subscribe(final EventBusListener<?> listener) {
		Validations.INPUT.notNull(listener).exception("The listener must not be null.");
		unsubscribe(listener);
		anySubscribers.add(listener);
	}




	@Override
	public void unsubscribe(final String[] channels, final EventBusListener<?> listener) {
		Validations.INPUT.notNull(channels).exception("The channels must not be null.");
		for (String channel : channels) {
			unsubscribe(channel, listener);
		}
	}




	@Override
	public void unsubscribe(final String channel, final EventBusListener<?> listener) {
		if (subscribers.containsKey(channel)) {
			subscribers.get(channel).remove(listener);
		}
	}




	@Override
	public void unsubscribe(final EventBusListener<?> listener) {
		Validations.INPUT.notNull(listener).exception("The listener must not be null.");
		final String[] channels = subscribers.keySet().toArray(new String[]{});
		unsubscribe(channels, listener);
		anySubscribers.remove(listener);
	}




	@Override
	public void publish(final String[] channels) {
		publish(channels, new EmptyEventPackage());
	}




	@Override
	public int publish(final String channel) {
		return publish(channel, new EmptyEventPackage());
	}




	@Override
	public void publish(final String[] channels, final EventPackage<?> event) {
		Validations.INPUT.notNull(channels).exception("The channels must not be null.");
		Validations.INPUT.notNull(event).exception("The event must not be null.");
		for (String channel : channels) {
			publish(channel, event);
		}
	}




	@Override
	public int publish(final String channel, final EventPackage<?> event) {
		Validations.INPUT.notBlank(channel).exception("The channel must not be null or empty.");
		Validations.INPUT.notNull(event).exception("The event must not be null.");
		int count = getSubscriberCount(channel);
		event.setChannels(new String[]{channel});
		event.setTimestamp(System.currentTimeMillis());
		event.setReceivers(count);
		if (count > 0) {
			final List<EventBusListener> list = new ArrayList<>(count);
			list.addAll(subscribers.getOrDefault(channel, List.of()));
			list.addAll(anySubscribers);
			list.forEach(listener -> listener.onEvent(event));
		}
		return count;
	}




	@Override
	public int getSubscriberCount(final String channel) {
		return subscribers.getOrDefault(channel, Collections.emptyList()).size() + anySubscribers.size();
	}


}
