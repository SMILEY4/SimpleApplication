package de.ruegnerlukas.simpleapplication.common.events2;

import de.ruegnerlukas.simpleapplication.common.events2.specializedevents.EventBusListener;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class EventBusImpl implements EventBus {


	/**
	 * The map of subscribed listeners. Key is the name of the channel.
	 */
	private final Map<String, List<EventBusListener>> subscribers = new HashMap<>();




	@Override
	public void subscribe(final String[] channels, final EventBusListener<?> listener) {
		Validations.INPUT.notNull(channels, "The channels must not be null.");
		Validations.INPUT.notNull(listener, "The listener must not be null.");
		for (String channel : channels) {
			subscribe(channel, listener);
		}
	}




	@Override
	public void subscribe(final String channel, final EventBusListener<?> listener) {
		Validations.INPUT.notBlank(channel, "The channel must not be null or empty.");
		Validations.INPUT.notNull(listener, "The listener must not be null.");
		final List<EventBusListener> list = subscribers.computeIfAbsent(channel, k -> new ArrayList<>());
		list.add(listener);
	}




	@Override
	public void unsubscribe(final String[] channels, final EventBusListener<?> listener) {
		Validations.INPUT.notNull(channels, "The channels must not be null.");
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
	public void publish(final String[] channels, final EventPackage<?> event) {
		Validations.INPUT.notNull(channels, "The channels must not be null.");
		Validations.INPUT.notNull(event, "The event must not be null.");
		for (String channel : channels) {
			publish(channel, event);
		}
	}




	@Override
	public int publish(final String channel, final EventPackage<?> event) {
		Validations.INPUT.notBlank(channel, "The channel must not be null or empty.");
		Validations.INPUT.notNull(event, "The event must not be null.");
		int count = subscribers.getOrDefault(channel, Collections.emptyList()).size();
		event.setChannels(new String[]{channel});
		event.setTimestamp(System.currentTimeMillis());
		event.setReceivers(count);
		log.debug("Publish event to {} listeners: {} in [{}].",
				count, event.getEvent(), String.join(",", event.getChannels()));
		if (count > 0) {
			final List<EventBusListener> list = subscribers.get(channel);
			list.forEach(listener -> listener.onEvent(event));
		}
		return count;
	}




	@Override
	public int getSubscriberCount(final String channel) {
		return subscribers.getOrDefault(channel, Collections.emptyList()).size();
	}

}
