package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;

public class EventApplicationStarted extends Publishable {


	/**
	 * The default constructor.
	 */
	public EventApplicationStarted() {
		super(Channel.name(ApplicationConstants.EVENT_APPLICATION_STARTED));
	}

}
