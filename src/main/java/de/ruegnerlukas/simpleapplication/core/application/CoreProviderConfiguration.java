package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.eventbus.EventBus;
import de.ruegnerlukas.simpleapplication.common.eventbus.EventBusImpl;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.ProviderConfiguration;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.GenericFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginService;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginServiceImpl;

import java.util.List;

public class CoreProviderConfiguration extends ProviderConfiguration {


	@Override
	public List<GenericFactory<?, ?>> getFactories() {
		configure();
		return super.getFactories();
	}




	/**
	 * creates and add all the factories
	 */
	protected void configure() {
		add(new InstanceFactory<>(PluginService.class) {
			@Override
			public PluginService buildObject() {
				return new PluginServiceImpl();
			}
		});
		add(new InstanceFactory<>(EventBus.class) {
			@Override
			public EventBus buildObject() {
				return new EventBusImpl();
			}
		});
	}

}
