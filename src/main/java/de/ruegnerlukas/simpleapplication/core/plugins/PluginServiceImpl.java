package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class PluginServiceImpl implements PluginService {


	/**
	 * All currently loaded ids.
	 */
	private Set<String> loadedIds = new HashSet<>();


	/**
	 * All currently registered plugins. The key is the id of the plugin.
	 */
	private Map<String, Plugin> registeredPlugins = new HashMap<>();




	@Override
	public void registerPlugins(final List<Plugin> plugins) {
		plugins.forEach(this::registerPlugin);
	}




	@Override
	public void registerPlugin(final Plugin plugin) {
		Validations.INPUT.notNull(plugin).exception("The plugin may not be null.");
		if (isRegistered(plugin.getId())) {
			log.warn("The plugin with the id {} is already registered and will not be registered again.", plugin.getId());
		} else {
			registeredPlugins.put(plugin.getId(), plugin);
			log.info("Successfully registered plugin with the id {}.", plugin.getId());
		}
	}




	@Override
	public void deregisterPlugin(final String id) {
		Validations.STATE.isFalse(isLoaded(id)).exception("The plugin {} can not be deregistered while it is loaded.", id);
		if (isRegistered(id)) {
			registeredPlugins.remove(id);
			log.info("Successfully de-registered plugin with the id {}.", id);
		}
	}




	@Override
	public void loadPlugin(final String id) {
		Validations.PRESENCE.isTrue(isRegistered(id)).exception("The plugin with the id {} is not registered.", id);
		final Plugin plugin = registeredPlugins.get(id);
		log.info("Loading plugin with the id {}.", plugin.getId());
		if (isLoaded(plugin.getId())) {
			log.warn("The plugin with the id {} is already loaded and will not be loaded again.", plugin.getId());
		} else {
			loadedIds.add(plugin.getId());
			plugin.onLoad();
			log.info("The plugin with the id {} was loaded.", plugin.getId());
		}
	}




	/**
	 * Loads all currently registered plugins (if possible).
	 */
	@Override
	public void loadAllPlugins() {
		registeredPlugins.keySet().forEach(this::loadPlugin);
	}




	@Override
	public void unloadPlugin(final String id) {
		Validations.PRESENCE.isTrue(isRegistered(id)).exception("The plugin with the id {} is not registered.", id);
		final Plugin plugin = registeredPlugins.get(id);
		if (isLoaded(plugin.getId())) {
			log.info("Unloading plugin with the id {}.", plugin.getId());
			loadedIds.remove(plugin.getId());
			plugin.onUnload();
			log.info("The plugin with the id {} was unloaded.", plugin.getId());
		} else {
			log.warn("The plugin with the id {} is already unloaded and will not be unloaded again.", plugin.getId());
		}
	}




	/**
	 * Unloads all currently loaded plugins
	 */
	@Override
	public void unloadAllPlugins() {
		final List<String> pluginIds = loadedIds
				.stream()
				.filter(id -> registeredPlugins.containsKey(id))
				.collect(Collectors.toList());
		pluginIds.forEach(this::unloadPlugin);
	}




	@Override
	public boolean isLoaded(final String id) {
		return loadedIds.contains(id);
	}




	/**
	 * Checks whether the plugin with the given id is registered.
	 *
	 * @param id the id of the plugin.
	 * @return whether the plugin is registered.
	 */
	private boolean isRegistered(final String id) {
		return registeredPlugins.containsKey(id);
	}

}
