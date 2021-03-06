package de.ruegnerlukas.simpleapplication.common.eventbus;

import de.ruegnerlukas.simpleapplication.common.events.specializedevents.EmptyEvent;
import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import de.ruegnerlukas.simpleapplication.common.utils.HashCode;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.application.ApplicationConstants;
import de.ruegnerlukas.simpleapplication.core.plugins.EventComponentUnloaded;
import de.ruegnerlukas.simpleapplication.core.plugins.EventPluginUnloaded;
import javafx.application.Platform;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class EventBusImpl implements EventBus {


	/**
	 * All subscriptions to this event bus
	 */
	private final Set<Subscription> subscriptions = new HashSet<>();

	/**
	 * The executor service for async events
	 */
	private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());




	@Override
	public <T> void subscribe(final SubscriptionData<T> subscriptionData, final Consumer<T> subscriber) {
		Validations.INPUT.notNull(subscriber).exception("The subscriber can not be null.");
		Validations.INPUT.notNull(subscriptionData).exception("The subscriber meta can not be null.");
		subscriptions.add(new Subscription(subscriber, subscriptionData));
	}




	@Override
	public void unsubscribe(final Consumer<?> subscriber) {
		Validations.INPUT.notNull(subscriber).exception("The subscriber to unsubscribe can not be null.");
		subscriptions.removeIf(wrapper -> wrapper.getConsumer().equals(subscriber));
	}




	@Override
	public void publish(final Object event) {
		publish(Tags.empty(), event);
	}




	@Override
	public void publish(final Tags tags, final Object event) {
		publish(tags, event, false);
	}




	@Override
	public void publish(final Tags tags, final Object event, final boolean wait) {
		Validations.INPUT.notNull(tags).exception("The tags can not be null.");
		Validations.INPUT.notNull(event).exception("The event can not be null.");

		final List<Subscription> applicableSubscriptions = findApplicable(event.getClass(), tags);
		final Map<ThreadMode, List<Subscription>> groupedSubscriptions = applicableSubscriptions.stream()
				.collect(Collectors.groupingBy(s -> s.getMeta().getThreadMode()));

		// order of execution is important here
		// -> e.g.: if POSTING first => wait for sync POSTING to complete, then wait again for ASYNC -> unnecessary wait

		final Phaser phaser = new Phaser(applicableSubscriptions.size() + 1);
		groupedSubscriptions.getOrDefault(ThreadMode.ASYNC, List.of()).forEach(subscription -> {
			@SuppressWarnings ("rawtypes") final Consumer consumer = subscription.getConsumer();
			executorService.submit(() -> {
				//noinspection unchecked
				consumer.accept(event);
				phaser.arrive();
			});
		});
		groupedSubscriptions.getOrDefault(ThreadMode.JFX, List.of()).forEach(subscription -> {
			@SuppressWarnings ("rawtypes") final Consumer consumer = subscription.getConsumer();
			Platform.runLater(() -> {
				//noinspection unchecked
				consumer.accept(event);
				phaser.arriveAndDeregister();
			});
		});
		groupedSubscriptions.getOrDefault(ThreadMode.POSTING, List.of()).forEach(subscription -> {
			@SuppressWarnings ("rawtypes") final Consumer consumer = subscription.getConsumer();
			//noinspection unchecked
			consumer.accept(event);
			phaser.arriveAndDeregister();
		});

		if (wait) {
			phaser.arriveAndAwaitAdvance();
		}

		checkAutoUnsubscribe(tags, event);
	}




	/**
	 * Checks if subscribers have to be removed when a plugin/components gets unloaded.
	 *
	 * @param tags  the tags of the event
	 * @param event the event
	 */
	private void checkAutoUnsubscribe(final Tags tags, final Object event) {
		if (Tags.contains(ApplicationConstants.EVENT_PLUGIN_UNLOADED_TYPE).matches(tags)) {
			final EventPluginUnloaded unloadedEvent = (EventPluginUnloaded) event;
			subscriptions.removeIf(subscription -> Objects.equals(unloadedEvent.getPluginId(), subscription.getMeta().getPluginId()));
		}
		if (Tags.contains(ApplicationConstants.EVENT_COMPONENT_UNLOADED_TYPE).matches(tags)) {
			final EventComponentUnloaded unloadedEvent = (EventComponentUnloaded) event;
			subscriptions.removeIf(subscription -> Objects.equals(unloadedEvent.getComponentId(), subscription.getMeta().getPluginId()));
		}
	}




	@Override
	public void publishEmpty(final Tags tags) {
		Validations.INPUT.notNull(tags).exception("The tags can not be null.");
		publish(tags, EmptyEvent.INSTANCE);
	}




	/**
	 * Get a list of subscribers that can receive the event with the given data
	 *
	 * @param type the type of the event
	 * @param tags the tags of the event
	 */
	private List<Subscription> findApplicable(final Class<?> type, final Tags tags) {
		return subscriptions.stream()
				.filter(wrapper -> wrapper.meta.getType().isAssignableFrom(type))
				.filter(wrapper -> wrapper.meta.getFilter().matches(tags))
				.collect(Collectors.toList());
	}




	@Getter
	@AllArgsConstructor
	private static class Subscription {


		/**
		 * The consumer to receive the events.
		 */
		private final Consumer<?> consumer;

		/**
		 * Information about the subscription.
		 */
		private final SubscriptionData<?> meta;




		@Override
		public boolean equals(final Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			final Subscription that = (Subscription) o;
			if (!getConsumer().equals(that.getConsumer())) {
				return false;
			}
			return getMeta().equals(that.getMeta());
		}




		@Override
		public int hashCode() {
			int result = getConsumer().hashCode();
			result = HashCode.CONSTANT * result + getMeta().hashCode();
			return result;
		}

	}


}
