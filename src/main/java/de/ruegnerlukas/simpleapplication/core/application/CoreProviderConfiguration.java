package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.ProviderConfiguration;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.events.EventServiceImpl;
import de.ruegnerlukas.simpleapplication.core.extensions.ExtensionPointService;
import de.ruegnerlukas.simpleapplication.core.extensions.ExtensionPointServiceImpl;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginService;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginServiceImpl;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewService;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewServiceImpl;

public class CoreProviderConfiguration extends ProviderConfiguration {


	@Override
	public void configure() {
		add(new InstanceFactory<>(PluginService.class) {
			@Override
			public PluginService buildObject() {
				return new PluginServiceImpl();
			}
		});
		add(new InstanceFactory<>(ViewService.class) {
			@Override
			public ViewService buildObject() {
				return new ViewServiceImpl();
			}
		});
		add(new InstanceFactory<>(EventService.class) {
			@Override
			public EventService buildObject() {
				return new EventServiceImpl();
			}
		});
		add(new InstanceFactory<>(ExtensionPointService.class) {
			@Override
			public ExtensionPointService buildObject() {
				return new ExtensionPointServiceImpl();
			}
		});
	}

}
