package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedSpinner;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnValueChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.EditableProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SpinnerFactoryProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiSpinner {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiSpinner() {
		// do nothing
	}




	public static SuiSpinnerBuilder create() {
		return new SuiSpinnerBuilder();
	}




	public static class SuiSpinnerBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiSpinnerBuilder>,
			RegionBuilderExtension<SuiSpinnerBuilder>,
			CommonEventBuilderExtension<SuiSpinnerBuilder>,
			TooltipProperty.PropertyBuilderExtension<SuiSpinnerBuilder>,
			OnValueChangedEventProperty.PropertyBuilderExtension<SuiSpinnerBuilder>,
			EditableProperty.PropertyBuilderExtension<SuiSpinnerBuilder>,
			SpinnerFactoryProperty.PropertyBuilderExtension<SuiSpinnerBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return SuiNode.create(
					SuiSpinner.class,
					getFactoryInternalProperties(),
					state,
					tags
			);
		}


	}




	/**
	 * Creates a new spinner
	 *
	 * @param properties the properties
	 * @return the factory for a spinner
	 */
	public static NodeFactory spinner(final SuiProperty... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiSpinner.class, properties);
		Validations.INPUT.contains(
				Stream.of(properties).map(SuiProperty::getKey).collect(Collectors.toSet()), SpinnerFactoryProperty.class)
				.exception("Property '{}' missing. this property is required,", SpinnerFactoryProperty.class.getSimpleName());
		return (state, tags) -> SuiNode.create(
				SuiSpinner.class,
				List.of(properties),
				state,
				tags
		);
	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SuiRegistry registry) {
		registry.registerBaseFxNodeBuilder(SuiSpinner.class, new FxNodeBuilder());
		registry.registerProperties(SuiSpinner.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiSpinner.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiSpinner.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiSpinner.class, List.of(
				PropertyEntry.of(SpinnerFactoryProperty.class, new SpinnerFactoryProperty.SpinnerUpdatingBuilder()),
				PropertyEntry.of(EditableProperty.class, new EditableProperty.SpinnerUpdatingBuilder()),
				PropertyEntry.of(OnValueChangedEventProperty.class, new OnValueChangedEventProperty.SpinnerUpdatingBuilder<>()),
				PropertyEntry.of(TooltipProperty.class, new TooltipProperty.ControlUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<ExtendedSpinner<?>> {


		@Override
		public ExtendedSpinner<?> build(final SuiNode node) {
			return new ExtendedSpinner<>();
		}


	}


}
