package de.ruegnerlukas.simpleapplication.core.simpleui.core;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.ProviderService;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.WindowRootElement;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class InjectWindowRootElementsTest {


	@Before
	public void setupSuiRegistry() {
		ProviderService.registerFactory(new InstanceFactory<>(SuiRegistry.class) {
			@Override
			public SuiRegistry buildObject() {
				return new SuiRegistry(true);
			}
		});
	}




	@After
	public void cleanupSuiRegistry() {
		ProviderService.cleanup();
	}




	@Test
	public void get_children_no_injection_point() {

		final WindowRootElement root = WindowRootElement.windowRoot()
				.modal(WindowRootElement.windowRoot().title("1"))
				.modal(WindowRootElement.windowRoot().title("2"))
				.modal(WindowRootElement.windowRoot().title("3"));

		final List<WindowRootElement> children = root.getModalWindowRootElements();

		assertThat(children).hasSize(3);
		assertThat(children.stream().map(WindowRootElement::getTitle).collect(Collectors.toList()))
				.containsExactlyInAnyOrder("1", "2", "3");

	}


	@Test
	public void get_children_nothing_injected() {

		final WindowRootElement root = WindowRootElement.windowRoot()
				.modal(WindowRootElement.windowRoot().title("1"))
				.modal(WindowRootElement.windowRoot().title("2"))
				.modal(WindowRootElement.windowRoot().title("3"))
				.modalsInjectable("injectionPointA")
				.modalsInjectable("injectionPointB");

		final List<WindowRootElement> children = root.getModalWindowRootElements();

		assertThat(children).hasSize(3);
		assertThat(children.stream().map(WindowRootElement::getTitle).collect(Collectors.toList()))
				.containsExactlyInAnyOrder("1", "2", "3");

	}


	@Test
	public void get_children_injected() {

		final WindowRootElement root = WindowRootElement.windowRoot()
				.modal(WindowRootElement.windowRoot().title("1"))
				.modal(WindowRootElement.windowRoot().title("2"))
				.modal(WindowRootElement.windowRoot().title("3"))
				.modalsInjectable("injectionPointA")
				.modalsInjectable("injectionPointB");

		final SuiRegistry suiRegistry = new Provider<>(SuiRegistry.class).get();
		suiRegistry.injectChildWindow("injectionPointA",
				WindowRootElement.windowRoot().title("a1"),
				WindowRootElement.windowRoot().title("a2"));

		suiRegistry.injectChildWindow("injectionPointB",
				WindowRootElement.windowRoot().title("b1"),
				WindowRootElement.windowRoot().title("b2"));

		suiRegistry.injectChildWindow("injectionPointX",
				WindowRootElement.windowRoot().title("x1"),
				WindowRootElement.windowRoot().title("x2"));

		final List<WindowRootElement> children = root.getModalWindowRootElements();

		assertThat(children).hasSize(7);
		assertThat(children.stream().map(WindowRootElement::getTitle).collect(Collectors.toList()))
				.containsExactlyInAnyOrder("1", "2", "3", "a1", "a2", "b1", "b2");

	}



}
