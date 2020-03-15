package de.ruegnerlukas.simpleapplication.core.events;

import de.ruegnerlukas.simpleapplication.common.events.EventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EventServiceImpl implements EventService {


	/**
	 * The map of subscribed listeners. Key is the name of the channel.
	 */
	private final Map<String, List<EventListener<Publishable>>> subscribers = new HashMap<>();


	/**
	 * The listeners subscribes to all channels
	 */
	private final Set<EventListener<Publishable>> anySubscribers = new HashSet<>();




	@Override
	public void subscribe(final String channel, final EventListener<Publishable> listener) {
		subscribers.computeIfAbsent(channel, c -> new ArrayList<>()).add(listener);
	}




	@Override
	public void subscribe(final EventListener<Publishable> listener) {
		anySubscribers.add(listener);
	}




	@Override
	public void unsubscribe(final String channel, final EventListener<Publishable> listener) {
		subscribers.computeIfAbsent(channel, c -> new ArrayList<>()).remove(listener);
	}




	@Override
	public void publish(final Publishable publishable) {
		subscribers.getOrDefault(publishable.getChannel(), Collections.emptyList()).forEach(listener -> {
			listener.onEvent(publishable);
		});
		anySubscribers.forEach(listener -> listener.onEvent(publishable));
	}

}
