package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.ProviderConfiguration;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginService;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewService;

public class CoreProviderConfiguration extends ProviderConfiguration {


	@Override
	public void configure() {
		add(new InstanceFactory<>(PluginService.class) {
			@Override
			public PluginService buildObject() {
				return new PluginService();
			}
		});
		add(new InstanceFactory<>(ViewService.class) {
			@Override
			public ViewService buildObject() {
				return new ViewService();
			}
		});
		add(new InstanceFactory<>(EventService.class) {
			@Override
			public EventService buildObject() {
				return new EventService();
			}
		});
	}

}
