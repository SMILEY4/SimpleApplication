package de.ruegnerlukas.simpleapplication.common.events2;

public interface ListenableEventSource<T> {


	/**
	 * Subscribes the given listener to this event source.
	 *
	 * @param listener the {@link EventListener}
	 */
	void subscribe(EventListener<T> listener);

	/**
	 * Unsubscribes the given listener from this event source.
	 *
	 * @param listener the {@link EventListener}
	 */
	void unsubscribe(EventListener<T> listener);


}
