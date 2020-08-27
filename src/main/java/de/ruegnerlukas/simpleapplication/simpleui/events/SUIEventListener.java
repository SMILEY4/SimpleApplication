package de.ruegnerlukas.simpleapplication.simpleui.events;

public interface SUIEventListener<T> {


	/**
	 * Called when the event is triggered.
	 *
	 * @param event the event
	 */
	default void onEvent(final SUIEvent<T> event) {
		onEvent(event.getData());
	}


	/**
	 * Called when the event is triggered.
	 *
	 * @param eventData the data of the event
	 */
	void onEvent(T eventData);

}