package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.core.application.ApplicationConstants;
import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.Getter;

@Getter
public class EventComponentUnloaded extends Publishable {


	/**
	 * The id of the unloaded component.
	 */
	private final String componentId;




	/**
	 * @param componentId the id of the unloaded component
	 */
	public EventComponentUnloaded(final String componentId) {
		super(Channel.name(ApplicationConstants.EVENT_COMPONENT_UNLOADED));
		this.componentId = componentId;
	}

}
