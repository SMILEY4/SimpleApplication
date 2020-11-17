package de.ruegnerlukas.simpleapplication.common.eventbus;

import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.application.ApplicationConstants;
import de.ruegnerlukas.simpleapplication.core.plugins.EventComponentUnloaded;
import de.ruegnerlukas.simpleapplication.core.plugins.EventPluginUnloaded;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class EventBusTest {


	@Test
	public void subscribers_receive_events() {

		final EventBus eventBus = new EventBusImpl();
		final List<String> collectedEventsA = new ArrayList<>();
		final List<String> collectedEventsB = new ArrayList<>();
		final Consumer<String> consumerA = collectedEventsA::add;
		final Consumer<String> consumerB = collectedEventsB::add;

		eventBus.subscribe(SubscriptionData.ofType(String.class), consumerA);
		eventBus.subscribe(SubscriptionData.ofType(String.class), consumerB);
		eventBus.publish("Event 1");
		eventBus.publish("Event 2");

		assertThat(collectedEventsA).containsExactly("Event 1", "Event 2");
		assertThat(collectedEventsB).containsExactly("Event 1", "Event 2");
	}




	@Test
	public void subscribe_twice_only_receive_one_event() {

		final EventBus eventBus = new EventBusImpl();
		final List<String> collectedEvents = new ArrayList<>();
		final Consumer<String> consumer = collectedEvents::add;

		eventBus.subscribe(SubscriptionData.ofType(String.class), consumer);
		eventBus.subscribe(SubscriptionData.ofType(String.class), consumer);
		eventBus.publish("Test Event");

		assertThat(collectedEvents).hasSize(1);
	}




	@Test
	public void subscribe_to_any_event_type() {

		final EventBus eventBus = new EventBusImpl();
		final List<Object> collectedEvents = new ArrayList<>();
		final Consumer<Object> consumer = collectedEvents::add;

		eventBus.subscribe(SubscriptionData.anyType(), consumer);
		eventBus.publish("Test Event");
		eventBus.publish(42);

		assertThat(collectedEvents).hasSize(2);
		assertThat(collectedEvents).containsExactly("Test Event", 42);
	}




	@Test
	public void subscribe_filter_type() {

		final EventBus eventBus = new EventBusImpl();
		final List<String> collectedEvents = new ArrayList<>();
		final Consumer<String> consumer = collectedEvents::add;

		eventBus.subscribe(SubscriptionData.ofType(String.class), consumer);
		eventBus.publish("Test Event");
		eventBus.publish(42);

		assertThat(collectedEvents).hasSize(1);
		assertThat(collectedEvents).containsExactly("Test Event");
	}




	@Test
	public void subscribe_filter_tags() {

		final EventBus eventBus = new EventBusImpl();
		final List<String> collectedEvents = new ArrayList<>();
		final Consumer<String> consumer = collectedEvents::add;

		eventBus.subscribe(SubscriptionData.ofType(String.class).filter(Tags.contains("x")), consumer);
		eventBus.publish("Event 1");
		eventBus.publish(Tags.from("a", "b"), "Event 2");
		eventBus.publish(Tags.from("x", "b"), "Event 3");
		eventBus.publish(Tags.from("x", "b"), 32);
		eventBus.publish(64);

		assertThat(collectedEvents).hasSize(1);
		assertThat(collectedEvents).containsExactly("Event 3");
	}




	@Test
	public void unsubscribe_and_receive_no_more_events() {

		final EventBus eventBus = new EventBusImpl();
		final List<String> collectedEvents = new ArrayList<>();
		final Consumer<String> consumer = collectedEvents::add;

		eventBus.subscribe(SubscriptionData.ofType(String.class), consumer);
		eventBus.publish("Event 1");
		eventBus.unsubscribe(consumer);
		eventBus.publish("Event 2");

		assertThat(collectedEvents).hasSize(1);
		assertThat(collectedEvents).containsExactly("Event 1");
	}




	@Test
	public void subscribe_to_multiple_different_sources() {

		final EventBus eventBus = new EventBusImpl();
		final List<String> collectedEvents = new ArrayList<>();
		final Consumer<String> consumer = collectedEvents::add;

		eventBus.subscribe(SubscriptionData.ofType(String.class).filter(Tags.contains("x")), consumer);
		eventBus.subscribe(SubscriptionData.ofType(String.class).filter(Tags.contains("y")), consumer);

		eventBus.publish("Event 1");
		eventBus.publish(Tags.from("a", "b"), "Event 1");
		eventBus.publish(Tags.from("x", "b"), "Event 2");
		eventBus.publish(Tags.from("y", "b"), "Event 3");
		eventBus.publish(Tags.from("x", "y"), "Event 4");

		assertThat(collectedEvents).hasSize(4);
		assertThat(collectedEvents).containsExactly("Event 2", "Event 3", "Event 4", "Event 4");
	}




	@Test
	public void unsubscribe_from_multiple_different_sources() {

		final EventBus eventBus = new EventBusImpl();
		final List<String> collectedEvents = new ArrayList<>();
		final Consumer<String> consumer = collectedEvents::add;

		eventBus.subscribe(SubscriptionData.ofType(String.class).filter(Tags.contains("x")), consumer);
		eventBus.subscribe(SubscriptionData.ofType(String.class).filter(Tags.contains("y")), consumer);
		eventBus.unsubscribe(consumer);

		eventBus.publish(Tags.from("x", "b"), "Event 2");
		eventBus.publish(Tags.from("y", "b"), "Event 3");
		eventBus.publish(Tags.from("x", "y"), "Event 4");

		assertThat(collectedEvents).isEmpty();
	}




	@Test
	public void automatic_unsubscribe_on_unload() {

		final EventBus eventBus = new EventBusImpl();
		final List<Object> collectedEvents = new ArrayList<>();
		final Consumer<Object> consumer = collectedEvents::add;

		eventBus.subscribe(SubscriptionData.anyType().filter(Tags.contains("x")).linkedPlugin("test.plugin"), consumer);
		eventBus.subscribe(SubscriptionData.anyType().filter(Tags.contains("y")).linkedPlugin("test.component"), consumer);

		eventBus.publish(ApplicationConstants.EVENT_PLUGIN_UNLOADED_TAGS, new EventPluginUnloaded("test.plugin"));
		eventBus.publish(ApplicationConstants.EVENT_COMPONENT_UNLOADED_TAGS, new EventComponentUnloaded("test.component"));

		eventBus.publish(Tags.from("x", "b"), "Event 1");
		eventBus.publish(Tags.from("y", "b"), "Event 2");
		assertThat(collectedEvents).isEmpty();
	}


	@Test
	public void wait_for_async_subscribers() {

		final EventBus eventBus = new EventBusImpl();
		final AtomicInteger counter = new AtomicInteger(0);

		final int N = 5;
		for(int i=0; i<N; i++) {
			eventBus.subscribe(SubscriptionData.ofType(String.class).threadMode(ThreadMode.ASYNC), e -> {
				try {
					Thread.sleep(100);
				} catch (InterruptedException interruptedException) {
					interruptedException.printStackTrace();
				}
				counter.incrementAndGet();
			});
		}

		eventBus.publish(Tags.empty(), "My Event", true);
		assertThat(counter.get()).isEqualTo(N);

	}

	@Test
	public void dont_wait_for_async_subscribers() {

		final EventBus eventBus = new EventBusImpl();
		final AtomicInteger counter = new AtomicInteger(0);

		final int N = 5;
		for(int i=0; i<N; i++) {
			eventBus.subscribe(SubscriptionData.ofType(String.class).threadMode(ThreadMode.ASYNC), e -> {
				try {
					Thread.sleep(100);
				} catch (InterruptedException interruptedException) {
					interruptedException.printStackTrace();
				}
				counter.incrementAndGet();
			});
		}

		eventBus.publish(Tags.empty(), "My Event", false);
		assertThat(counter.get()).isNotEqualTo(N);
	}


}
