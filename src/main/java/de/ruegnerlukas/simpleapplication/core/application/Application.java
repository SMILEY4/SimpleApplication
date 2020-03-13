package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.callbacks.Callback;
import de.ruegnerlukas.simpleapplication.common.callbacks.EmptyCallback;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.InstanceFactory;
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
		registerPrimaryStageProvider(stage);
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
	 * Registers the factory for the provider of the primars javafx-stage.
	 *
	 * @param stage the primary stage
	 */
	private void registerPrimaryStageProvider(final Stage stage) {
		ProviderService.registerFactory(new InstanceFactory<>(ApplicationConstants.PROVIDED_PRIMARY_STAGE) {
			@Override
			public Stage buildObject() {
				return stage;
			}
		});
	}




	/**
	 * Setup and load the core plugins and the {@link PluginService}.
	 */
	private void setupPlugins() {
		log.info("Setup plugins.");
		final PluginService pluginService = pluginServiceProvider.get();
		configuration.getPlugins().forEach(pluginService::registerPlugin);
		pluginService.loadAllPlugins();
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
		pluginServiceProvider.get().loadComponent(ApplicationConstants.COMPONENT_VIEW_SYSTEM);
	}




	/**
	 * Called when the javafx application was stopped.
	 */
	private void onStop() {
		log.info("Application on stop.");
		pluginServiceProvider.get().unloadComponent(ApplicationConstants.COMPONENT_VIEW_SYSTEM);
		eventServiceProvider.get().publish(ApplicationConstants.EVENT_APPLICATION_STOPPING);
		pluginServiceProvider.get().unloadAllPlugins();
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
