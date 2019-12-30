package de.ruegnerlukas.simpleapplication.common.events;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EventGroupTest {


	@Test
	public void testListenableEventGroup() {
		final ListenableEventGroup group = new ListenableEventGroup();

		group.addEvent("event-1", new SimpleEvent());
		group.addEvent("event-2", new SimpleEvent());
		group.addEvent("event-3", new SimpleEvent());
		assertThat(group.getEvents().keySet()).containsExactlyInAnyOrder("event-1", "event-2", "event-3");

		assertThat(group.getEvent("event-1")).isNotNull();
		assertThat(group.getEvent("event-2")).isNotNull();
		assertThat(group.getEvent("event-3")).isNotNull();
		assertThat(group.getEvent("invalid-event")).isNull();
	}




	@Test
	public void testTriggerableEventGroup() {
		final TriggerableEventGroup group = new TriggerableEventGroup();

		group.addEvent("event-1", new SimpleEvent());
		group.addEvent("event-2", new SimpleEvent());
		group.addEvent("event-3", new SimpleEvent());
		assertThat(group.getEvents().keySet()).containsExactlyInAnyOrder("event-1", "event-2", "event-3");

		assertThat(group.getEvent("event-1")).isNotNull();
		assertThat(group.getEvent("event-2")).isNotNull();
		assertThat(group.getEvent("event-3")).isNotNull();
		assertThat(group.getEvent("invalid-event")).isNull();
	}


}
