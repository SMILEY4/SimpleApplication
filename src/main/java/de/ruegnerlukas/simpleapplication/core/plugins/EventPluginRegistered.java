package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.core.simpleui.core.tags.Tags;
import lombok.Getter;

/**
 * The event when a new plugin was registered.
 */
@Getter
public final class EventPluginRegistered {


	public static final String EVENT_TYPE = "event.type.plugins.plugin.registered";

	public static final Tags TAGS = Tags.from(EVENT_TYPE);


	/**
	 * The id of the de-registered plugin.
	 */
	private final String pluginId;




	/**
	 * @param pluginId the id of the registered plugin
	 */
	public EventPluginRegistered(final String pluginId) {
		this.pluginId = pluginId;
	}

}
