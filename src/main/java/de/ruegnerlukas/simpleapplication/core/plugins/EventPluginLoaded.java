package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.core.simpleui.core.tags.Tags;
import lombok.Getter;

/**
 * The event when a plugin was loaded.
 */
@Getter
public final class EventPluginLoaded {


	public static final String EVENT_TYPE = "event.type.plugins.plugin.loaded";

	public static final Tags TAGS = Tags.from(EVENT_TYPE);


	/**
	 * The id of the loaded plugin.
	 */
	private final String pluginId;




	/**
	 * @param pluginId the id of the loaded plugin
	 */
	public EventPluginLoaded(final String pluginId) {
		this.pluginId = pluginId;
	}

}
