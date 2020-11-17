package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedToggleButton;
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

public final class SuiToggleButton {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiToggleButton() {
		// do nothing
	}




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
	public static SuiToggleButtonBuilder create() {
		return new SuiToggleButtonBuilder();
	}




	public static class SuiToggleButtonBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiToggleButtonBuilder>,
			RegionBuilderExtension<SuiToggleButtonBuilder>,
			CommonEventBuilderExtension<SuiToggleButtonBuilder>,
			TextContentProperty.PropertyBuilderExtension<SuiToggleButtonBuilder>,
			IconProperty.PropertyBuilderExtension<SuiToggleButtonBuilder>,
			WrapTextProperty.PropertyBuilderExtension<SuiToggleButtonBuilder>,
			AlignmentProperty.PropertyBuilderExtension<SuiToggleButtonBuilder>,
			TooltipProperty.PropertyBuilderExtension<SuiToggleButtonBuilder>,
			FontProperty.PropertyBuilderExtension<SuiToggleButtonBuilder>,
			UnderlineProperty.PropertyBuilderExtension<SuiToggleButtonBuilder>,
			TextFillProperty.PropertyBuilderExtension<SuiToggleButtonBuilder>,
			CheckedProperty.PropertyBuilderExtension<SuiToggleButtonBuilder>,
			ToggleGroupProperty.PropertyBuilderExtension<SuiToggleButtonBuilder>,
			OnActionEventProperty.PropertyBuilderExtension<SuiToggleButtonBuilder>,
			OnCheckedEventProperty.PropertyBuilderExtension<SuiToggleButtonBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return create(
					SuiToggleButton.class,
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
		registry.registerBaseFxNodeBuilder(SuiToggleButton.class, new ToggleButtonNodeBuilder());
		registry.registerProperties(SuiToggleButton.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiToggleButton.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiToggleButton.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiToggleButton.class, List.of(
				PropertyEntry.of(IdProperty.class, new SuiToggleButton.IdUpdatingBuilder()),
				PropertyEntry.of(TextContentProperty.class, new TextContentProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(IconProperty.class, new IconProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(WrapTextProperty.class, new WrapTextProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(AlignmentProperty.class, new AlignmentProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(TooltipProperty.class, new TooltipProperty.ControlUpdatingBuilder()),
				PropertyEntry.of(FontProperty.class, new FontProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(UnderlineProperty.class, new UnderlineProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(TextFillProperty.class, new TextFillProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(CheckedProperty.class, new CheckedProperty.ToggleButtonUpdatingBuilder()),
				PropertyEntry.of(ToggleGroupProperty.class, new ToggleGroupProperty.ToggleButtonUpdatingBuilder()),
				PropertyEntry.of(OnActionEventProperty.class, new OnActionEventProperty.ButtonBaseUpdatingBuilder()),
				PropertyEntry.of(OnCheckedEventProperty.class, new OnCheckedEventProperty.ToggleButtonUpdatingBuilder())
		));
	}




	private static class ToggleButtonNodeBuilder implements AbstractFxNodeBuilder<ExtendedToggleButton> {


		@Override
		public ExtendedToggleButton build(final SuiNode node) {
			return new ExtendedToggleButton();
		}

	}






	public static class IdUpdatingBuilder implements PropFxNodeUpdatingBuilder<IdProperty, ExtendedToggleButton> {


		@Override
		public void build(final SuiNode node, final IdProperty property, final ExtendedToggleButton fxNode) {
			fxNode.setSuiNodeId(property.getId());
		}




		@Override
		public MutationResult update(final IdProperty property, final SuiNode node, final ExtendedToggleButton fxNode) {
			fxNode.setSuiNodeId(property.getId());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final IdProperty property, final SuiNode node, final ExtendedToggleButton fxNode) {
			fxNode.setSuiNodeId(null);
			return MutationResult.MUTATED;
		}

	}


}
