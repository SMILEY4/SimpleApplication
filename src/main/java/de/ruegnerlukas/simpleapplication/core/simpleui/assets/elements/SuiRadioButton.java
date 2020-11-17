package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedRadioButton;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.events.OnActionEventProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.events.OnCheckedEventProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.CheckedProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.FontProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.IconProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.IdProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.TextContentProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.TextFillProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.ToggleGroupProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.UnderlineProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.WrapTextProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiRadioButton {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiRadioButton() {
		// do nothing
	}




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
	public static SuiRadioButtonBuilder create() {
		return new SuiRadioButtonBuilder();
	}




	public static class SuiRadioButtonBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiRadioButtonBuilder>,
			RegionBuilderExtension<SuiRadioButtonBuilder>,
			CommonEventBuilderExtension<SuiRadioButtonBuilder>,
			TextContentProperty.PropertyBuilderExtension<SuiRadioButtonBuilder>,
			IconProperty.PropertyBuilderExtension<SuiRadioButtonBuilder>,
			WrapTextProperty.PropertyBuilderExtension<SuiRadioButtonBuilder>,
			AlignmentProperty.PropertyBuilderExtension<SuiRadioButtonBuilder>,
			TooltipProperty.PropertyBuilderExtension<SuiRadioButtonBuilder>,
			FontProperty.PropertyBuilderExtension<SuiRadioButtonBuilder>,
			UnderlineProperty.PropertyBuilderExtension<SuiRadioButtonBuilder>,
			TextFillProperty.PropertyBuilderExtension<SuiRadioButtonBuilder>,
			CheckedProperty.PropertyBuilderExtension<SuiRadioButtonBuilder>,
			ToggleGroupProperty.PropertyBuilderExtension<SuiRadioButtonBuilder>,
			OnActionEventProperty.PropertyBuilderExtension<SuiRadioButtonBuilder>,
			OnCheckedEventProperty.PropertyBuilderExtension<SuiRadioButtonBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return create(
					SuiRadioButton.class,
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
		registry.registerBaseFxNodeBuilder(SuiRadioButton.class, new RadioButtonNodeBuilder());
		registry.registerProperties(SuiRadioButton.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiRadioButton.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiRadioButton.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiRadioButton.class, List.of(
				PropertyEntry.of(IdProperty.class, new SuiRadioButton.IdUpdatingBuilder()),
				PropertyEntry.of(TextContentProperty.class, new TextContentProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(IconProperty.class, new IconProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(WrapTextProperty.class, new WrapTextProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(AlignmentProperty.class, new AlignmentProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(TooltipProperty.class, new TooltipProperty.ControlUpdatingBuilder()),
				PropertyEntry.of(FontProperty.class, new FontProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(UnderlineProperty.class, new UnderlineProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(TextFillProperty.class, new TextFillProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(CheckedProperty.class, new CheckedProperty.RadioButtonUpdatingBuilder()),
				PropertyEntry.of(ToggleGroupProperty.class, new ToggleGroupProperty.RadioButtonUpdatingBuilder()),
				PropertyEntry.of(OnActionEventProperty.class, new OnActionEventProperty.ButtonBaseUpdatingBuilder()),
				PropertyEntry.of(OnCheckedEventProperty.class, new OnCheckedEventProperty.RadioButtonUpdatingBuilder())
		));
	}




	private static class RadioButtonNodeBuilder implements AbstractFxNodeBuilder<ExtendedRadioButton> {


		@Override
		public ExtendedRadioButton build(final SuiNode node) {
			return new ExtendedRadioButton();
		}

	}






	public static class IdUpdatingBuilder implements PropFxNodeUpdatingBuilder<IdProperty, ExtendedRadioButton> {


		@Override
		public void build(final SuiNode node, final IdProperty property, final ExtendedRadioButton fxNode) {
			fxNode.setSuiNodeId(property.getId());
		}




		@Override
		public MutationResult update(final IdProperty property, final SuiNode node, final ExtendedRadioButton fxNode) {
			fxNode.setSuiNodeId(property.getId());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final IdProperty property, final SuiNode node, final ExtendedRadioButton fxNode) {
			fxNode.setSuiNodeId(null);
			return MutationResult.MUTATED;
		}

	}


}
