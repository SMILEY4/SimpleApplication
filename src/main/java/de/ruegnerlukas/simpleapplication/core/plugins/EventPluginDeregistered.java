package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.Getter;

/**
 * The event when a new plugin was deregistered.
 */
@Getter
public final class EventPluginDeregistered extends Publishable {


	/**
	 * The id of the de-registered plugin.
	 */
	private final String pluginId;




	/**
	 * @param pluginId the id of the de-registered plugin
	 */
	public EventPluginDeregistered(final String pluginId) {
		super(Channel.type(EventPluginDeregistered.class));
		this.pluginId = pluginId;
	}

}
