package de.ruegnerlukas.simpleapplication.common.events;

import de.ruegnerlukas.simpleapplication.common.events.listeners.EventListener;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class GenericFixedEventSource<T extends Event> implements FixedTriggerableEventSource, ListenableEventSource {


	/**
	 * The event triggered by this event source.
	 */
	@Getter
	private final T event;

	/**
	 * The list of subscribers of this event source.
	 */
	private List<EventListener> subscribers = new ArrayList<>();




	/**
	 * @param event the event triggered by this event source
	 */
	public GenericFixedEventSource(final T event) {
		this.event = event;
		this.event.setChannels(new String[]{});
	}




	@Override
	public void subscribe(final EventListener listener) {
		Validations.INPUT.notNull(listener, "The listener must not be null.");
		subscribers.add(listener);
	}




	@Override
	public void unsubscribe(final EventListener listener) {
		subscribers.remove(listener);
	}




	@Override
	public int getSubscriberCount() {
		return subscribers.size();
	}




	@Override
	public void trigger() {
		event.setReceivers(getSubscriberCount());
		event.setTimestamp(System.currentTimeMillis());
		subscribers.forEach(listener -> listener.onEvent(getEvent()));
	}


}
