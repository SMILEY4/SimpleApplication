package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.core.simpleui.core.tags.Tags;
import lombok.Getter;


/**
 * The event when a component (in the plugin-service) was loaded.
 */
@Getter
public final class EventComponentLoaded {


	public static final String EVENT_TYPE = "event.type.plugins.component.loaded";

	public static final Tags TAGS = Tags.from(EVENT_TYPE);


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
