package de.ruegnerlukas.simpleapplication.common.events.listeners;

import de.ruegnerlukas.simpleapplication.common.events.Event;

public interface EventListener extends GenericListener<Event> {


	@Override
	void onEvent(Event event);

}
