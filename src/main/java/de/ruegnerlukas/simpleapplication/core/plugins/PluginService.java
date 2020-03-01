package de.ruegnerlukas.simpleapplication.core.plugins;

import java.util.List;
import java.util.Optional;

/**
 * The plugin service is responsible for loading and unloading {@link Plugin}s.
 * <p>
 * A plugin can be registered and can have any amount of dependencies on other plugins or generic components.
 * Registered plugins can be loaded if all their dependencies are currently loaded.
 * Alternatively, a plugin can be loaded together with all its dependencies. Dependencies are loaded in the correct order.
 * If a plugin can be loaded without having to load dependencies can be checked with {@link PluginService#canLoadDirectly}.
 * <p>
 * Components can be loaded and unloaded in the same way.
 * However, components do not have any dependencies and are generally used by plugins to depend on.
 * Components can be points in the application where certain plugins can not be loaded before
 * (e.g. Plugin depends on a non-plugin-system to be loaded completely)
 * <p>
 * Plugins and components can be unloaded at any time.
 * When something get unloaded, all plugins that depend on it will be unloaded too.
 * If a plugin or component can be unloaded without
 * having to unload other plugins can be checked with {@link PluginService#canUnloadSafely}.
 */
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
	 * Loads a generic component with an id.
	 *
	 * @param id the id of the component
	 */
	void loadComponent(String id);


	/**
	 * Load the plugin with the given id. The plugin must be registered and all dependencies must be loaded.
	 *
	 * @param id the id of a plugin to load
	 */
	void loadPlugin(String id);


	/**
	 * Load the plugin with the given id and the (unloaded) dependencies. The plugin must be registered.
	 *
	 * @param id the id of a plugin to load
	 */
	void loadPluginWithDependencies(String id);


	/**
	 * Loads all currently registered plugins (if possible).
	 */
	void loadAllPlugins();


	/**
	 * Unloads a generic component with an id. This will also unload all plugins that depend in this component.
	 *
	 * @param id the id of the component
	 */
	void unloadComponent(String id);


	/**
	 * Unload the plugin with the given id. This will also unload all plugins that depend in this plugin.
	 *
	 * @param id the id of a plugin to unload
	 */
	void unloadPlugin(String id);


	/**
	 * Unloads all currently loaded plugins.
	 */
	void unloadAllPlugins();


	/**
	 * Check if the plugin with the given id can be loaded without having to load any dependencies.
	 *
	 * @param pluginId the id of the plugin to check
	 * @return if the plugin can be loaded without having to load any dependencies.
	 */
	boolean canLoadDirectly(String pluginId);


	/**
	 * Check if the plugin/component with the given id can be unloaded without having to unload
	 * any other plugins depending on it.
	 *
	 * @param id the id of the plugin/component to check
	 * @return whether the given plugin/component can be unloaded without having to unload other plugins.
	 */
	boolean canUnloadSafely(String id);


	/**
	 * Check if the plugin or dependency with the given id is loaded.
	 *
	 * @param id the id of the plugin or component.
	 * @return whether the id is loaded
	 */
	boolean isLoaded(String id);


	/**
	 * Finds the {@link Plugin} with the given id.
	 *
	 * @param id the id of the plugin
	 * @return the plugin with the id as an optional.
	 */
	Optional<Plugin> findById(String id);


}
