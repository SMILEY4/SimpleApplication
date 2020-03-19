package de.ruegnerlukas.simpleapplication.core.events;


import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.common.events.EventListener;
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
		eventService.subscribe(Channel.name(CHANNEL), listener);

		eventService.publish(publishable(CHANNEL));
		eventService.unsubscribe(Channel.name(CHANNEL), listener);
		eventService.publish(publishable(CHANNEL));

		verify(listener, times(1)).onEvent(any());
	}




	@Test
	public void testMetadataSimple() {

		final String CHANNEL = "test.channel";
		final EventService eventService = new EventServiceImpl();

		eventService.subscribe(Channel.name(CHANNEL), Mockito.mock(PublishableEventListener.class));
		eventService.subscribe(Channel.name(CHANNEL), Mockito.mock(PublishableEventListener.class));

		final Publishable publishable = publishable(CHANNEL);

		final long timeBefore = System.currentTimeMillis();
		final PublishableMeta meta = eventService.publish(publishable);
		final long timeAfter = System.currentTimeMillis();

		assertThat(meta.getPublishable()).isEqualTo(publishable);
		assertThat(meta.isPublished()).isTrue();
		assertThat(meta.getChannel()).isEqualTo(Channel.name(CHANNEL));
		assertThat(timeBefore <= meta.getTimestamp() && meta.getTimestamp() <= timeAfter).isTrue();
		assertThat(meta.getNumListeners()).isEqualTo(2);
		assertThat(meta.getNumReceivers()).isEqualTo(2);
		assertThat(meta.isCancelled()).isFalse();
	}




	@Test
	public void testMetadataCancelled() {

		final String CHANNEL = "test.channel";
		final EventService eventService = new EventServiceImpl();

		eventService.subscribe(Channel.name(CHANNEL), Mockito.mock(PublishableEventListener.class));
		eventService.subscribe(Channel.name(CHANNEL), Publishable::cancel);
		eventService.subscribe(Channel.name(CHANNEL), Mockito.mock(PublishableEventListener.class));

		final PublishableMeta meta = eventService.publish(publishable(CHANNEL));

		assertThat(meta.isPublished()).isTrue();
		assertThat(meta.getChannel()).isEqualTo(Channel.name(CHANNEL));
		assertThat(meta.getNumListeners()).isEqualTo(3);
		assertThat(meta.getNumReceivers()).isEqualTo(2);
		assertThat(meta.isCancelled()).isTrue();
	}




	@Test
	public void testCustomEvent() {

		final String CHANNEL = "test.channel";
		final EventService eventService = new EventServiceImpl();

		TestEventListener listener = Mockito.mock(TestEventListener.class);
		eventService.subscribe(Channel.name(CHANNEL), listener);

		PublishableMeta meta = eventService.publish(new TestEvent(CHANNEL));
		verify(listener).onEvent(any());
		assertThat(meta.getNumReceivers()).isEqualTo(1);
		assertThat(meta.getNumListeners()).isEqualTo(1);
	}




	@Test
	public void testCustomEventWrongType() {

		final String CHANNEL = "test.channel";
		final EventService eventService = new EventServiceImpl();

		TestEventListener listener = Mockito.mock(TestEventListener.class);
		eventService.subscribe(Channel.name(CHANNEL), listener);

		PublishableMeta meta = eventService.publish(new TestCommand(CHANNEL));
		verify(listener, times(0)).onEvent(any());
		assertThat(meta.getNumReceivers()).isEqualTo(0);
		assertThat(meta.getNumListeners()).isEqualTo(1);
	}




	@Test
	public void testCustomEventGenericListener() {

		final String CHANNEL = "test.channel";
		final EventService eventService = new EventServiceImpl();

		PublishableEventListener listener = Mockito.mock(PublishableEventListener.class);
		eventService.subscribe(Channel.name(CHANNEL), listener);

		PublishableMeta metaA = eventService.publish(new TestEvent(CHANNEL));
		PublishableMeta metaB = eventService.publish(new TestCommand(CHANNEL));

		verify(listener, times(2)).onEvent(any());
		assertThat(metaA.getNumReceivers()).isEqualTo(1);
		assertThat(metaA.getNumListeners()).isEqualTo(1);
		assertThat(metaB.getNumReceivers()).isEqualTo(1);
		assertThat(metaB.getNumListeners()).isEqualTo(1);
	}




	@Test
	public void testListenerPriority() {

		final String CHANNEL = "test.channel";
		final EventService eventService = new EventServiceImpl();

		final PublishableEventListener listenerA = Mockito.mock(PublishableEventListener.class);
		final PublishableEventListener listenerB = Mockito.mock(PublishableEventListener.class);
		final PublishableEventListener listenerC = Mockito.mock(PublishableEventListener.class);

		eventService.subscribe(Channel.name(CHANNEL), 1, listenerA);
		eventService.subscribe(Channel.name(CHANNEL), listenerB); // expected priority: 0
		eventService.subscribe(Channel.name(CHANNEL), 2, listenerC);

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
		eventService.subscribe(Channel.name(CHANNEL), 2, listenerA);
		eventService.subscribe(Channel.name(CHANNEL), 1, listenerB);
		eventService.subscribe(Channel.name(CHANNEL), 0, listenerC);
		eventService.subscribe(listenerD);

		eventService.publish(publishable(CHANNEL));

		verify(listenerA, times(1)).onEvent(any());
		verify(listenerC, never()).onEvent(any());
		verify(listenerD, never()).onEvent(any());
	}




	private Publishable publishable(final String channel) {
		return new Publishable(Channel.name(channel)) {
		};
	}




	static class TestEvent extends Publishable {


		public TestEvent(String channel) {
			super(Channel.name(channel));
		}

	}






	static class TestCommand extends Publishable {


		public TestCommand(String channel) {
			super(Channel.name(channel));
		}

	}






	abstract static class TestEventListener implements EventListener<TestEvent> {


	}


}
