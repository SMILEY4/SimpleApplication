package de.ruegnerlukas.simpleapplication.common.events;

import de.ruegnerlukas.simpleapplication.common.events.listeners.EventListener;

public interface ListenableEventSource {


	/**
	 * Subscribes the given {@link EventListener} to this event source.
	 *
	 * @param listener the listener
	 */
	void subscribe(EventListener listener);

	/**
	 * Unsubscribe the given {@link EventListener} from this event source.
	 *
	 * @param listener the listener
	 */
	void unsubscribe(EventListener listener);

	/**
	 * @return the total number of subscribed listeners.
	 */
	int getSubscriberCount();

}
