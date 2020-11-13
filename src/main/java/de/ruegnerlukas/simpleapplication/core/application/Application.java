package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.eventbus.EventBus;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.ProviderService;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginService;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

import java.util.function.Consumer;

@Slf4j
public class Application {


	/**
	 * The provider for the {@link PluginService}.
	 */
	private final Provider<PluginService> pluginServiceProvider = new Provider<>(PluginService.class);

	/**
	 * The provider for the {@link EventBus}.
	 */
	private final Provider<EventBus> eventBusProvider = new Provider<>(EventBus.class);

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
		SysOutOverSLF4J.sendSystemOutAndErrToSLF4J();
		final Consumer<Stage> startAction = this::onStart;
		final Runnable stopAction = this::onStop;
		jfxStarter.start(startAction, stopAction);
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
		log.info("Application started.");
		eventBusProvider.get().publish(EventApplicationStarted.TAGS, new EventApplicationStarted());
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
	 * Called when the javafx application was stopped.
	 */
	private void onStop() {
		log.info("Application on stop.");
		eventBusProvider.get().publish(EventApplicationStopping.TAGS, new EventApplicationStopping());
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
