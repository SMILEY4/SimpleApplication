package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.Getter;

/**
 * The event when a component (in the plugin-service) was unloaded.
 */
@Getter
public final  class EventComponentUnloaded extends Publishable {


	/**
	 * The id of the unloaded component.
	 */
	private final String componentId;




	/**
	 * @param componentId the id of the unloaded component
	 */
	public EventComponentUnloaded(final String componentId) {
		super(Channel.type(EventComponentUnloaded.class));
		this.componentId = componentId;
	}

}
