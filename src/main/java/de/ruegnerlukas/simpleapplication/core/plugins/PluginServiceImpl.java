package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
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
	private Set<IdEntry> loadedIds = new HashSet<>();


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
	public void loadComponent(final String id) {
		log.info("Loading component with the id {}.", id);
		if (isLoaded(id)) {
			log.warn("The component with the id {} is already loaded and will not be loaded again.", id);
		} else {
			loadedIds.add(IdEntry.ofComponent(id));
			log.info("The component with the id {} was loaded.", id);
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
			loadedIds.add(IdEntry.ofPlugin(plugin.getId()));
			plugin.onLoad();
			log.info("The plugin with the id {} was loaded.", plugin.getId());
		}
	}




	@Override
	public void loadAllPlugins() {
		registeredPlugins.keySet().forEach(this::loadPlugin);
	}




	@Override
	public void unloadComponent(final String id) {
		if (isLoaded(id)) {
			log.info("Unloading component with the id {}.", id);
			loadedIds.remove(IdEntry.ofComponent(id));
			log.info("The component with the id {} was unloaded.", id);
		} else {
			log.warn("The component with the id {} is already unloaded and will not be unloaded again.", id);
		}
	}




	@Override
	public void unloadPlugin(final String id) {
		Validations.PRESENCE.isTrue(isRegistered(id)).exception("The plugin with the id {} is not registered.", id);
		final Plugin plugin = registeredPlugins.get(id);
		if (isLoaded(plugin.getId())) {
			log.info("Unloading plugin with the id {}.", plugin.getId());
			loadedIds.remove(IdEntry.ofPlugin(plugin.getId()));
			plugin.onUnload();
			log.info("The plugin with the id {} was unloaded.", plugin.getId());
		} else {
			log.warn("The plugin with the id {} is already unloaded and will not be unloaded again.", plugin.getId());
		}
	}




	@Override
	public void unloadAllPlugins() {
		final List<String> pluginIds = loadedIds
				.stream()
				.filter(IdEntry::isPlugin)
				.map(IdEntry::getId)
				.collect(Collectors.toList());
		pluginIds.forEach(this::unloadPlugin);
	}




	@Override
	public boolean isLoaded(final String id) {
		return loadedIds.contains(IdEntry.ofComponent(id));
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




	@Getter
	@AllArgsConstructor
	@EqualsAndHashCode (exclude = {"isPlugin"})
	private static class IdEntry {


		/**
		 * Creates a new entry for a component.
		 *
		 * @param id the id of the component
		 * @return the created {@link IdEntry}
		 */
		public static IdEntry ofComponent(final String id) {
			return new IdEntry(id, false);
		}




		/**
		 * Creates a new entry for a plugin.
		 *
		 * @param id the id of the plugin
		 * @return the created {@link IdEntry}
		 */
		public static IdEntry ofPlugin(final String id) {
			return new IdEntry(id, true);
		}




		/**
		 * The id of the plugin or component.
		 */
		private final String id;

		/**
		 * Whether it is a plugin or a component.
		 */
		private final boolean isPlugin;


	}

}
