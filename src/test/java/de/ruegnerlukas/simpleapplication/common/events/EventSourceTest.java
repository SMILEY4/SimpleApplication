package de.ruegnerlukas.simpleapplication.common.events;

import de.ruegnerlukas.simpleapplication.common.events.listeners.EventListener;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

public class EventSourceTest {


	@Test
	public void testSimple() {
		final Event EVENT = new Event();
		final EventSource<Event> SOURCE = new GenericEventSource<>();
		final EventListener listener = Mockito.mock(EventListener.class);
		SOURCE.subscribe(listener);
		assertThat(SOURCE.getSubscriberCount()).isEqualTo(1);
		SOURCE.trigger(EVENT);
		verify(listener).onEvent(eq(EVENT));
	}




	@Test
	public void testUnsubscribe() {
		final Event EVENT = new Event();
		final EventSource<Event> SOURCE = new GenericEventSource<>();
		final EventListener listener = Mockito.mock(EventListener.class);
		SOURCE.subscribe(listener);
		assertThat(SOURCE.getSubscriberCount()).isEqualTo(1);
		SOURCE.unsubscribe(listener);
		assertThat(SOURCE.getSubscriberCount()).isEqualTo(0);
		SOURCE.trigger(EVENT);
		verifyNoInteractions(listener);
	}




	@Test
	public void testMultipleListeners() {
		final Event EVENT = new Event();
		final EventSource<Event> SOURCE = new GenericEventSource<>();
		final EventListener listenerA = Mockito.mock(EventListener.class);
		final EventListener listenerB = Mockito.mock(EventListener.class);
		SOURCE.subscribe(listenerA);
		SOURCE.subscribe(listenerB);
		assertThat(SOURCE.getSubscriberCount()).isEqualTo(2);
		SOURCE.trigger(EVENT);
		verify(listenerA).onEvent(eq(EVENT));
		verify(listenerB).onEvent(eq(EVENT));
	}




	@Test
	public void testFixedEventSource() {
		final Event EVENT = new Event();
		final FixedEventSource SOURCE = new FixedEventSource(EVENT);
		final EventListener listener = Mockito.mock(EventListener.class);
		SOURCE.subscribe(listener);
		assertThat(SOURCE.getSubscriberCount()).isEqualTo(1);
		SOURCE.trigger();
		verify(listener).onEvent(eq(EVENT));
	}


}
