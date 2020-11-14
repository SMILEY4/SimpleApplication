package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.core.simpleui.core.tags.Tags;
import lombok.Getter;

/**
 * The event when a component (in the plugin-service) was unloaded.
 */
@Getter
public final  class EventComponentUnloaded {


	public static final String EVENT_TYPE = "event.type.plugins.component.unloaded";

	public static final Tags TAGS = Tags.from(EVENT_TYPE);


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
