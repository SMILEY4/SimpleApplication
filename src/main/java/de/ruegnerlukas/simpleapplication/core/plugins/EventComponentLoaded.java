package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.Getter;


/**
 * The event when a component (in the plugin-service) was loaded.
 */
@Getter
public final class EventComponentLoaded extends Publishable {


	/**
	 * The id of the loaded component.
	 */
	private final String componentId;




	/**
	 * @param componentId the id of the loaded component
	 */
	public EventComponentLoaded(final String componentId) {
		super(Channel.type(EventComponentLoaded.class));
		this.componentId = componentId;
	}

}
