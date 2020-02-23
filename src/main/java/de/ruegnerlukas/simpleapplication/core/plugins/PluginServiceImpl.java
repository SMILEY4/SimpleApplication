package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.common.events.EventPackage;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.application.ApplicationConstants;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class PluginServiceImpl implements PluginService {


	/**
	 * The provider for the {@link EventService}.
	 */
	private final Provider<EventService> eventServiceProvider = new Provider<>(EventService.class);

	/**
	 * All currently loaded ids as a graph.
	 */
	private final DependencyGraph graph = new DependencyGraph();


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
			graph.insert(plugin.getId());
			plugin.getDependencyIds().forEach(dependency -> {
				if (!graph.exists(dependency)) {
					graph.insert(dependency);
				}
				graph.addDependency(plugin.getId(), dependency);
			});
			eventServiceProvider.get().publish(
					ApplicationConstants.EVENT_PLUGIN_REGISTERED, new EventPackage<>(plugin.getId()));
			log.info("Successfully registered plugin with the id {}.", plugin.getId());
		}
	}




	@Override
	public void deregisterPlugin(final String id) {
		Validations.STATE.isFalse(isLoaded(id)).exception("The plugin {} can not be deregistered while it is loaded.", id);
		if (isRegistered(id)) {
			registeredPlugins.remove(id);
			eventServiceProvider.get().publish(ApplicationConstants.EVENT_PLUGIN_DEREGISTERED, new EventPackage<>(id));
			log.info("Successfully de-registered plugin with the id {}.", id);
		}
	}




	@Override
	public void loadComponent(final String id) {
		if (isLoaded(id)) {
			log.warn("The component with the id {} is already loaded and will not be loaded again.", id);
		} else {
			log.info("Loading component with the id {}.", id);
			if (!graph.exists(id)) {
				graph.insert(id);
			}
			graph.setLoaded(id);
			eventServiceProvider.get().publish(ApplicationConstants.EVENT_COMPONENT_LOADED, new EventPackage<>(id));
			log.info("The component with the id {} was loaded.", id);
		}
	}




	@Override
	public void loadPlugin(final String id) {
		Validations.PRESENCE.isTrue(isRegistered(id)).exception("The plugin with the id {} is not registered.", id);
		final Plugin plugin = registeredPlugins.get(id);
		if (isLoaded(plugin.getId())) {
			log.warn("The plugin with the id {} is already loaded and will not be loaded again.", plugin.getId());
		} else {
			log.info("Loading plugin with the id {}.", plugin.getId());
			if (canLoadDirectly(plugin.getId())) {
				forceLoadPlugin(plugin);
				log.info("The plugin with the id {} was loaded.", plugin.getId());
			} else {
				log.warn("The plugin with the id {} could not be loaded: missing dependencies.", plugin.getId());
			}
		}
	}




	/**
	 * Loads the plugin without any additional checks.
	 *
	 * @param plugin the plugin to load
	 */
	private void forceLoadPlugin(final Plugin plugin) {
		graph.setLoaded(plugin.getId());
		plugin.onLoad();
		eventServiceProvider.get().publish(ApplicationConstants.EVENT_PLUGIN_LOADED, new EventPackage<>(plugin.getId()));
	}




	@Override
	public void loadPluginWithDependencies(final String id) {
		Validations.PRESENCE.isTrue(isRegistered(id)).exception("The plugin with the id {} is not registered.", id);
		final Plugin plugin = registeredPlugins.get(id);
		if (isLoaded(plugin.getId())) {
			log.warn("The plugin with the id {} is already loaded and will not be loaded again.", plugin.getId());
		} else {
			log.info("Loading plugin (including dependencies) with the id {}.", plugin.getId());
			final List<String> dependencies = graph.getDependenciesIndirect(plugin.getId());
			Collections.reverse(dependencies);
			for (String dependency : dependencies) {
				if (!isLoaded(dependency)) {
					loadPlugin(dependency);
				}
			}
			forceLoadPlugin(plugin);
			log.info("The plugin with the id {} was loaded.", plugin.getId());
		}
	}




	@Override
	public void loadAllPlugins() {
		registeredPlugins.keySet().forEach(this::loadPluginWithDependencies);
	}




	@Override
	public void unloadComponent(final String id) {
		if (isLoaded(id)) {
			log.info("Unloading component with the id {}.", id);
			final List<String> dependOn = graph.getDependsOnIndirect(id);
			Collections.reverse(dependOn);
			for (String dependency : dependOn) {
				if (!isLoaded(dependency)) {
					if (isRegistered(dependency)) {
						log.info("Unloading plugin with the id {}.", dependency);
						forceUnloadPlugin(registeredPlugins.get(dependency));
					} else {
						log.info("Unloading component with the id {}.", dependency);
						forceUnloadComponent(dependency);
					}
				}
			}
			forceUnloadComponent(id);
			log.info("The component with the id {} was unloaded.", id);
		} else {
			log.warn("The component with the id {} is already unloaded and will not be unloaded again.", id);
		}
	}




	/**
	 * Unloads the component without any additional checks.
	 *
	 * @param id the id of the component to unload
	 */
	private void forceUnloadComponent(final String id) {
		graph.setUnloaded(id);
		eventServiceProvider.get().publish(ApplicationConstants.EVENT_COMPONENT_UNLOADED, new EventPackage<>(id));
	}




	@Override
	public void unloadPlugin(final String id) {
		Validations.PRESENCE.isTrue(isRegistered(id)).exception("The plugin with the id {} is not registered.", id);
		final Plugin plugin = registeredPlugins.get(id);
		if (isLoaded(plugin.getId())) {
			log.info("Unloading plugin with the id {}.", plugin.getId());
			final List<String> dependOn = graph.getDependsOnIndirect(plugin.getId());
			Collections.reverse(dependOn);
			for (String dependency : dependOn) {
				if (isLoaded(dependency)) {
					if (isRegistered(dependency)) {
						log.info("Unloading plugin with the id {}.", dependency);
						forceUnloadPlugin(registeredPlugins.get(dependency));
					} else {
						log.info("Unloading component with the id {}.", dependency);
						forceUnloadComponent(dependency);
					}
				}
			}
			forceUnloadPlugin(plugin);
			log.info("The plugin with the id {} was unloaded.", plugin.getId());
		} else {
			log.warn("The plugin with the id {} is already unloaded and will not be unloaded again.", plugin.getId());
		}
	}




	/**
	 * Unloads the plugin without any additional checks.
	 *
	 * @param plugin the plugin to unload
	 */
	private void forceUnloadPlugin(final Plugin plugin) {
		graph.setUnloaded(plugin.getId());
		plugin.onUnload();
		eventServiceProvider.get().publish(ApplicationConstants.EVENT_PLUGIN_UNLOADED, new EventPackage<>(plugin.getId()));
	}




	@Override
	public void unloadAllPlugins() {
		graph.getIds().stream().filter(id -> registeredPlugins.containsKey(id)).forEach(this::unloadPlugin);
	}




	@Override
	public boolean canLoadDirectly(final String pluginId) {
		return graph.exists(pluginId) && graph.getDependenciesIndirect(pluginId).stream().allMatch(graph::isLoaded);
	}




	@Override
	public boolean canUnloadSafely(final String id) {
		return graph.getDependsOnIndirect(id).stream().noneMatch(graph::isLoaded);
	}




	@Override
	public boolean isLoaded(final String id) {
		return graph.isLoaded(id);
	}




	@Override
	public Optional<Plugin> findById(final String id) {
		return Optional.ofNullable(registeredPlugins.get(id));
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
