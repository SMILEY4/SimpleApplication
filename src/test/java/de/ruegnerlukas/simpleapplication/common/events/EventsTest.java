package de.ruegnerlukas.simpleapplication.common.events;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class EventsTest {


	@Test
	public void testEvents() {

		final Event<String> event = new SimpleEvent<>();

		event.addListener(log::info);

		event.trigger("Im an event!");

	}


}
