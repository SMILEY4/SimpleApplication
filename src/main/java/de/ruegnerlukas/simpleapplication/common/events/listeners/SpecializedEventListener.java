package de.ruegnerlukas.simpleapplication.common.events.listeners;

import de.ruegnerlukas.simpleapplication.common.events.Event;

public interface SpecializedEventListener<T extends Event> extends EventListener {


	void onSpecializedEvent(T event);

	@Override
	default void onEvent(Event event) {
		onSpecializedEvent((T) event);
	}

}
