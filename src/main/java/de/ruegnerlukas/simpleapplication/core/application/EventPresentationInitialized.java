package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;

/**
 * The event triggered when javafx and the presentation system was initialized.
 */
public final class EventPresentationInitialized extends Publishable {


	/**
	 * The default constructor.
	 */
	public EventPresentationInitialized() {
		super(Channel.type(EventPresentationInitialized.class));
	}

}
