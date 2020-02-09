package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.GenericFactory;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import de.ruegnerlukas.simpleapplication.core.presentation.views.EmptyView;
import de.ruegnerlukas.simpleapplication.core.presentation.views.View;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ApplicationConfiguration {


	/**
	 * The list of factories for the providers.
	 */
	private List<GenericFactory<?, ?>> providerFactories = new ArrayList<>();

	/**
	 * The plugins to load at startup.
	 */
	private List<Plugin> plugins = new ArrayList<>();

	/**
	 * Whether to show any view at application startup.
	 */
	@Setter
	private boolean showViewAtStartup = false;

	/**
	 * The first view to show when the application starts.
	 */
	@Setter
	private View view = new EmptyView();


}
