package de.ruegnerlukas.simpleapplication.common.events;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple implementation of an event.
 *
 * @param <T> the type of the event
 */
public class SimpleEvent<T> implements Event<T> {


	/**
	 * The list of listeners of this event.
	 */
	private final List<EventListener<T>> listeners = new ArrayList<>();




	@Override
	public void addListener(final EventListener<T> listener) {
		listeners.add(listener);
	}




	@Override
	public void removeListener(final EventListener<T> listener) {
		listeners.remove(listener);
	}




	@Override
	public void trigger(final T data) {
		listeners.forEach(l -> l.onEvent(data));
	}




	@Override
	public void trigger() {
		trigger(null);
	}

}
