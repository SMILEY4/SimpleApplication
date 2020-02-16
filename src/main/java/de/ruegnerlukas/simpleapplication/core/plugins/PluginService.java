package de.ruegnerlukas.simpleapplication.core.plugins;

import java.util.List;

public interface PluginService {


	/**
	 * Register the given plugins.
	 *
	 * @param plugins the list of plugins to register
	 */
	void registerPlugins(List<Plugin> plugins);


	/**
	 * Register the given plugin.
	 *
	 * @param plugin the plugin to register
	 */
	void registerPlugin(Plugin plugin);


	/**
	 * Deregister the given plugin. The plugin may not be loaded.
	 *
	 * @param id the id of the plugin to deregister
	 */
	void deregisterPlugin(String id);


	/**
	 * Load the plugin with the given id. The plugin must be registered.
	 *
	 * @param id the id of a plugin to load
	 */
	void loadPlugin(String id);


	/**
	 * Loads all currently registered plugins (if possible).
	 */
	void loadAllPlugins();


	/**
	 * Unload the plugin with the given id.
	 *
	 * @param id the id of a plugin to unload
	 */
	void unloadPlugin(String id);


	/**
	 * Unloads all currently loaded plugins
	 */
	void unloadAllPlugins();


	/**
	 * Check if the plugin or dependency with the given id is loaded.
	 *
	 * @param id the id of the plugin or dependency.
	 * @return whether the id is loaded
	 */
	boolean isLoaded(String id);


}
