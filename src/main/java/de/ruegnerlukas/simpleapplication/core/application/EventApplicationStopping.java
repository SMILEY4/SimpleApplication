package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;

/**
 * The event triggered when the application is about to stop and close.
 */
public final class EventApplicationStopping extends Publishable {


	/**
	 * The default constructor.
	 */
	public EventApplicationStopping() {
		super(Channel.type(EventApplicationStopping.class));
	}

}
