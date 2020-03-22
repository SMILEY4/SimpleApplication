package de.ruegnerlukas.simpleapplication.common.events;

import de.ruegnerlukas.simpleapplication.common.events.specializedevents.EmptyEvent;
import de.ruegnerlukas.simpleapplication.common.events.specializedevents.StringEvent;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

public class EventsTest {


	@Test
	public void testSimpleEventSource() {

		final String EVENT = "test_event";

		// create event source
		final EventSource<String> source = new EventSource<>();
		final TriggerableEventSource<String> triggerable = source;
		final ListenableEventSource<String> listenable = source;

		// subscribe listener to source
		final EventListener<String> listener = (EventListener<String>) Mockito.mock(EventListener.class);
		listenable.subscribe(listener);

		// trigger event
		triggerable.trigger(EVENT);
		verify(listener).onEvent(eq(EVENT));
	}




	@Test
	public void testStringEvent() {

		final String EVENT = "test_event";

		// create event source
		final StringEvent.StringEventSource source = new StringEvent.StringEventSource();

		// subscribe listener to source
		final StringEvent.StringEventListener listener = Mockito.mock(StringEvent.StringEventListener.class);
		source.subscribe(listener);

		// trigger event
		source.trigger(EVENT);
		verify(listener).onEvent(eq(EVENT));
	}




	@Test
	public void testEmptyEvent() {

		// create event source
		final EmptyEvent.EmptyEventSource source = new EmptyEvent.EmptyEventSource();

		// subscribe listener to source
		final EmptyEvent.EmptyEventListener listener = Mockito.mock(EmptyEvent.EmptyEventListener.class);
		source.subscribe(listener);

		// trigger event
		final EmptyEvent.EmptyTriggerableEventSource triggerable = source;
		triggerable.trigger();
		verify(listener).onEvent(eq(EmptyEvent.INSTANCE));
	}




	@Test
	public void testFixedEventSource() {

		// create fixed event source
		final StringEvent.FixedStringEventSource source = new StringEvent.FixedStringEventSource("Hello World!");

		// subscribe listener to source
		final StringEvent.StringEventListener listener = Mockito.mock(StringEvent.StringEventListener.class);
		source.subscribe(listener);

		// trigger event
		source.trigger();
		verify(listener).onEvent(eq(source.getEvent()));
	}




	@Test
	public void testEventSourceGroup() {

		final String SOURCE_NAME_A = "test_source_a";
		final String SOURCE_NAME_B = "test_source_b";

		final String EVENT_A = "test event a";
		final String EVENT_B = "test event b";

		// create event sources
		final StringEvent.StringEventSource sourceA = new StringEvent.StringEventSource();
		final StringEvent.StringEventSource sourceB = new StringEvent.StringEventSource();

		// create listenable group
		final ListenableEventSourceGroup groupListenable = new ListenableEventSourceGroup();
		groupListenable.add(Channel.name(SOURCE_NAME_A), sourceA);
		groupListenable.add(Channel.name(SOURCE_NAME_B), sourceB);

		// create triggerable group
		final TriggerableEventSourceGroup groupTriggerable = new TriggerableEventSourceGroup();
		groupTriggerable.add(Channel.name(SOURCE_NAME_A), sourceA);
		groupTriggerable.add(Channel.name(SOURCE_NAME_B), sourceB);

		// subscribe listener to sources (through group)
		final StringEvent.StringEventListener listenerA = Mockito.mock(StringEvent.StringEventListener.class);
		final ListenableEventSource<String> listenableA = groupListenable.find(Channel.name(SOURCE_NAME_A));
		assertThat(listenableA).isNotNull();
		listenableA.subscribe(listenerA);

		final StringEvent.StringEventListener listenerB = Mockito.mock(StringEvent.StringEventListener.class);
		final ListenableEventSource<String> listenableB = groupListenable.find(Channel.name(SOURCE_NAME_B));
		assertThat(listenableB).isNotNull();
		listenableB.subscribe(listenerB);

		// trigger events (through group)
		final TriggerableEventSource<String> triggerableA = groupTriggerable.find(Channel.name(SOURCE_NAME_A));
		triggerableA.trigger(EVENT_A);
		final TriggerableEventSource<String> triggerableB = groupTriggerable.find(Channel.name(SOURCE_NAME_B));
		triggerableB.trigger(EVENT_B);

		// verify
		verify(listenerA).onEvent(eq(EVENT_A));
		verify(listenerB).onEvent(eq(EVENT_B));
	}


}
