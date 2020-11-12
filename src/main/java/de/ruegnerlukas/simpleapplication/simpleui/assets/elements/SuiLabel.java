package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.FontProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TextContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TextFillProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.UnderlineProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.WrapTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;
import javafx.scene.control.Label;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiLabel {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiLabel() {
		// do nothing
	}




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
	public static SuiLabelBuilder create() {
		return new SuiLabelBuilder();
	}




	public static class SuiLabelBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiLabelBuilder>,
			RegionBuilderExtension<SuiLabelBuilder>,
			CommonEventBuilderExtension<SuiLabelBuilder>,
			TextContentProperty.PropertyBuilderExtension<SuiLabelBuilder>,
			WrapTextProperty.PropertyBuilderExtension<SuiLabelBuilder>,
			AlignmentProperty.PropertyBuilderExtension<SuiLabelBuilder>,
			FontProperty.PropertyBuilderExtension<SuiLabelBuilder>,
			UnderlineProperty.PropertyBuilderExtension<SuiLabelBuilder>,
			TextFillProperty.PropertyBuilderExtension<SuiLabelBuilder>,
			TooltipProperty.PropertyBuilderExtension<SuiLabelBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return create(
					SuiLabel.class,
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
		registry.registerBaseFxNodeBuilder(SuiLabel.class, new FxNodeBuilder());
		registry.registerProperties(SuiLabel.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiLabel.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiLabel.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiLabel.class, List.of(
				PropertyEntry.of(TextContentProperty.class, new TextContentProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(WrapTextProperty.class, new WrapTextProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(AlignmentProperty.class, new AlignmentProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(TooltipProperty.class, new TooltipProperty.ControlUpdatingBuilder()),
				PropertyEntry.of(FontProperty.class, new FontProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(UnderlineProperty.class, new UnderlineProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(TextFillProperty.class, new TextFillProperty.LabeledUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<Label> {


		@Override
		public Label build(final SuiNode node) {
			return new Label();
		}

	}


}
