package de.ruegnerlukas.simpleapplication.core.events;


import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import static de.ruegnerlukas.simpleapplication.common.events.specializedevents.PublishableEvent.PublishableEventListener;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class EventServiceTest {


	@Test
	public void testSimple() {

		final String CHANNEL = "test.channel";
		final EventService eventService = new EventServiceImpl();

		final PublishableEventListener listener = Mockito.mock(PublishableEventListener.class);
		eventService.subscribe(CHANNEL, listener);

		eventService.publish(publishable(CHANNEL));
		eventService.unsubscribe(CHANNEL, listener);
		eventService.publish(publishable(CHANNEL));

		verify(listener, times(1)).onEvent(any());
	}




	@Test
	public void testMetadataSimple() {

		final String CHANNEL = "test.channel";
		final EventService eventService = new EventServiceImpl();

		eventService.subscribe(CHANNEL, Mockito.mock(PublishableEventListener.class));
		eventService.subscribe(CHANNEL, Mockito.mock(PublishableEventListener.class));

		final Publishable publishable = publishable(CHANNEL);

		final long timeBefore = System.currentTimeMillis();
		final PublishableMeta meta = eventService.publish(publishable);
		final long timeAfter = System.currentTimeMillis();

		assertThat(meta.getPublishable()).isEqualTo(publishable);
		assertThat(meta.isPublished()).isTrue();
		assertThat(meta.getChannel()).isEqualTo(CHANNEL);
		assertThat(timeBefore <= meta.getTimestamp() && meta.getTimestamp() <= timeAfter).isTrue();
		assertThat(meta.getNumListeners()).isEqualTo(2);
		assertThat(meta.getNumReceivers()).isEqualTo(2);
		assertThat(meta.isCancelled()).isFalse();
	}




	@Test
	public void testMetadataCancelled() {

		final String CHANNEL = "test.channel";
		final EventService eventService = new EventServiceImpl();

		eventService.subscribe(CHANNEL, Mockito.mock(PublishableEventListener.class));
		eventService.subscribe(CHANNEL, Publishable::cancel);
		eventService.subscribe(CHANNEL, Mockito.mock(PublishableEventListener.class));

		final PublishableMeta meta = eventService.publish(publishable(CHANNEL));

		assertThat(meta.isPublished()).isTrue();
		assertThat(meta.getChannel()).isEqualTo(CHANNEL);
		assertThat(meta.getNumListeners()).isEqualTo(3);
		assertThat(meta.getNumReceivers()).isEqualTo(2);
		assertThat(meta.isCancelled()).isTrue();
	}




	@Test
	public void testPriority() {

		final String CHANNEL = "test.channel";
		final EventService eventService = new EventServiceImpl();

		final PublishableEventListener listenerA = Mockito.mock(PublishableEventListener.class);
		final PublishableEventListener listenerB = Mockito.mock(PublishableEventListener.class);
		final PublishableEventListener listenerC = Mockito.mock(PublishableEventListener.class);

		eventService.subscribe(CHANNEL, 1, listenerA);
		eventService.subscribe(CHANNEL, listenerB); // expected priority: 0
		eventService.subscribe(CHANNEL, 2, listenerC);

		eventService.publish(publishable(CHANNEL));

		InOrder order = inOrder(listenerC, listenerA, listenerB);
		order.verify(listenerC).onEvent(any());
		order.verify(listenerA).onEvent(any());
		order.verify(listenerB).onEvent(any());
	}




	@Test
	public void testCancelEvents() {

		final String CHANNEL = "test.channel";
		final EventService eventService = new EventServiceImpl();

		final PublishableEventListener listenerA = Mockito.mock(PublishableEventListener.class);
		final PublishableEventListener listenerB = Publishable::cancel;
		final PublishableEventListener listenerC = Mockito.mock(PublishableEventListener.class);
		final PublishableEventListener listenerD = Mockito.mock(PublishableEventListener.class);
		eventService.subscribe(CHANNEL, 2, listenerA);
		eventService.subscribe(CHANNEL, 1, listenerB);
		eventService.subscribe(CHANNEL, 0, listenerC);
		eventService.subscribe(listenerD);

		eventService.publish(publishable(CHANNEL));

		verify(listenerA, times(1)).onEvent(any());
		verify(listenerC, never()).onEvent(any());
		verify(listenerD, never()).onEvent(any());
	}




	private Publishable publishable(final String channel) {
		return new Publishable(channel) {
		};
	}


}
