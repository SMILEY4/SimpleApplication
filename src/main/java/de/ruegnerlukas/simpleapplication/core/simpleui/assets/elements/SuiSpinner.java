package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedSpinner;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.events.OnValueChangedEventProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.EditableProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.FontProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.SpinnerFactoryProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;
import de.ruegnerlukas.simpleapplication.common.tags.Tags;

import java.util.List;
import java.util.stream.Collectors;

import static de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiSpinner {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiSpinner() {
		// do nothing
	}




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
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
			FontProperty.PropertyBuilderExtension<SuiSpinnerBuilder>,
			SpinnerFactoryProperty.PropertyBuilderExtension<SuiSpinnerBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			Validations.INPUT.contains(
					getBuilderProperties().stream().map(SuiProperty::getKey).collect(Collectors.toSet()), SpinnerFactoryProperty.class)
					.exception("Property '{}' missing. Property is required,", SpinnerFactoryProperty.class.getSimpleName());
			return create(
					SuiSpinner.class,
					state,
					tags
			);
		}


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
				PropertyEntry.of(TooltipProperty.class, new TooltipProperty.ControlUpdatingBuilder()),
				PropertyEntry.of(FontProperty.class, new FontProperty.SpinnerUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<ExtendedSpinner<?>> {


		@Override
		public ExtendedSpinner<?> build(final SuiNode node) {
			return new ExtendedSpinner<>();
		}


	}


}
