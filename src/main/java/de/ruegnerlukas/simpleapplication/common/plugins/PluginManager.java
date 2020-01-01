package de.ruegnerlukas.simpleapplication.common.plugins;

import java.util.HashMap;
import java.util.Map;

public class PluginManager {


	/**
	 * All registered plugins.
	 */
	private Map<String, Plugin> plugins = new HashMap<>();




	/**
	 * Registers the given plugin.
	 *
	 * @param plugin the plugin
	 */
	public void register(final Plugin plugin) {
		plugins.put(plugin.getId(), plugin);
	}



}
