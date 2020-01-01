package de.ruegnerlukas.simpleapplication.common.events;

import de.ruegnerlukas.simpleapplication.common.events.events.EmptyEvent;

public interface FixedTriggerableEventSource extends TriggerableEventSource<EmptyEvent> {


	/**
	 * Triggers the event of this event source.
	 */
	void trigger();

	/**
	 * USE {@link FixedTriggerableEventSource#trigger()} INSTEAD! Triggers the event of this event source and ignores the given event.
	 *
	 * @param event ignored event.
	 */
	@Override
	default void trigger(final EmptyEvent event) {
		trigger();
	}

}
