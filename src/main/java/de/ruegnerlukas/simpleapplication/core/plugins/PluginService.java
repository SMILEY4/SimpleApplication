package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class PluginService {


	/**
	 * All currently loaded ids.
	 */
	private Set<String> loadedIds = new HashSet<>();

	/**
	 * All currently loaded plugins.
	 */
	private Map<String, Plugin> loadedPlugins = new HashMap<>();




	/**
	 * Loads the given plugin as a core plugin.
	 *
	 * @param plugin the plugin to load
	 */
	public void loadCorePlugin(final Plugin plugin) {
		Validations.INPUT.notNull(plugin).exception("Plugin can not be null.");
		if (loadedPlugins.containsKey(plugin.getId())) {
			log.warn("Core plugin {} is already loaded and will not be loaded again.", plugin.getId());
		} else {
			loadedPlugins.put(plugin.getId(), plugin);
			loadedIds.add(plugin.getId());
			plugin.onLoad();
			log.info("Loaded plugin {}.", plugin.getId());
		}
	}




	/**
	 * Unloads the given (core) plugin.
	 *
	 * @param plugin the plugin to unload
	 */
	public void unloadCorePlugin(final Plugin plugin) {
		Validations.INPUT.notNull(plugin).exception("Plugin can not be null.");
		if (!loadedPlugins.containsKey(plugin.getId())) {
			log.warn("Core plugin {} is already unloaded and will not be unloaded again.", plugin.getId());
		} else {
			loadedPlugins.remove(plugin.getId());
			loadedIds.remove(plugin.getId());
			plugin.onUnload();
			log.info("Unloaded plugin {}.", plugin.getId());
		}
	}




	/**
	 * Checks if the given id is currently loaded.
	 *
	 * @param id the id to check
	 * @return whether the id is currently loaded.
	 */
	public boolean isLoaded(final String id) {
		return loadedIds.contains(id);
	}


}
