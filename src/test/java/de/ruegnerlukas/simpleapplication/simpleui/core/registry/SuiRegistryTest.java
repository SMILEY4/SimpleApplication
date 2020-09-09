package de.ruegnerlukas.simpleapplication.simpleui.core.registry;

import de.ruegnerlukas.simpleapplication.common.validation.ValidatePresenceException;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.IdProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TextContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import org.junit.After;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class SuiRegistryTest {


	@After
	public void cleanup() {
		SuiRegistry.dispose();
	}




	@Test (expected = ValidatePresenceException.class)
	public void test_not_initialized() {
		assertThat(SuiRegistry.get()).isNull();
	}




	@Test
	public void test_initialize() {
		SuiRegistry.initialize();
		assertThat(SuiRegistry.get()).isNotNull();
	}




	@Test
	public void test_register_base_fx_node_builder() {

		SuiRegistry.initializeEmpty();

		assertThatThrownBy(() -> SuiRegistry.get().getEntry(SuiRegistryTest.class)).isInstanceOf(ValidatePresenceException.class);

		final AbstractFxNodeBuilder<?> fxNodeBuilder = Mockito.mock(AbstractFxNodeBuilder.class);
		SuiRegistry.get().registerBaseFxNodeBuilder(SuiRegistryTest.class, fxNodeBuilder);

		assertThat(SuiRegistry.get().getEntry(SuiRegistryTest.class).getProperties()).containsExactly(IdProperty.class);
		assertThat(SuiRegistry.get().getEntry(SuiRegistryTest.class).getPropFxNodeBuilders()).hasSize(1);
		assertThat(SuiRegistry.get().getEntry(SuiRegistryTest.class).getPropFxNodeUpdaters()).hasSize(1);
		assertThat(SuiRegistry.get().getEntry(SuiRegistryTest.class).getBaseFxNodeBuilder()).isEqualTo(fxNodeBuilder);

	}




	@Test
	public void test_register_single_property() {

		SuiRegistry.initializeEmpty();

		assertThatThrownBy(
				() -> SuiRegistry.get().registerProperty(
						SuiRegistryTest.class,
						TextContentProperty.class,
						new TextContentProperty.LabeledUpdatingBuilder())
		).isInstanceOf(ValidatePresenceException.class);

		final AbstractFxNodeBuilder<?> fxNodeBuilder = Mockito.mock(AbstractFxNodeBuilder.class);
		TextContentProperty.LabeledUpdatingBuilder propUpdatingBuilder = new TextContentProperty.LabeledUpdatingBuilder();

		SuiRegistry.get().registerBaseFxNodeBuilder(SuiRegistryTest.class, fxNodeBuilder);
		SuiRegistry.get().registerProperty(SuiRegistryTest.class, TextContentProperty.class, propUpdatingBuilder);

		assertThat(SuiRegistry.get().getEntry(SuiRegistryTest.class).getProperties()).containsExactly(IdProperty.class, TextContentProperty.class);
		assertThat(SuiRegistry.get().getEntry(SuiRegistryTest.class).getPropFxNodeBuilders()).hasSize(2);
		assertThat(SuiRegistry.get().getEntry(SuiRegistryTest.class).getPropFxNodeUpdaters()).hasSize(2);

		assertThat(SuiRegistry.get().getEntry(SuiRegistryTest.class).getPropFxNodeBuilders()).containsEntry(TextContentProperty.class, propUpdatingBuilder);
		assertThat(SuiRegistry.get().getEntry(SuiRegistryTest.class).getPropFxNodeUpdaters()).containsEntry(TextContentProperty.class, propUpdatingBuilder);

	}




	@Test
	public void test_register_multiple_properties_at_once() {

		SuiRegistry.initializeEmpty();

		final AbstractFxNodeBuilder<?> fxNodeBuilder = Mockito.mock(AbstractFxNodeBuilder.class);

		TextContentProperty.LabeledUpdatingBuilder textUpdatingBuilder = new TextContentProperty.LabeledUpdatingBuilder();
		AlignmentProperty.LabeledUpdatingBuilder alignmentUpdatingBuilder = new AlignmentProperty.LabeledUpdatingBuilder();

		SuiRegistry.get().registerBaseFxNodeBuilder(SuiRegistryTest.class, fxNodeBuilder);
		SuiRegistry.get().registerProperties(SuiRegistryTest.class, List.of(
				SuiRegistry.PropertyEntry.of(TextContentProperty.class, textUpdatingBuilder),
				SuiRegistry.PropertyEntry.of(AlignmentProperty.class, alignmentUpdatingBuilder)
		));

		assertThat(SuiRegistry.get().getEntry(SuiRegistryTest.class).getProperties()).containsExactlyInAnyOrder(IdProperty.class, TextContentProperty.class, AlignmentProperty.class);
		assertThat(SuiRegistry.get().getEntry(SuiRegistryTest.class).getPropFxNodeBuilders()).hasSize(3);
		assertThat(SuiRegistry.get().getEntry(SuiRegistryTest.class).getPropFxNodeUpdaters()).hasSize(3);

		assertThat(SuiRegistry.get().getEntry(SuiRegistryTest.class).getPropFxNodeBuilders()).containsEntry(TextContentProperty.class, textUpdatingBuilder);
		assertThat(SuiRegistry.get().getEntry(SuiRegistryTest.class).getPropFxNodeUpdaters()).containsEntry(TextContentProperty.class, textUpdatingBuilder);

		assertThat(SuiRegistry.get().getEntry(SuiRegistryTest.class).getPropFxNodeBuilders()).containsEntry(AlignmentProperty.class, alignmentUpdatingBuilder);
		assertThat(SuiRegistry.get().getEntry(SuiRegistryTest.class).getPropFxNodeUpdaters()).containsEntry(AlignmentProperty.class, alignmentUpdatingBuilder);

	}




	@Test
	public void test_inject() {

		final String INJECTION_POINT_ID = "injectionPoint";
		NodeFactory factory1 = Mockito.mock(NodeFactory.class);
		NodeFactory factory2 = Mockito.mock(NodeFactory.class);
		NodeFactory factory3 = Mockito.mock(NodeFactory.class);

		SuiRegistry.initializeEmpty();

		SuiRegistry.get().inject(INJECTION_POINT_ID, List.of(factory1, factory2, factory3));

		assertThat(SuiRegistry.get().getInjected("otherInjectionPoint")).isEmpty();
		assertThat(SuiRegistry.get().getInjected(INJECTION_POINT_ID)).hasSize(3);
		assertThat(SuiRegistry.get().getInjected(INJECTION_POINT_ID)).containsExactly(factory1, factory2, factory3);
	}


}
