package de.ruegnerlukas.simpleapplication.common.events.listeners;

public interface Listener<T> {


	/**
	 * @param data the generic event
	 */
	void onEvent(T data);

}
