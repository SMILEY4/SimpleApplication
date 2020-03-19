package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.Getter;

/**
 * The event when a new plugin was registered.
 */
@Getter
public final  class EventPluginRegistered extends Publishable {


	/**
	 * The id of the de-registered plugin.
	 */
	private final String pluginId;




	/**
	 * @param pluginId the id of the registered plugin
	 */
	public EventPluginRegistered(final String pluginId) {
		super(Channel.type(EventPluginRegistered.class));
		this.pluginId = pluginId;
	}

}
