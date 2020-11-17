package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.GenericFactory;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ApplicationConfiguration {


	/**
	 * The list of factories for the providers.
	 */
	private final List<GenericFactory<?, ?>> providerFactories = new ArrayList<>();

	/**
	 * The plugins to load at startup.
	 */
	private final List<Plugin> plugins = new ArrayList<>();

}
