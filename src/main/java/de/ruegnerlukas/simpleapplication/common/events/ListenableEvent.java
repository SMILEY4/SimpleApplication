package de.ruegnerlukas.simpleapplication.common.events;

public interface ListenableEvent<T> {


	/**
	 * Adds the given listener to this event.
	 *
	 * @param listener the {@link EventListener}
	 */
	void addListener(EventListener<T> listener);

	/**
	 * Removes the given listener from this event.
	 *
	 * @param listener the {@link EventListener}
	 */
	void removeListener(EventListener<T> listener);

}
