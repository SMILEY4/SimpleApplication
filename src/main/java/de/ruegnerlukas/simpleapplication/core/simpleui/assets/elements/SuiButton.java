package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.events.OnActionEventProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.FontProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.IconProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.TextContentProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.TextFillProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.UnderlineProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.WrapTextProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.tags.Tags;
import javafx.scene.control.Button;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiButton {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiButton() {
		// do nothing
	}




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
	public static SuiButtonBuilder create() {
		return new SuiButtonBuilder();
	}




	public static class SuiButtonBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiButtonBuilder>,
			RegionBuilderExtension<SuiButtonBuilder>,
			CommonEventBuilderExtension<SuiButtonBuilder>,
			TextContentProperty.PropertyBuilderExtension<SuiButtonBuilder>,
			IconProperty.PropertyBuilderExtension<SuiButtonBuilder>,
			WrapTextProperty.PropertyBuilderExtension<SuiButtonBuilder>,
			AlignmentProperty.PropertyBuilderExtension<SuiButtonBuilder>,
			TooltipProperty.PropertyBuilderExtension<SuiButtonBuilder>,
			FontProperty.PropertyBuilderExtension<SuiButtonBuilder>,
			UnderlineProperty.PropertyBuilderExtension<SuiButtonBuilder>,
			TextFillProperty.PropertyBuilderExtension<SuiButtonBuilder>,
			OnActionEventProperty.PropertyBuilderExtension<SuiButtonBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return create(
					SuiButton.class,
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
		registry.registerBaseFxNodeBuilder(SuiButton.class, new ButtonNodeBuilder());
		registry.registerProperties(SuiButton.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiButton.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiButton.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiButton.class, List.of(
				PropertyEntry.of(TextContentProperty.class, new TextContentProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(IconProperty.class, new IconProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(WrapTextProperty.class, new WrapTextProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(AlignmentProperty.class, new AlignmentProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(OnActionEventProperty.class, new OnActionEventProperty.ButtonBaseUpdatingBuilder()),
				PropertyEntry.of(TooltipProperty.class, new TooltipProperty.ControlUpdatingBuilder()),
				PropertyEntry.of(FontProperty.class, new FontProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(UnderlineProperty.class, new UnderlineProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(TextFillProperty.class, new TextFillProperty.LabeledUpdatingBuilder())
		));
	}




	private static class ButtonNodeBuilder implements AbstractFxNodeBuilder<Button> {


		@Override
		public Button build(final SuiNode node) {
			return new Button();
		}

	}


}
