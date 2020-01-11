package de.ruegnerlukas.simpleapplication.common.events;

import de.ruegnerlukas.simpleapplication.common.events.listeners.EventListener;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

public class EventBusTest {


	@Test
	public void testSimpleListener() {
		final EventBus EVENTS = new EventBusImpl();
		final String CHANNEL = "test";
		final Event EVENT = new Event();
		final EventListener listener = Mockito.mock(EventListener.class);
		EVENTS.subscribe(CHANNEL, listener);
		assertThat(EVENTS.getSubscriberCount(CHANNEL)).isEqualTo(1);
		final int subscriberCount = EVENTS.publish(CHANNEL, EVENT);
		assertThat(subscriberCount).isEqualTo(1);
		verify(listener).onEvent(eq(EVENT));
		assertThat(EVENT.getChannels()).containsExactly(CHANNEL);
	}




	@Test
	public void testMultipleListeners() {
		final EventBus EVENTS = new EventBusImpl();
		final String CHANNEL = "test";
		final Event EVENT = new Event();
		final EventListener listenerA = Mockito.mock(EventListener.class);
		final EventListener listenerB = Mockito.mock(EventListener.class);
		EVENTS.subscribe(CHANNEL, listenerA);
		EVENTS.subscribe(CHANNEL, listenerB);
		assertThat(EVENTS.getSubscriberCount(CHANNEL)).isEqualTo(2);
		final int subscriberCount = EVENTS.publish(CHANNEL, EVENT);
		assertThat(subscriberCount).isEqualTo(2);
		verify(listenerA).onEvent(eq(EVENT));
		verify(listenerB).onEvent(eq(EVENT));
		assertThat(EVENT.getChannels()).containsExactly(CHANNEL);
	}




	@Test
	public void testMultipleChannels() {
		final EventBus EVENTS = new EventBusImpl();
		final Event EVENT_A = new Event();
		final Event EVENT_B = new Event();
		final String CHANNEL_A = "test_a";
		final String CHANNEL_B = "test_b";
		final EventListener listenerA = Mockito.mock(EventListener.class);
		final EventListener listenerB = Mockito.mock(EventListener.class);
		EVENTS.subscribe(CHANNEL_A, listenerA);
		EVENTS.subscribe(CHANNEL_B, listenerB);
		assertThat(EVENTS.getSubscriberCount(CHANNEL_A)).isEqualTo(1);
		assertThat(EVENTS.getSubscriberCount(CHANNEL_B)).isEqualTo(1);
		final int subscriberCountA = EVENTS.publish(CHANNEL_A, EVENT_A);
		final int subscriberCountB = EVENTS.publish(CHANNEL_B, EVENT_B);
		assertThat(subscriberCountA).isEqualTo(1);
		assertThat(subscriberCountB).isEqualTo(1);
		verify(listenerA).onEvent(eq(EVENT_A));
		verify(listenerB).onEvent(eq(EVENT_B));
		assertThat(EVENT_A.getChannels()).containsExactly(CHANNEL_A);
		assertThat(EVENT_B.getChannels()).containsExactly(CHANNEL_B);
	}




	@Test
	public void testUnsubscribe() {
		final EventBus EVENTS = new EventBusImpl();
		final String CHANNEL = "test";
		final Event EVENT = new Event();
		final EventListener listener = Mockito.mock(EventListener.class);
		EVENTS.subscribe(CHANNEL, listener);
		assertThat(EVENTS.getSubscriberCount(CHANNEL)).isEqualTo(1);
		EVENTS.unsubscribe(CHANNEL, listener);
		assertThat(EVENTS.getSubscriberCount(CHANNEL)).isEqualTo(0);
		final int subscriberCount = EVENTS.publish(CHANNEL, EVENT);
		assertThat(subscriberCount).isEqualTo(0);
		verifyNoInteractions(listener);
	}

}
