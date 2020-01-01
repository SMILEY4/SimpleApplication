package de.ruegnerlukas.simpleapplication.common.events;

public interface TriggerableEventSource {


	/**
	 * Triggers the given {@link Event}.
	 *
	 * @param event the event
	 */
	void trigger(Event event);

}
