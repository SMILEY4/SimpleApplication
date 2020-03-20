package de.ruegnerlukas.simpleapplication.core.events;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.common.events.EventListener;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
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
		Validations.INPUT.notNull(listener).exception("The listener must not be null");
		validateChannel(channel);
		subscribers.computeIfAbsent(channel, c -> new ArrayList<>()).add(new Subscriber(listener, priority));
		Collections.sort(subscribers.get(channel));
	}




	@Override
	public void subscribe(final Channel channel, final EventListener<? extends Publishable> listener) {
		Validations.INPUT.notNull(listener).exception("The listener must not be null");
		validateChannel(channel);
		subscribe(channel, Subscriber.DEFAULT_PRIORITY, listener);
	}




	@Override
	public void subscribe(final EventListener<? extends Publishable> listener) {
		Validations.INPUT.notNull(listener).exception("The listener must not be null");
		anySubscribers.add(new Subscriber(listener, Integer.MIN_VALUE));
	}




	@Override
	public void unsubscribe(final Channel channel, final EventListener<? extends Publishable> listener) {
		Validations.INPUT.notNull(listener).exception("The listener must not be null");
		validateChannel(channel);
		final List<Subscriber> subscriberList = subscribers.computeIfAbsent(channel, c -> new ArrayList<>());
		subscriberList.removeAll(subscriberList.stream()
				.filter(s -> s.getListener().equals(listener))
				.collect(Collectors.toList()));
	}




	@Override
	public void unsubscribe(final EventListener<? extends Publishable> listener) {
		Validations.INPUT.notNull(listener).exception("The listener must not be null");
		subscribers.forEach((channel, subscribers) -> {
			List<Subscriber> toRemove = new ArrayList<>();
			subscribers.forEach(subscriber -> {
				if (subscriber.getListener() == listener) {
					toRemove.add(subscriber);
				}
			});
			subscribers.removeAll(toRemove);
		});
		List<Subscriber> anyToRemove = new ArrayList<>();
		anySubscribers.forEach(subscriber -> {
			if (subscriber.getListener() == listener) {
				anyToRemove.add(subscriber);
			}
		});
		anySubscribers.removeAll(anyToRemove);
	}




	@Override
	public void register(final Object object) {
		Validations.INPUT.notNull(object).exception("The object to register must not be null.");
		Arrays.stream(object.getClass().getDeclaredMethods())
				.filter(method -> !Modifier.isStatic(method.getModifiers()))
				.filter(method -> method.isAnnotationPresent(Listener.class))
				.filter(method -> method.getParameterCount() == 1)
				.forEach(method -> registerAnnotatedMethod(method, object));
	}




	@Override
	public void register(final Class<?> c) {
		Validations.INPUT.notNull(c).exception("The class to register must not be null.");
		Arrays.stream(c.getDeclaredMethods())
				.filter(method -> Modifier.isStatic(method.getModifiers()))
				.filter(method -> method.isAnnotationPresent(Listener.class))
				.filter(method -> method.getParameterCount() == 1)
				.forEach(this::registerAnnotatedStaticMethod);
	}




	/**
	 * Register a listener for the given method (annotated with {@link Listener}) of the given object.
	 *
	 * @param method the method to handle {@link Publishable}s
	 * @param object the instance
	 */
	private void registerAnnotatedMethod(final Method method, final Object object) {
		final Listener annotation = method.getAnnotation(Listener.class);
		Listener.Utils.toChannel(annotation).ifPresent(channel ->
				subscribe(channel, annotation.priority(), publishable -> {
					try {
						method.invoke(object, publishable);
					} catch (IllegalAccessException | InvocationTargetException e) {
						log.warn("Could not invoke listener-method: {} of {}: {}.",
								method.getName(), object.toString(), e);
					}
				}));
	}




	/**
	 * Register a listener for the given static method (annotated with {@link Listener}).
	 *
	 * @param method the static method to handle {@link Publishable}s
	 */
	private void registerAnnotatedStaticMethod(final Method method) {
		final Listener annotation = method.getAnnotation(Listener.class);
		Listener.Utils.toChannel(annotation).ifPresent(channel ->
				subscribe(channel, annotation.priority(), publishable -> {
					try {
						method.invoke(null, publishable);
					} catch (IllegalAccessException | InvocationTargetException e) {
						log.warn("Could not invoke static listener-method: {}: {}.", method.getName(), e);
					}
				}));
	}




	@Override
	public PublishableMeta publish(final Publishable publishable) {
		Validations.INPUT.notNull(publishable).exception("The publishable must not be null");
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




	@Override
	public PublishableMeta publishEmpty(final Channel channel) {
		validateChannel(channel);
		return publish(new EmptyPublishable(channel));
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




	/**
	 * Validates the given channel
	 *
	 * @param channel the {@link Channel} to validate.
	 */
	private void validateChannel(final Channel channel) {
		Validations.INPUT.notNull(channel).exception("The channel must not be null");
		if (channel.getChannelType() == Channel.ChannelType.NAME) {
			Validations.INPUT.notBlank(channel.getName()).exception("The name of the channel must not be null or empty.");
		} else {
			Validations.INPUT.notNull(channel.getType()).exception("The type of the channel must not be null.");
		}
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
