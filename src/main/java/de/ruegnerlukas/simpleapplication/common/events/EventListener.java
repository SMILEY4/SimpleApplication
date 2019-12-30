package de.ruegnerlukas.simpleapplication.common.events;

public interface EventListener<T> {


	/**
	 * Called when the event was triggered.
	 *
	 * @param data the data
	 */
	void onEvent(T data);

}
