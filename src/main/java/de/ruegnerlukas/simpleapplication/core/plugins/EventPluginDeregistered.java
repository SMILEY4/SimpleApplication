package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.core.simpleui.core.tags.Tags;
import lombok.Getter;

/**
 * The event when a new plugin was deregistered.
 */
@Getter
public final class EventPluginDeregistered {


	public static final String EVENT_TYPE = "event.type.plugins.plugin.deregistered";

	public static final Tags TAGS = Tags.from(EVENT_TYPE);


	/**
	 * The id of the de-registered plugin.
	 */
	private final String pluginId;




	/**
	 * @param pluginId the id of the de-registered plugin
	 */
	public EventPluginDeregistered(final String pluginId) {
		this.pluginId = pluginId;
	}

}
