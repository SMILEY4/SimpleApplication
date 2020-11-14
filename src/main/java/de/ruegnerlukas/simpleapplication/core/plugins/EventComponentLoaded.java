package de.ruegnerlukas.simpleapplication.core.plugins;

import lombok.Getter;


/**
 * The event when a component (in the plugin-service) was loaded.
 */
@Getter
public final class EventComponentLoaded {


	/**
	 * The id of the loaded component.
	 */
	private final String componentId;




	/**
	 * @param componentId the id of the loaded component
	 */
	public EventComponentLoaded(final String componentId) {
		this.componentId = componentId;
	}

}
