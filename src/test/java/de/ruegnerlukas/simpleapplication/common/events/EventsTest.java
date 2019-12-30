package de.ruegnerlukas.simpleapplication.common.events;

import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

public class EventsTest {


	@Test
	public void testOneListener() {
		final String str = "some string";
		final Event<String> event = new SimpleEvent<>();
		final EventListener listener = Mockito.mock(EventListener.class);
		event.addListener(listener);
		event.trigger(str);
		verify(listener).onEvent(eq(str));
	}




	@Test
	public void testMultipleListeners() {
		final String str = "some other string";
		final Event<String> event = new SimpleEvent<>();

		final EventListener listener1 = Mockito.mock(EventListener.class);
		final EventListener listener2 = Mockito.mock(EventListener.class);
		final EventListener listener3 = Mockito.mock(EventListener.class);

		event.addListener(listener1);
		event.addListener(listener2);
		event.addListener(listener3);

		event.trigger(str);
		verify(listener1).onEvent(eq(str));
		verify(listener2).onEvent(eq(str));
		verify(listener3).onEvent(eq(str));

	}


}
