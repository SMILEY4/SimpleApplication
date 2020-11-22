package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.common.eventbus.EventBus;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.application.ApplicationConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class PluginServiceImpl implements PluginService {


	/**
	 * The provider for the {@link EventBus}.
	 */
	private final Provider<EventBus> eventBusProvider = new Provider<>(EventBus.class);

	/**
	 * All currently loaded ids as a graph.
	 */
	private final DependencyGraph graph = new DependencyGraph();


	/**
	 * All currently registered plugins. The key is the id of the plugin.
	 */
	private final Map<String, Plugin> registeredPlugins = new HashMap<>();


	/**
	 * All plugins that are marked as autoload. Autoload-plugins get automatically loaded when all their dependencies are loaded.
	 */
	private final List<Plugin> autoloadPlugins = new ArrayList<>();




	@Override
	public void registerPlugins(final List<Plugin> plugins) {
		plugins.forEach(this::registerPlugin);
	}




	@Override
	public void registerPlugin(final Plugin plugin) {
		Validations.INPUT.notNull(plugin).exception("The plugin may not be null.");
		if (isRegistered(plugin.getId())) {
			log.debug("The plugin with the id '{}' is already registered and will not be registered again.", plugin.getId());
		} else {
			registeredPlugins.put(plugin.getId(), plugin);
			if (plugin.getInformation().isAutoload() && !plugin.getInformation().getDependencyIds().isEmpty()) {
				autoloadPlugins.add(plugin);
			}
			graph.insert(plugin.getId());
			plugin.getInformation().getDependencyIds().forEach(dependency -> {
				if (!graph.exists(dependency)) {
					graph.insert(dependency);
				}
				graph.addDependency(plugin.getId(), dependency);
			});
			eventBusProvider.get().publish(ApplicationConstants.EVENT_PLUGIN_REGISTERED_TAGS, new EventPluginRegistered(plugin.getId()));
			log.info("Successfully registered plugin with the id '{}'.", plugin.getId());
		}
	}




	@Override
	public void deregisterPlugin(final String id) {
		Validations.STATE.isFalse(isLoaded(id)).exception("The plugin '{}' can not be de-registered while it is loaded.", id);
		if (isRegistered(id)) {
			registeredPlugins.remove(id);
			eventBusProvider.get().publish(ApplicationConstants.EVENT_PLUGIN_DEREGISTERED_TAGS, new EventPluginDeregistered(id));
			log.info("Successfully de-registered plugin with the id '{}'.", id);
		}
	}




	@Override
	public void loadComponent(final String id) {
		if (isLoaded(id)) {
			log.debug("The component with the id '{}' is already loaded and will not be loaded again.", id);
		} else {
			log.debug("Loading component with the id '{}'.", id);
			if (!graph.exists(id)) {
				graph.insert(id);
			}
			graph.setLoaded(id);
			eventBusProvider.get().publish(ApplicationConstants.EVENT_COMPONENT_LOADED_TAGS, new EventComponentLoaded(id));
			log.info("The component with the id '{}' was loaded.", id);
			checkAutoloadPlugins();
		}
	}




	@Override
	public void loadPlugin(final String id) {
		Validations.PRESENCE.isTrue(isRegistered(id)).exception("The plugin with the id '{}' is not registered.", id);
		final Plugin plugin = registeredPlugins.get(id);
		if (isLoaded(plugin.getId())) {
			log.debug("The plugin with the id '{}' is already loaded and will not be loaded again.", plugin.getId());
		} else {
			log.debug("Loading plugin with the id '{}'.", plugin.getId());
			if (canLoadDirectly(plugin.getId())) {
				forceLoadPlugin(plugin);
				log.info("The plugin with the id '{}' was loaded.", plugin.getId());
				checkAutoloadPlugins();
			} else {
				log.warn("The plugin with the id '{}' could not be loaded: missing dependencies.", plugin.getId());
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
		eventBusProvider.get().publish(ApplicationConstants.EVENT_PLUGIN_LOADED_TAGS, new EventPluginLoaded(plugin.getId()));
	}




	@Override
	public void loadPluginWithDependencies(final String id) {
		Validations.PRESENCE.isTrue(isRegistered(id)).exception("The plugin with the id '{}' is not registered.", id);
		final Plugin plugin = registeredPlugins.get(id);
		if (isLoaded(plugin.getId())) {
			log.debug("The plugin with the id '{}' is already loaded and will not be loaded again.", plugin.getId());
		} else {
			log.debug("Loading plugin (including dependencies) with the id '{}'.", plugin.getId());
			final List<String> dependencies = graph.getDependenciesIndirect(plugin.getId());
			Collections.reverse(dependencies);
			for (String dependency : dependencies) {
				if (isRegistered(dependency) && !isLoaded(dependency)) {
					loadPlugin(dependency);
				}
			}
			if (canLoadDirectly(plugin.getId())) {
				if (!isLoaded(plugin.getId())) {
					forceLoadPlugin(plugin);
					log.info("The plugin with the id '{}' was loaded.", plugin.getId());
				}
			} else {
				log.warn("The plugin with the id '{}' could not be loaded.", plugin.getId());
			}
		}
	}




	@Override
	public void loadAllPlugins() {
		registeredPlugins.keySet().forEach(this::loadPluginWithDependencies);
	}




	@Override
	public void unloadComponent(final String id) {
		if (isLoaded(id)) {
			log.debug("Unloading component with the id '{}'.", id);
			final List<String> dependOn = graph.getDependsOnIndirect(id);
			Collections.reverse(dependOn);
			for (String dependency : dependOn) {
				if (!isLoaded(dependency)) {
					if (isRegistered(dependency)) {
						log.debug("Unloading dependency (plugin) with the id '{}'.", dependency);
						forceUnloadPlugin(registeredPlugins.get(dependency));
					} else {
						log.debug("Unloading dependency (component) with the id '{}'.", dependency);
						forceUnloadComponent(dependency);
					}
				}
			}
			forceUnloadComponent(id);
			log.info("The component with the id '{}' was unloaded.", id);
		} else {
			log.warn("The component with the id '{}' is already unloaded and will not be unloaded again.", id);
		}
	}




	/**
	 * Unloads the component without any additional checks.
	 *
	 * @param id the id of the component to unload
	 */
	private void forceUnloadComponent(final String id) {
		graph.setUnloaded(id);
		eventBusProvider.get().publish(ApplicationConstants.EVENT_COMPONENT_UNLOADED_TAGS, new EventComponentUnloaded(id));
	}




	@Override
	public void unloadPlugin(final String id) {
		Validations.PRESENCE.isTrue(isRegistered(id)).exception("The plugin with the id '{}' is not registered.", id);
		final Plugin plugin = registeredPlugins.get(id);
		if (isLoaded(plugin.getId())) {
			log.debug("Unloading plugin with the id '{}'.", plugin.getId());
			final List<String> dependOn = graph.getDependsOnIndirect(plugin.getId());
			Collections.reverse(dependOn);
			for (String dependency : dependOn) {
				if (isLoaded(dependency)) {
					if (isRegistered(dependency)) {
						log.debug("Unloading dependency (plugin) with the id '{}'.", dependency);
						forceUnloadPlugin(registeredPlugins.get(dependency));
					} else {
						log.debug("Unloading dependency (component) with the id '{}'.", dependency);
						forceUnloadComponent(dependency);
					}
				}
			}
			forceUnloadPlugin(plugin);
			log.info("The plugin with the id '{}' was unloaded.", plugin.getId());
		} else {
			log.warn("The plugin with the id '{}' is already unloaded and will not be unloaded again.", plugin.getId());
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
		eventBusProvider.get().publish(ApplicationConstants.EVENT_PLUGIN_UNLOADED_TAGS, new EventPluginUnloaded(plugin.getId()));
	}




	/**
	 * Checks whether any plugins marked as 'autoload' can be loaded.
	 */
	private void checkAutoloadPlugins() {
		autoloadPlugins.stream()
				.map(Plugin::getId)
				.filter(this::canLoadDirectly)
				.filter(this::isUnloaded)
				.forEach(this::loadPlugin);
	}




	@Override
	public void unloadAllPlugins() {
		graph.getIds().stream()
				.filter(registeredPlugins::containsKey)
				.forEach(this::unloadPlugin);
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
	public boolean isUnloaded(final String id) {
		return !isLoaded(id);
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
