package de.ruegnerlukas.simpleapplication.core.events;


import de.ruegnerlukas.simpleapplication.common.events.EventListener;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class EventServiceTest {


	@Test
	public void testSimple() {

		final String CHANNEL = "test.channel";
		final EventService eventService = new EventServiceImpl();

		final EventServiceListener listener = Mockito.mock(EventServiceListener.class);
		eventService.subscribe(CHANNEL, listener);

		eventService.publish(publishable(CHANNEL));
		eventService.unsubscribe(CHANNEL, listener);
		eventService.publish(publishable(CHANNEL));

		verify(listener, times(1)).onEvent(any());

	}




	private Publishable publishable(final String channel) {
		return new Publishable(channel) {
		};
	}




	abstract class EventServiceListener implements EventListener<Publishable> {


	}


}
