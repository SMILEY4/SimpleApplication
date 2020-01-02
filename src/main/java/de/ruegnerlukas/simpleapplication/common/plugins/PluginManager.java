package de.ruegnerlukas.simpleapplication.common.plugins;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class PluginManager {


	/**
	 * All registered plugins.
	 */
	private Map<String, Plugin> plugins = new HashMap<>();

	/**
	 * All ids of loaded plugins/systems.
	 */
	private Set<String> loadedIds = new HashSet<>();

	/**
	 * All ids of unloaded plugins/systems.
	 */
	private Set<String> unloadedIds = new HashSet<>();




	/**
	 * Registers and loads the given plugin.
	 *
	 * @param plugin the plugin
	 */
	public void registerAndLoad(final Plugin plugin) {
		register(plugin);
		load(plugin, true);
	}




	/**
	 * Registers the given plugin.
	 *
	 * @param plugin the plugin
	 */
	public void register(final Plugin plugin) {
		Validations.STATE.containsNot(plugins.keySet(), plugin.getId(), "Plugin {} is already registered.", plugin.getId());
		log.info("Registered plugin {} ({}).", plugin.getId(), plugin.getVersion());
		plugins.put(plugin.getId(), plugin);
		unloadedIds.add(plugin.getId());
	}




	/**
	 * Loads the plugin/system with the given id.
	 *
	 * @param id the id to load
	 */
	public void load(final String id) {
		Validations.STATE.containsNot(loadedIds, id, "Plugin/System with id {} is already loaded.", id);
		if (plugins.containsKey(id)) {
			final Plugin plugin = plugins.get(id);
			load(plugin, true);
		} else {
			setIdState(id, true);
			log.info("System with id {} loaded.", id);
			checkAndLoadUnloadedPlugins();
		}
	}




	/**
	 * Unloads the plugin/system with the given id.
	 *
	 * @param id the id to unload
	 */
	public void unload(final String id) {
		Validations.STATE.containsNot(unloadedIds, id, "Plugin/System with id {} is already unloaded.", id);
		if (loadedIds.contains(id)) {
			if (plugins.containsKey(id)) {
				final Plugin plugin = plugins.get(id);
				unload(plugin, true);
			} else {
				setIdState(id, false);
				log.info("System with id {} unloaded.", id);
				checkAndUnloadLoadedPlugins();
			}
		}
	}




	/**
	 * Loads the given plugin.
	 *
	 * @param plugin the plugin to load
	 */
	private void load(final Plugin plugin, final boolean checkUnloaded) {
		if (areLoaded(plugin.getPluginDependencies())) {
			final boolean loaded = plugin.onLoad();
			if (loaded) {
				setIdState(plugin.getId(), true);
				log.info("Loaded plugin {} ({}).", plugin.getId(), plugin.getVersion());
				if (checkUnloaded) {
					checkAndLoadUnloadedPlugins();
				}
			} else {
				log.warn("Failed to load plugin {} ({}).", plugin.getId(), plugin.getVersion());
			}
		}
	}




	/**
	 * Unloads the given plugin.
	 *
	 * @param plugin the plugin to unload
	 */
	private void unload(final Plugin plugin, final boolean checkLoaded) {
		plugin.onUnload();
		setIdState(plugin.getId(), false);
		log.info("Unloaded plugin {} ({}).", plugin.getId(), plugin.getVersion());
		if (checkLoaded) {
			checkAndUnloadLoadedPlugins();
		}
	}




	/**
	 * Checks unloaded plugins if they can be loaded (and loads them if possible).
	 */
	private void checkAndLoadUnloadedPlugins() {

		final List<String> unloadedCopy = new ArrayList<>(unloadedIds);
		boolean loadedPlugins = true;

		while (!unloadedCopy.isEmpty() && loadedPlugins) {

			final List<Plugin> loadablePlugins = new LinkedList<>();
			for (String unloadedId : unloadedCopy) {
				final Plugin unloadedPlugin = plugins.get(unloadedId);
				if (unloadedPlugin != null && satisfiedDependencies(unloadedPlugin)) {
					loadablePlugins.add(unloadedPlugin);
				}
			}

			for (Plugin plugin : loadablePlugins) {
				load(plugin, false);
			}

			loadedPlugins = !loadablePlugins.isEmpty();
			unloadedCopy.clear();
			unloadedCopy.addAll(unloadedIds);
		}

	}




	/**
	 * Checks loaded plugins if they must be unloaded (and unloads them).
	 */
	private void checkAndUnloadLoadedPlugins() {

		final List<String> loadedCopy = new ArrayList<>(loadedIds);
		boolean unloadedPlugins = true;

		while (!loadedCopy.isEmpty() && unloadedPlugins) {

			final List<Plugin> unloadablePlugins = new LinkedList<>();
			for (String loaded : loadedCopy) {
				final Plugin loadedPlugin = plugins.get(loaded);
				if (loadedPlugin != null && !satisfiedDependencies(loadedPlugin)) {
					unloadablePlugins.add(loadedPlugin);
				}
			}

			for (Plugin plugin : unloadablePlugins) {
				unload(plugin, false);
			}

			unloadedPlugins = !unloadablePlugins.isEmpty();
			loadedCopy.clear();
			loadedCopy.addAll(loadedIds);
		}

	}




	/**
	 * Sets the state of the given id to loaded or unloaded.
	 *
	 * @param id     the id of the system/plugin
	 * @param loaded whether the system/plugin is loaded
	 */
	private void setIdState(final String id, final boolean loaded) {
		if (loaded) {
			loadedIds.add(id);
			unloadedIds.remove(id);
		} else {
			loadedIds.remove(id);
			unloadedIds.add(id);
		}
	}




	/**
	 * Check the given plugin dependencies are loaded.
	 *
	 * @param plugin the plugin to check
	 * @return whether the dependencies of the plugin are loaded.
	 */
	private boolean satisfiedDependencies(final Plugin plugin) {
		for (String dep : plugin.getPluginDependencies()) {
			if (!loadedIds.contains(dep)) {
				return false;
			}
		}
		return true;
	}




	/**
	 * @return a list of the ids of all currently loaded plugins/systems
	 */
	public List<String> getLoadedIds() {
		return new ArrayList<>(loadedIds);
	}




	/**
	 * Check if the plugin/system with the given id is loaded.
	 *
	 * @param id the id of the plugin/system
	 * @return true, if it is loaded
	 */
	public boolean isLoaded(final String id) {
		return loadedIds.contains(id);
	}




	/**
	 * Check if the plugins/systems with the given ids are loaded.
	 *
	 * @param ids the ids of the plugins/systems
	 * @return true, if they are loaded
	 */
	private boolean areLoaded(final String... ids) {
		for (String id : ids) {
			if (!isLoaded(id)) {
				return false;
			}
		}
		return true;
	}

}
