package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.tags.Tags;

/**
 * The event triggered when the application is fully started.
 */
public final class EventApplicationStarted {


	public static final String EVENT_TYPE = "event.type.application.started";

	public static final Tags TAGS = Tags.from(EVENT_TYPE);




	/**
	 * The default constructor.
	 */
	public EventApplicationStarted() {
	}

}
