package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import lombok.Getter;

/**
 * The event when a plugin was unloaded.
 */
@Getter
public final class EventPluginUnloaded {


	public static final String EVENT_TYPE = "event.type.plugins.plugin.unloaded";

	public static final Tags TAGS = Tags.from(EVENT_TYPE);


	/**
	 * The id of the unloaded plugin.
	 */
	private final String pluginId;




	/**
	 * @param pluginId the id of the unloaded plugin
	 */
	public EventPluginUnloaded(final String pluginId) {
		this.pluginId = pluginId;
	}

}
