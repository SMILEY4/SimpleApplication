package de.ruegnerlukas.simpleapplication.common.events;

public interface TriggerableEvent<T> {


	/**
	 * Triggers this event with the given data
	 *
	 * @param data the data
	 */
	void trigger(T data);


	/**
	 * Triggers this event without any data
	 */
	void trigger();

}
