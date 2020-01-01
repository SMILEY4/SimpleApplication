package de.ruegnerlukas.simpleapplication.common.events;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventSourceGroupTest {


	@Test
	public void testTriggerableGroup() {
		final String[] SOURCE_NAMES = new String[]{"event_1", "event_2", "event_3"};
		final TriggerableEventSourceGroup GROUP = new TriggerableEventSourceGroup();
		for (String source : SOURCE_NAMES) {
			GROUP.addEventSource(source, new GenericEventSource());
		}
		for (String source : SOURCE_NAMES) {
			assertThat(GROUP.getEventSource(source)).isNotNull();
		}
		assertThat(GROUP.getEventSource("invalid name")).isNull();
	}




	@Test
	public void testListenableGroup() {
		final String[] SOURCE_NAMES = new String[]{"event_1", "event_2", "event_3"};
		final ListenableEventSourceGroup GROUP = new ListenableEventSourceGroup();
		for (String source : SOURCE_NAMES) {
			GROUP.addEventSource(source, new GenericEventSource());
		}
		for (String source : SOURCE_NAMES) {
			assertThat(GROUP.getEventSource(source)).isNotNull();
		}
		assertThat(GROUP.getEventSource("invalid name")).isNull();
	}

}
