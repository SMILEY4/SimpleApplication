package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.callbacks.Callback;
import de.ruegnerlukas.simpleapplication.common.callbacks.EmptyCallback;
import de.ruegnerlukas.simpleapplication.common.events.FixedEventSource;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.GenericFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.ProviderService;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginFinder;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginService;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewService;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {


	private final Provider<PluginService> pluginServiceProvider = new Provider<>(PluginService.class);
	private final Provider<ViewService> viewServiceProvider = new Provider<>(ViewService.class);
	private final Provider<EventService> eventServiceProvider = new Provider<>(EventService.class);

	private final PluginFinder pluginFinder;

	private final FixedEventSource<String> eventPresentationStarted = new FixedEventSource<>("");




	public Application(final PluginFinder corePluginFinder) {
		this.pluginFinder = corePluginFinder;
	}




	public void run() {
		log.info("Running application.");
		final Callback<Stage> startCallback = this::onStart;
		final EmptyCallback stopCallback = this::onStop;
		JFXApplication.start(startCallback, stopCallback);
	}




	private void onStart(final Stage stage) {
		log.info("Application on start.");
		setupProviderConfigurations();
		setupApplicationEvents();
		setupPlugins();
		setupViews(stage);
		log.info("Application started.");
	}




	private void setupProviderConfigurations() {
		log.info("Setup provider configurations.");
		final CoreProviderConfiguration coreConfig = new CoreProviderConfiguration();
		coreConfig.configure();
		for (GenericFactory<?, ?> factory : coreConfig.getFactories()) {
			ProviderService.registerFactory(factory);
		}
	}




	private void setupApplicationEvents() {
		log.info("Setup events.");
		final EventService eventService = eventServiceProvider.get();
		eventService.addEvent("presentation_initialized", eventPresentationStarted);
	}




	private void setupPlugins() {
		log.info("Setup plugins.");
		final PluginService pluginService = pluginServiceProvider.get();
		pluginFinder.findPlugins();
		for (Plugin plugin : pluginFinder.getPlugins()) {
			pluginService.loadCorePlugin(plugin);
		}
	}




	private void setupViews(final Stage stage) {
		log.info("Setup views.");
		final ViewService viewService = viewServiceProvider.get();
		viewService.initializePrimary(stage);
		eventPresentationStarted.trigger();
	}




	private void onStop() {
		log.info("Application on stop.");
		final PluginService pluginService = pluginServiceProvider.get();
		for (Plugin plugin : pluginFinder.getPlugins()) {
			pluginService.unloadCorePlugin(plugin);
		}
		log.info("Application stopped.");
	}

}
