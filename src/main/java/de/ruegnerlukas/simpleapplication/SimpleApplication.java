package de.ruegnerlukas.simpleapplication;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.GenericFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.ProviderService;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;

import java.util.ArrayList;
import java.util.List;

public final class SimpleApplication {


	/**
	 * Whether the application was started
	 */
	private static boolean applicationStarted = false;

	/**
	 * The list of {@link ProviderConfiguration} added before starting the application.
	 */
	private static final List<ProviderConfiguration> PROVIDER_CONFIGURATIONS = new ArrayList<>();




	/**
	 * Utility class
	 */
	private SimpleApplication() {
	}




	/**
	 * Adds the given configuration.
	 * All factories of this config will be registered with and usable by providers after the application was started.
	 *
	 * @param config the configuration
	 */
	public static void addProviderConfiguration(final ProviderConfiguration config) {
		Validations.INPUT.notNull(config).exception("The configuration must not be null.");
		Validations.STATE.isFalse(applicationStarted)
				.exception("New configurations cant be added after the application was started.");
		PROVIDER_CONFIGURATIONS.add(config);
	}




	/**
	 * Starts the application.
	 */
	public static void startApplication() {
		Validations.STATE.isFalse(applicationStarted).exception("The application is already running and cant be started again.");
		setupProvider();
	}




	/**
	 * Setup for all things provider related.
	 */
	private static void setupProvider() {
		for (ProviderConfiguration configuration : PROVIDER_CONFIGURATIONS) {
			for (GenericFactory<?, ?> factory : configuration.getFactories()) {
				ProviderService.registerFactory(factory);
			}
		}
	}


}
