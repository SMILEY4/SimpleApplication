package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.callbacks.Callback;
import de.ruegnerlukas.simpleapplication.common.callbacks.EmptyCallback;
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
	 * The starter for the {@link JFXApplication}.
	 */
	private JFXApplication.JFXStarter jfxStarter;

	/**
	 * The core provider configuration.
	 */
	private CoreProviderConfiguration coreProviderConfiguration;




	/**
	 * @param configuration the configuration of this application
	 */
	public Application(final ApplicationConfiguration configuration) {
		this.configuration = configuration;
		setJFXApplicationStarter(new JFXApplication.JFXStarter());
		setCoreProviderConfiguration(new CoreProviderConfiguration());
	}




	/**
	 * Start the application.
	 */
	public void run() {
		log.info("Running application.");
		final Callback<Stage> startCallback = this::onStart;
		final EmptyCallback stopCallback = this::onStop;
		jfxStarter.start(startCallback, stopCallback);
	}




	/**
	 * Called when the javafx application was started.
	 *
	 * @param stage the primary stage
	 */
	private void onStart(final Stage stage) {
		log.info("Application on start.");
		setupProviderConfigurations();
		setupPlugins();
		setupViews(stage);
		log.info("Application started.");
		eventServiceProvider.get().publish(ApplicationConstants.EVENT_APPLICATION_STARTED);
	}




	/**
	 * Setup of the providers.
	 */
	private void setupProviderConfigurations() {
		log.info("Setup provider configurations.");
		coreProviderConfiguration.configure();
		coreProviderConfiguration.getFactories().forEach(ProviderService::registerFactory);
		configuration.getProviderFactories().forEach(ProviderService::registerFactory);
	}




	/**
	 * Setup and load the core plugins and the {@link PluginService}.
	 */
	private void setupPlugins() {
		log.info("Setup plugins.");
		final PluginService pluginService = pluginServiceProvider.get();
		configuration.getPlugins().forEach(plugin -> {
			pluginService.registerPlugin(plugin);
			pluginService.loadPlugin(plugin.getId());
		});
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
		eventServiceProvider.get().publish(ApplicationConstants.EVENT_PRESENTATION_INITIALIZED);
	}




	/**
	 * Called when the javafx application was stopped.
	 */
	private void onStop() {
		log.info("Application on stop.");
		eventServiceProvider.get().publish(ApplicationConstants.EVENT_APPLICATION_STOPPING);
		final PluginService pluginService = pluginServiceProvider.get();
		configuration.getPlugins().forEach(plugin -> {
			pluginService.unloadPlugin(plugin.getId());
			pluginService.deregisterPlugin(plugin.getId());
		});
		ProviderService.cleanup();
		log.info("Application stopped.");
	}




	/**
	 * Set the starter for the {@link JFXApplication}. Usually only for internal purposes.
	 *
	 * @param starter the starter
	 */
	public void setJFXApplicationStarter(final JFXApplication.JFXStarter starter) {
		this.jfxStarter = starter;
	}




	/**
	 * Set the core provider configuration. Usually only for internal purposes.
	 *
	 * @param config the configuration.
	 */
	public void setCoreProviderConfiguration(final CoreProviderConfiguration config) {
		this.coreProviderConfiguration = config;
	}


}
