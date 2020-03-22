package de.ruegnerlukas.simpleapplication.core.events;


import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.common.events.EventListener;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static de.ruegnerlukas.simpleapplication.core.events.PublishableEvent.PublishableEventListener;
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
	public void testUnscubscribe() {

		final EventService eventService = new EventServiceImpl();
		final PublishableEventListener listenerA = Mockito.mock(PublishableEventListener.class);
		final PublishableEventListener listenerB = Mockito.mock(PublishableEventListener.class);
		final PublishableEventListener listenerC = Mockito.mock(PublishableEventListener.class);

		/*
		channel 1: a
		channel 2: b
		channel 3: b, c
		      all: a
		 */
		eventService.subscribe(Channel.name("channel 1"), listenerA);
		eventService.subscribe(Channel.name("channel 2"), listenerB);
		eventService.subscribe(Channel.name("channel 3"), listenerB);
		eventService.subscribe(Channel.name("channel 3"), listenerC);
		eventService.subscribe(listenerA);

		eventService.unsubscribe(listenerA);
		eventService.unsubscribe(listenerB);
		eventService.unsubscribe(listenerC);

		eventService.publish(publishable("channel 1"));
		eventService.publish(publishable("channel 2"));
		eventService.publish(publishable("channel 3"));

		verify(listenerA, never()).onEvent(any());
		verify(listenerB, never()).onEvent(any());
		verify(listenerC, never()).onEvent(any());

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




	@Test
	public void testAnnotationByName() {
		final String CHANNEL = "test.channel";
		final EventService eventService = new EventServiceImpl();

		AnnotatedClass annotatedClass = new AnnotatedClass();
		eventService.register(annotatedClass);

		Publishable publishable = publishable(CHANNEL);
		eventService.publish(publishable);

		assertThat(annotatedClass.callsByName).isEqualTo(1);
		assertThat(annotatedClass.lastReceived).isEqualTo(publishable);
	}




	@Test
	public void testAnnotationByType() {
		final EventService eventService = new EventServiceImpl();

		AnnotatedClass annotatedClass = new AnnotatedClass();
		eventService.register(annotatedClass);

		Publishable publishable = new TestEvent();
		eventService.publish(publishable);

		assertThat(annotatedClass.callsByType).isEqualTo(1);
		assertThat(annotatedClass.lastReceived).isEqualTo(publishable);
	}




	@Test
	public void testAnnotationPriorityLower() {
		final EventService eventService = new EventServiceImpl();

		AnnotatedClass annotatedClass = new AnnotatedClass();
		eventService.register(annotatedClass);
		eventService.subscribe(Channel.name("test.channel.priority"), 100, Publishable::cancel);

		PublishableMeta meta = eventService.publish(publishable("test.channel.priority"));
		assertThat(annotatedClass.callsByNameWithPriority).isEqualTo(0);
		assertThat(meta.isCancelled());
		assertThat(meta.getNumListeners()).isEqualTo(2);
		assertThat(meta.getNumReceivers()).isEqualTo(1);
	}




	@Test
	public void testAnnotationPriorityHigher() {
		final EventService eventService = new EventServiceImpl();

		AnnotatedClass annotatedClass = new AnnotatedClass();
		eventService.register(annotatedClass);
		eventService.subscribe(Channel.name("test.channel.priority"), 5, Publishable::cancel);

		PublishableMeta meta = eventService.publish(publishable("test.channel.priority"));
		assertThat(annotatedClass.callsByNameWithPriority).isEqualTo(1);
		assertThat(meta.isCancelled());
		assertThat(meta.getNumListeners()).isEqualTo(2);
		assertThat(meta.getNumReceivers()).isEqualTo(2);

	}




	@Test
	public void testAnnotationStaticMethod() {
		final String CHANNEL = "test.channel.static";
		final EventService eventService = new EventServiceImpl();

		eventService.register(AnnotatedClass.class);

		Publishable publishable = publishable(CHANNEL);
		eventService.publish(publishable);

		assertThat(AnnotatedClass.callsStaticMethod).isEqualTo(1);
		assertThat(AnnotatedClass.lastReceivedStatic).isEqualTo(publishable);
	}




	@Test
	public void testMultithreadedSubscribe() {

		final String CHANNEL = "test.channel.static";
		final EventService eventService = new EventServiceImpl();

		List<PublishableEventListener> listeners = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			listeners.add(Mockito.mock(PublishableEventListener.class));
		}

		// subscribe async
		listeners.parallelStream().forEach(listener ->
				eventService.subscribe(Channel.name(CHANNEL), new Random().nextInt(100), listener));

		PublishableMeta meta = eventService.publish(publishable(CHANNEL));
		assertThat(meta.getNumReceivers()).isEqualTo(listeners.size());
		assertThat(meta.getNumListeners()).isEqualTo(listeners.size());
	}




	@Test
	public void testMultithreadedPublish() {

		final String CHANNEL = "test.channel.static";
		final EventService eventService = new EventServiceImpl();

		List<PublishableEventListener> listeners = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			PublishableEventListener listener = Mockito.mock(PublishableEventListener.class);
			eventService.subscribe(Channel.name(CHANNEL), listeners.size() - i, listener);
			listeners.add(listener);
		}

		List<Publishable> publishables = new ArrayList<>();
		for (int i = 0; i < 100; i++) {
			publishables.add(publishable(CHANNEL));
		}

		// publish async
		publishables.parallelStream().forEach(publishable -> {
			PublishableMeta meta = eventService.publish(publishable);
			assertThat(meta.getNumListeners()).isEqualTo(listeners.size());
			assertThat(meta.getNumReceivers()).isEqualTo(listeners.size());
		});

	}




	private Publishable publishable(final String channel) {
		return new Publishable(Channel.name(channel)) {
		};
	}




	static class AnnotatedClass {


		public int callsByName = 0;
		public int callsByNameWithPriority = 0;
		public int callsByType = 0;
		public Publishable lastReceived = null;

		public static int callsStaticMethod = 0;
		public static Publishable lastReceivedStatic = null;




		@Listener (name = "test.channel")
		public void testMethodName(Publishable publishable) {
			System.out.println("Received by name: " + publishable);
			lastReceived = publishable;
			callsByName++;
		}




		@Listener (name = "test.channel.priority", priority = 10)
		public void testMethodNamePriority10(Publishable publishable) {
			System.out.println("Received by name with priority: " + publishable);
			lastReceived = publishable;
			callsByNameWithPriority++;
		}




		@Listener (type = TestEvent.class)
		public void testMethodType(Publishable publishable) {
			System.out.println("Received by type: " + publishable);
			lastReceived = publishable;
			callsByType++;
		}




		@Listener (name = "test.channel.static")
		public static void testStaticMethod(Publishable publishable) {
			System.out.println("Received by name static: " + publishable);
			lastReceivedStatic = publishable;
			callsStaticMethod++;
		}

	}






	static class TestEvent extends Publishable {


		public TestEvent(String channel) {
			super(Channel.name(channel));
		}




		public TestEvent() {
			super(Channel.type(TestEvent.class));
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
