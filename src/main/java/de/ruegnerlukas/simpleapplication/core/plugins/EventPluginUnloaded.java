package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.Getter;

/**
 * The event when a plugin was unloaded.
 */
@Getter
public final class EventPluginUnloaded extends Publishable {


	/**
	 * The id of the unloaded plugin.
	 */
	private final String pluginId;




	/**
	 * @param pluginId the id of the unloaded plugin
	 */
	public EventPluginUnloaded(final String pluginId) {
		super(Channel.type(EventPluginUnloaded.class));
		this.pluginId = pluginId;
	}

}
