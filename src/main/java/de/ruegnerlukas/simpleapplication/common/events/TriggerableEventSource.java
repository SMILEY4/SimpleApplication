package de.ruegnerlukas.simpleapplication.common.events;

public interface TriggerableEventSource<T> {


	/**
	 * Triggers this event source with the given event/data
	 *
	 * @param event the event/data
	 */
	void trigger(T event);

}
