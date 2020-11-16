package de.ruegnerlukas.simpleapplication.core.simpleui.core.registry;

import de.ruegnerlukas.simpleapplication.common.eventbus.EventBusImpl;
import de.ruegnerlukas.simpleapplication.common.validation.ValidatePresenceException;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.IdProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.TextContentProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.NodeFactory;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SuiRegistryTest {







	@Test
	public void test_register_base_fx_node_builder() {

		final SuiRegistry suiRegistry = new SuiRegistry(true, new EventBusImpl());

		assertThatThrownBy(() -> suiRegistry.getEntry(SuiRegistryTest.class)).isInstanceOf(ValidatePresenceException.class);

		final AbstractFxNodeBuilder<?> fxNodeBuilder = Mockito.mock(AbstractFxNodeBuilder.class);
		suiRegistry.registerBaseFxNodeBuilder(SuiRegistryTest.class, fxNodeBuilder);

		assertThat(suiRegistry.getEntry(SuiRegistryTest.class).getProperties()).containsExactly(IdProperty.class);
		assertThat(suiRegistry.getEntry(SuiRegistryTest.class).getPropFxNodeBuilders()).hasSize(1);
		assertThat(suiRegistry.getEntry(SuiRegistryTest.class).getPropFxNodeUpdaters()).hasSize(1);
		assertThat(suiRegistry.getEntry(SuiRegistryTest.class).getBaseFxNodeBuilder()).isEqualTo(fxNodeBuilder);

	}




	@Test
	public void test_register_single_property() {

		final SuiRegistry suiRegistry = new SuiRegistry(true, new EventBusImpl());

		assertThatThrownBy(
				() -> suiRegistry.registerProperty(
						SuiRegistryTest.class,
						TextContentProperty.class,
						new TextContentProperty.LabeledUpdatingBuilder())
		).isInstanceOf(ValidatePresenceException.class);

		final AbstractFxNodeBuilder<?> fxNodeBuilder = Mockito.mock(AbstractFxNodeBuilder.class);
		TextContentProperty.LabeledUpdatingBuilder propUpdatingBuilder = new TextContentProperty.LabeledUpdatingBuilder();

		suiRegistry.registerBaseFxNodeBuilder(SuiRegistryTest.class, fxNodeBuilder);
		suiRegistry.registerProperty(SuiRegistryTest.class, TextContentProperty.class, propUpdatingBuilder);

		assertThat(suiRegistry.getEntry(SuiRegistryTest.class).getProperties()).containsExactlyInAnyOrder(IdProperty.class, TextContentProperty.class);
		assertThat(suiRegistry.getEntry(SuiRegistryTest.class).getPropFxNodeBuilders()).hasSize(2);
		assertThat(suiRegistry.getEntry(SuiRegistryTest.class).getPropFxNodeUpdaters()).hasSize(2);

		assertThat(suiRegistry.getEntry(SuiRegistryTest.class).getPropFxNodeBuilders()).containsEntry(TextContentProperty.class, propUpdatingBuilder);
		assertThat(suiRegistry.getEntry(SuiRegistryTest.class).getPropFxNodeUpdaters()).containsEntry(TextContentProperty.class, propUpdatingBuilder);

	}




	@Test
	public void test_register_multiple_properties_at_once() {

		final SuiRegistry suiRegistry = new SuiRegistry(true, new EventBusImpl());

		final AbstractFxNodeBuilder<?> fxNodeBuilder = Mockito.mock(AbstractFxNodeBuilder.class);

		TextContentProperty.LabeledUpdatingBuilder textUpdatingBuilder = new TextContentProperty.LabeledUpdatingBuilder();
		AlignmentProperty.LabeledUpdatingBuilder alignmentUpdatingBuilder = new AlignmentProperty.LabeledUpdatingBuilder();

		suiRegistry.registerBaseFxNodeBuilder(SuiRegistryTest.class, fxNodeBuilder);
		suiRegistry.registerProperties(SuiRegistryTest.class, List.of(
				SuiRegistry.PropertyEntry.of(TextContentProperty.class, textUpdatingBuilder),
				SuiRegistry.PropertyEntry.of(AlignmentProperty.class, alignmentUpdatingBuilder)
		));

		assertThat(suiRegistry.getEntry(SuiRegistryTest.class).getProperties()).containsExactlyInAnyOrder(IdProperty.class, TextContentProperty.class, AlignmentProperty.class);
		assertThat(suiRegistry.getEntry(SuiRegistryTest.class).getPropFxNodeBuilders()).hasSize(3);
		assertThat(suiRegistry.getEntry(SuiRegistryTest.class).getPropFxNodeUpdaters()).hasSize(3);

		assertThat(suiRegistry.getEntry(SuiRegistryTest.class).getPropFxNodeBuilders()).containsEntry(TextContentProperty.class, textUpdatingBuilder);
		assertThat(suiRegistry.getEntry(SuiRegistryTest.class).getPropFxNodeUpdaters()).containsEntry(TextContentProperty.class, textUpdatingBuilder);

		assertThat(suiRegistry.getEntry(SuiRegistryTest.class).getPropFxNodeBuilders()).containsEntry(AlignmentProperty.class, alignmentUpdatingBuilder);
		assertThat(suiRegistry.getEntry(SuiRegistryTest.class).getPropFxNodeUpdaters()).containsEntry(AlignmentProperty.class, alignmentUpdatingBuilder);

	}




	@Test
	public void test_inject() {


		final String INJECTION_POINT_ID = "injectionPoint";
		NodeFactory factory1 = Mockito.mock(NodeFactory.class);
		NodeFactory factory2 = Mockito.mock(NodeFactory.class);
		NodeFactory factory3 = Mockito.mock(NodeFactory.class);

		final SuiRegistry suiRegistry = new SuiRegistry(true, new EventBusImpl());

		suiRegistry.inject(INJECTION_POINT_ID, List.of(factory1, factory2, factory3));

		assertThat(suiRegistry.getInjectedNodeFactories("otherInjectionPoint")).isEmpty();
		assertThat(suiRegistry.getInjectedNodeFactories(INJECTION_POINT_ID)).hasSize(3);
		assertThat(suiRegistry.getInjectedNodeFactories(INJECTION_POINT_ID)).containsExactly(factory1, factory2, factory3);
	}


}
