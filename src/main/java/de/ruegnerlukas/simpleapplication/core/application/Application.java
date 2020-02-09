package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.callbacks.Callback;
import de.ruegnerlukas.simpleapplication.common.callbacks.EmptyCallback;
import de.ruegnerlukas.simpleapplication.common.events.FixedEventSource;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.ProviderService;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginService;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewService;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Application {


	/**
	 * The provider for the {@link PluginService}.
	 */
	private final Provider<PluginService> pluginServiceProvider = new Provider<>(PluginService.class);

	/**
	 * The provider for the {@link ViewService}.
	 */
	private final Provider<ViewService> viewServiceProvider = new Provider<>(ViewService.class);

	/**
	 * The provider for the {@link EventService}.
	 */
	private final Provider<EventService> eventServiceProvider = new Provider<>(EventService.class);

	/**
	 * The configuration of this application.
	 */
	private final ApplicationConfiguration configuration;

	/**
	 * The event when the javafx-application was started / when the {@link ViewService} is initialized.
	 */
	private final FixedEventSource<String> eventPresentationStarted = new FixedEventSource<>("");




	/**
	 * @param configuration the configuration of this application
	 */
	public Application(final ApplicationConfiguration configuration) {
		this.configuration = configuration;
	}




	/**
	 * Start the application.
	 */
	public void run() {
		log.info("Running application.");
		final Callback<Stage> startCallback = this::onStart;
		final EmptyCallback stopCallback = this::onStop;
		JFXApplication.start(startCallback, stopCallback);
	}




	/**
	 * Called when the javafx application was started.
	 *
	 * @param stage the primary stage
	 */
	private void onStart(final Stage stage) {
		log.info("Application on start.");
		setupProviderConfigurations();
		setupApplicationEvents();
		setupPlugins();
		setupViews(stage);
		log.info("Application started.");
	}




	/**
	 * Setup of the providers.
	 */
	private void setupProviderConfigurations() {
		log.info("Setup provider configurations.");
		final CoreProviderConfiguration coreConfig = new CoreProviderConfiguration();
		coreConfig.configure();
		coreConfig.getFactories().forEach(ProviderService::registerFactory);
		configuration.getProviderFactories().forEach(ProviderService::registerFactory);
	}




	/**
	 * Setup of the application events and {@link EventService}.
	 */
	private void setupApplicationEvents() {
		log.info("Setup events.");
		final EventService eventService = eventServiceProvider.get();
		eventService.registerEvent("presentation_initialized", eventPresentationStarted);
	}




	/**
	 * Setup and load the core plugins and the {@link PluginService}.
	 */
	private void setupPlugins() {
		log.info("Setup plugins.");
		final PluginService pluginService = pluginServiceProvider.get();
		configuration.getPlugins().forEach(pluginService::loadCorePlugin);
	}




	/**
	 * Setup the {@link ViewService}.
	 *
	 * @param stage the primary stage
	 */
	private void setupViews(final Stage stage) {
		log.info("Setup views.");
		final ViewService viewService = viewServiceProvider.get();
		viewService.initialize(stage, configuration.isShowViewAtStartup(), configuration.getView());
		eventPresentationStarted.trigger();
	}




	/**
	 * Called when the javafx application was stopped.
	 */
	private void onStop() {
		log.info("Application on stop.");
		final PluginService pluginService = pluginServiceProvider.get();
		configuration.getPlugins().forEach(pluginService::unloadCorePlugin);
		log.info("Application stopped.");
	}

}
