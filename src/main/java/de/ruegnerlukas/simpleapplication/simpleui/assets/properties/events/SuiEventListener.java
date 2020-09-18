package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

public interface SuiEventListener<T> {


	/**
	 * Called when an event is triggered.
	 *
	 * @param event the event
	 */
	void onEvent(T event);

}
