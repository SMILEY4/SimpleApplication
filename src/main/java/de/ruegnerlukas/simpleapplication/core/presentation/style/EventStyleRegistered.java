package de.ruegnerlukas.simpleapplication.core.presentation.style;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.Getter;

/**
 * The event when a new style was registered.
 */
@Getter
public class EventStyleRegistered extends Publishable {


	/**
	 * The name of the registered style.
	 */
	private final String style;




	/**
	 * @param style the name of the registered style
	 */
	public EventStyleRegistered(final String style) {
		super(Channel.type(EventStyleRegistered.class));
		this.style = style;
	}

}
