package de.ruegnerlukas.simpleapplication.core.simpleui.core.events;

public interface SuiEventListener<T> {


	/**
	 * Called when an event is triggered.
	 *
	 * @param event the event
	 */
	void onEvent(T event);

}
