package de.ruegnerlukas.simpleapplication.common.events;

public interface TriggerableEventSource<T extends Event> {


	/**
	 * Triggers this event source with the given {@link Event}.
	 *
	 * @param event the event
	 */
	void trigger(T event);

}
