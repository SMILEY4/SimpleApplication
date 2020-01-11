package de.ruegnerlukas.simpleapplication.common.events;

public interface EventListener<T> {


	/**
	 * Called on an event
	 *
	 * @param event the event/data
	 */
	void onEvent(T event);

}
