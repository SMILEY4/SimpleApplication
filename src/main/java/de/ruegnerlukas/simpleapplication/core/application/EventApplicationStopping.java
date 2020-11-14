package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.tags.Tags;

/**
 * The event triggered when the application is about to stop and close.
 */
public final class EventApplicationStopping {


	public static final String EVENT_TYPE = "event.type.application.stopping";

	public static final Tags TAGS = Tags.from(EVENT_TYPE);




	/**
	 * The default constructor.
	 */
	public EventApplicationStopping() {
	}

}
