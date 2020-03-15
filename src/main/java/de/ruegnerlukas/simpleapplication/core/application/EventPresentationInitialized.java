package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.core.events.Publishable;

public class EventPresentationInitialized extends Publishable {


	/**
	 * The default constructor.
	 */
	public EventPresentationInitialized() {
		super(ApplicationConstants.EVENT_PRESENTATION_INITIALIZED);
	}

}
