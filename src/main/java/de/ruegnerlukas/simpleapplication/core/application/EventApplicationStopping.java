package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.core.events.Publishable;

public class EventApplicationStopping extends Publishable {


	/**
	 * The default constructor.
	 */
	public EventApplicationStopping() {
		super(ApplicationConstants.EVENT_APPLICATION_STOPPING);
	}

}
