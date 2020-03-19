package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;

public class EventApplicationStopping extends Publishable {


	/**
	 * The default constructor.
	 */
	public EventApplicationStopping() {
		super(Channel.name(ApplicationConstants.EVENT_APPLICATION_STOPPING));
	}

}
