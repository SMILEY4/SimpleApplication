package de.ruegnerlukas.simpleapplication.core.events;

import de.ruegnerlukas.simpleapplication.common.events.EventListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class EventServiceImpl implements EventService {


	/**
	 * The map of subscribed listeners. Key is the name of the channel. The subscribers are ordered by priority desc.
	 */
	private final Map<String, List<Subscriber>> subscribers = new HashMap<>();


	/**
	 * The listeners subscribes to all channels.
	 */
	private final Set<Subscriber> anySubscribers = new HashSet<>();




	@Override
	public void subscribe(final String channel, final int priority, final EventListener<Publishable> listener) {
		subscribers.computeIfAbsent(channel, c -> new ArrayList<>()).add(new Subscriber(listener, priority));
		Collections.sort(subscribers.get(channel));
	}




	@Override
	public void subscribe(final String channel, final EventListener<Publishable> listener) {
		subscribers.computeIfAbsent(channel, c -> new ArrayList<>()).add(new Subscriber(listener, Subscriber.DEFAULT_PRIORITY));
		Collections.sort(subscribers.get(channel));
	}




	@Override
	public void subscribe(final EventListener<Publishable> listener) {
		anySubscribers.add(new Subscriber(listener, Integer.MIN_VALUE));
	}




	@Override
	public void unsubscribe(final String channel, final EventListener<Publishable> listener) {
		final List<Subscriber> subscriberList = subscribers.computeIfAbsent(channel, c -> new ArrayList<>());
		subscriberList.removeAll(subscriberList.stream()
				.filter(s -> s.getListener().equals(listener))
				.collect(Collectors.toList()));
	}




	@Override
	public PublishableMeta publish(final Publishable publishable) {
		final List<Subscriber> subscriberList = new ArrayList<>();
		subscriberList.addAll(subscribers.getOrDefault(publishable.getChannel(), Collections.emptyList()));
		subscriberList.addAll(anySubscribers);

		publishable.getMetadata().setTimestamp(Instant.now().toEpochMilli());
		publishable.getMetadata().setPublished(true);
		publishable.getMetadata().setNumListeners(subscriberList.size());

		for (Subscriber subscriber : subscriberList) {
			subscriber.getListener().onEvent(publishable);
			publishable.getMetadata().setNumReceivers(publishable.getMetadata().getNumReceivers() + 1);
			if (publishable.isCancelled()) {
				break;
			}
		}

		return publishable.getMetadata();
	}




	@Getter
	@Setter
	@AllArgsConstructor
	private static class Subscriber implements Comparable<Subscriber> {


		/**
		 * The default priority of a subscriber.
		 */
		public static final int DEFAULT_PRIORITY = 0;

		/**
		 * The listener.
		 */
		private EventListener<Publishable> listener;

		/**
		 * The priority of this subscriber.
		 */
		private int priority;




		@Override
		public int compareTo(final Subscriber other) {
			return -Integer.compare(this.getPriority(), other.getPriority());
		}

	}

}
