package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.core.application.ApplicationConstants;
import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.Getter;


@Getter
public class EventComponentLoaded extends Publishable {


	/**
	 * The id of the loaded component.
	 */
	private final String componentId;




	/**
	 * @param componentId the id of the loaded component
	 */
	public EventComponentLoaded(final String componentId) {
		super(Channel.name(ApplicationConstants.EVENT_COMPONENT_LOADED));
		this.componentId = componentId;
	}

}
