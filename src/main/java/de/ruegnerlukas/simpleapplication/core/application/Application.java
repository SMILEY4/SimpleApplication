package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.eventbus.EventBus;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.ProviderService;
import de.ruegnerlukas.simpleapplication.core.application.jfx.JFXStarter;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginService;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
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
	 * The starter for the javafx application.
	 */
	private JFXStarter jfxStarter;

	/**
	 * The core provider configuration.
	 */
	private CoreProviderConfiguration coreProviderConfiguration;




	/**
	 * @param configuration the configuration of this application
	 */
	public Application(final ApplicationConfiguration configuration) {
		this.configuration = configuration;
		setJFXApplicationStarter(new JFXStarter());
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
		log.debug("Application on start.");
		setupProviders(stage);
		setupSimpleUI();
		setupPlugins();
		log.info("Application started.");
		eventBusProvider.get().publishEmpty(ApplicationConstants.EVENT_APPLICATION_STARTED_TAGS);
	}




	/**
	 * Setup of the providers.
	 *
	 * @param stage the primary stage
	 */
	private void setupProviders(final Stage stage) {
		log.debug("Setup provider configurations.");
		coreProviderConfiguration.getFactories().forEach(ProviderService::registerFactory);
		configuration.getProviderFactories().forEach(ProviderService::registerFactory);
		ProviderService.registerFactory(new InstanceFactory<>(ApplicationConstants.PROVIDED_PRIMARY_STAGE) {
			@Override
			public Stage buildObject() {
				return stage;
			}
		});
	}




	/**
	 * Sets up everything regarding simple-ui
	 */
	private void setupSimpleUI() {
		log.debug("Setup SimpleUI.");
		final SuiRegistry suiRegistry = new SuiRegistry(false, new Provider<>(EventBus.class).get());
		ProviderService.registerFactory(new InstanceFactory<>(SuiRegistry.class) {
			@Override
			public SuiRegistry buildObject() {
				return suiRegistry;
			}
		});
	}




	/**
	 * Setup and load the core plugins and the {@link PluginService}.
	 */
	private void setupPlugins() {
		log.debug("Setup plugins.");
		final PluginService pluginService = pluginServiceProvider.get();
		configuration.getPlugins().forEach(pluginService::registerPlugin);
		pluginService.loadAllPlugins();
	}




	/**
	 * Called when the javafx application was stopped.
	 */
	private void onStop() {
		log.debug("Application stopping.");
		eventBusProvider.get().publishEmpty(ApplicationConstants.EVENT_APPLICATION_STOPPING_TAGS);
		pluginServiceProvider.get().unloadAllPlugins();
		ProviderService.cleanup();
		log.info("Application stopped.");
	}




	/**
	 * Set the starter for the javafx application. Usually only for internal purposes.
	 *
	 * @param starter the starter
	 */
	public void setJFXApplicationStarter(final JFXStarter starter) {
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
