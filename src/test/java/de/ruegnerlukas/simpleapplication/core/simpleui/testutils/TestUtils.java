package de.ruegnerlukas.simpleapplication.core.simpleui.testutils;


import de.ruegnerlukas.simpleapplication.common.eventbus.EventBus;
import de.ruegnerlukas.simpleapplication.common.eventbus.EventBusImpl;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.ProviderService;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtils {


	public static void registerSuiRegistryFactory() {
		registerSuiRegistryFactory(false, new EventBusImpl());
	}




	public static void registerSuiRegistryFactory(final EventBus eventBus) {
		registerSuiRegistryFactory(false, eventBus);
	}




	public static void registerSuiRegistryFactory(final boolean empty, final EventBus eventBus) {
		ProviderService.registerFactory(new InstanceFactory<>(SuiRegistry.class) {
			@Override
			public SuiRegistry buildObject() {
				return new SuiRegistry(empty, eventBus);
			}
		});
	}




	public static void assertNode(SuiNode node, Class<?> nodeType) {
		assertThat(node).isNotNull();
		assertThat(node.getNodeType()).isEqualTo(nodeType);
	}


}
