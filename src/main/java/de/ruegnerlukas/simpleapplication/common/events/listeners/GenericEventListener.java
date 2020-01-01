package de.ruegnerlukas.simpleapplication.common.events.listeners;

import de.ruegnerlukas.simpleapplication.common.events.Event;

public interface GenericEventListener<T extends Event> extends EventListener {


	/**
	 * @param event the generic event
	 */
	void onReceivedEvent(T event);

	@Override
	default void onEvent(Event event) {
		onReceivedEvent((T) event);
	}

}
