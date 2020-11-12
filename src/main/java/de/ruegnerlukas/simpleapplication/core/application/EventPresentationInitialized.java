package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;

/**
 * The event triggered when javafx and the presentation system was initialized.
 */
public final class EventPresentationInitialized {


	public static final String EVENT_TYPE = "event.type.presentation.initialized";

	public static final Tags TAGS = Tags.from(EVENT_TYPE);



	/**
	 * The default constructor.
	 */
	public EventPresentationInitialized() {
	}

}
