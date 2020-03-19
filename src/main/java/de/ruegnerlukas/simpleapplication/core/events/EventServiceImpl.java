package de.ruegnerlukas.simpleapplication.core.events;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.common.events.EventListener;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class EventServiceImpl implements EventService {


	/**
	 * The map of subscribed listeners. Key is the name of the channel. The subscribers are ordered by priority desc.
	 */
	private final Map<Channel, List<Subscriber>> subscribers = new HashMap<>();


	/**
	 * The listeners subscribes to all channels.
	 */
	private final Set<Subscriber> anySubscribers = new HashSet<>();




	@Override
	public void subscribe(final Channel channel, final int priority, final EventListener<? extends Publishable> listener) {
		subscribers.computeIfAbsent(channel, c -> new ArrayList<>()).add(new Subscriber(listener, priority));
		Collections.sort(subscribers.get(channel));
	}




	@Override
	public void subscribe(final Channel channel, final EventListener<? extends Publishable> listener) {
		subscribe(channel, Subscriber.DEFAULT_PRIORITY, listener);
	}




	@Override
	public void subscribe(final EventListener<? extends Publishable> listener) {
		anySubscribers.add(new Subscriber(listener, Integer.MIN_VALUE));
	}




	@Override
	public void unsubscribe(final Channel channel, final EventListener<? extends Publishable> listener) {
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
			if (sendToSubscriber(publishable, subscriber)) {
				publishable.getMetadata().setNumReceivers(publishable.getMetadata().getNumReceivers() + 1);
			}
			if (publishable.isCancelled()) {
				break;
			}
		}

		return publishable.getMetadata();
	}




	/**
	 * Send the given {@link Publishable} to the given {@link Subscriber} if possible.
	 *
	 * @param publishable the publishable to send to the given subscriber
	 * @param subscriber  the subscriber to receive the given publishable
	 * @return true, if the operation was successful and the publishable was received.
	 */
	private boolean sendToSubscriber(final Publishable publishable, final Subscriber subscriber) {
		boolean successful = true;
		try {
			subscriber.getGenericListener().onEvent(publishable);
		} catch (ClassCastException e) {
			log.warn("Cannot cast event listener '{}' to type of received event '{}'", subscriber.getListener(), publishable);
			successful = false;
		}
		return successful;
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
		private EventListener<? extends Publishable> listener;

		/**
		 * The priority of this subscriber.
		 */
		private int priority;




		/**
		 * @return a generic version of the event listener
		 */
		public EventListener<Publishable> getGenericListener() {
			return (EventListener<Publishable>) listener;
		}




		@Override
		public int compareTo(final Subscriber other) {
			return -Integer.compare(this.getPriority(), other.getPriority());
		}

	}

}
