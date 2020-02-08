package de.ruegnerlukas.simpleapplication.core.plugins;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class PluginFinder {


	/**
	 * The list of all plugins.
	 */
	private List<Plugin> plugins = new ArrayList<>();




	/**
	 * Adds the given plugin to the list of plugins.
	 *
	 * @param plugin the plugin to add
	 */
	protected void add(final Plugin plugin) {
		plugins.add(plugin);
	}




	/**
	 * Called when the application is ready to load plugins. Add all plugins here.
	 */
	public abstract void findPlugins();




	/**
	 * @return an unmodifiable list of all added plugins
	 */
	public List<Plugin> getPlugins() {
		return Collections.unmodifiableList(plugins);
	}

}
