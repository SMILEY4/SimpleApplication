package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
public class PluginServiceImpl implements PluginService {


	/**
	 * All currently loaded ids.
	 */
	private Set<String> loadedIds = new HashSet<>();

	/**
	 * All currently loaded plugins.
	 */
	private Map<String, Plugin> loadedPlugins = new HashMap<>();




	@Override
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




	@Override
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




	@Override
	public boolean isLoaded(final String id) {
		return loadedIds.contains(id);
	}


}
