package de.ruegnerlukas.simpleapplication.common.events;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;

import java.util.ArrayList;
import java.util.List;

public class EventSource<T> implements ListenableEventSource<T>, TriggerableEventSource<T> {


	/**
	 * The subscribed {@link EventListener}s.
	 */
	private final List<EventListener<T>> subscribers = new ArrayList<>();




	@Override
	public void subscribe(final EventListener<T> listener) {
		Validations.INPUT.notNull(listener).exception("The listener must not be null.");
		subscribers.add(listener);
	}




	@Override
	public void unsubscribe(final EventListener<T> listener) {
		subscribers.remove(listener);
	}




	@Override
	public void trigger(final T event) {
		Validations.INPUT.notNull(event).exception("The event must not be null.");
		subscribers.forEach(listener -> listener.onEvent(event));
	}

}
