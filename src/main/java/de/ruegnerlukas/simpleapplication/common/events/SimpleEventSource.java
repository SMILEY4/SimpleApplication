package de.ruegnerlukas.simpleapplication.common.events;

import de.ruegnerlukas.simpleapplication.common.events.listeners.EventListener;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;

import java.util.ArrayList;
import java.util.List;

public class SimpleEventSource implements EventSource {


	/**
	 * The list of subscribers of this event source.
	 */
	private List<EventListener> subscribers = new ArrayList<>();




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
	public void trigger(final Event event) {
		Validations.INPUT.notNull(event, "The event must not be null.");
		event.setChannels(new String[]{});
		event.setReceivers(getSubscriberCount());
		event.setTimestamp(System.currentTimeMillis());
		subscribers.forEach(listener -> listener.onEvent(event));
	}


}
