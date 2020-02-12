package de.ruegnerlukas.simpleapplication.core.plugins;

public interface PluginService {


	/**
	 * Loads the given plugin as a core plugin.
	 *
	 * @param plugin the plugin to load
	 */
	void loadCorePlugin(Plugin plugin);


	/**
	 * Unloads the given (core) plugin.
	 *
	 * @param plugin the plugin to unload
	 */
	void unloadCorePlugin(Plugin plugin);


	/**
	 * Checks if the given id is currently loaded.
	 *
	 * @param id the id to check
	 * @return whether the id is currently loaded.
	 */
	boolean isLoaded(String id);

}
