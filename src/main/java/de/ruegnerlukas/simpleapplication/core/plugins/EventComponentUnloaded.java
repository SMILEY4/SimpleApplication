package de.ruegnerlukas.simpleapplication.core.plugins;

import lombok.Getter;

/**
 * The event when a component (in the plugin-service) was unloaded.
 */
@Getter
public final class EventComponentUnloaded {


	/**
	 * The id of the unloaded component.
	 */
	private final String componentId;




	/**
	 * @param componentId the id of the unloaded component
	 */
	public EventComponentUnloaded(final String componentId) {
		this.componentId = componentId;
	}

}
