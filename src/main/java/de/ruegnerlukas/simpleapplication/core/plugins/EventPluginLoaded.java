package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.Getter;

/**
 * The event when a plugin was loaded.
 */
@Getter
public final class EventPluginLoaded extends Publishable {


	/**
	 * The id of the loaded plugin.
	 */
	private final String pluginId;




	/**
	 * @param pluginId the id of the loaded plugin
	 */
	public EventPluginLoaded(final String pluginId) {
		super(Channel.type(EventPluginLoaded.class));
		this.pluginId = pluginId;
	}

}
