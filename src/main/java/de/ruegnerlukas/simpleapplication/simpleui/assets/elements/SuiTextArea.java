package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnTextChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnTextEnteredEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.EditableProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.PromptTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TextContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.WrapTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.scene.control.TextArea;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiTextArea {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiTextArea() {
		// do nothing
	}




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
	public static SuiTextAreaBuilder create() {
		return new SuiTextAreaBuilder();
	}




	public static class SuiTextAreaBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiTextAreaBuilder>,
			RegionBuilderExtension<SuiTextAreaBuilder>,
			CommonEventBuilderExtension<SuiTextAreaBuilder>,
			TextContentProperty.PropertyBuilderExtension<SuiTextAreaBuilder>,
			PromptTextProperty.PropertyBuilderExtension<SuiTextAreaBuilder>,
			WrapTextProperty.PropertyBuilderExtension<SuiTextAreaBuilder>,
			TooltipProperty.PropertyBuilderExtension<SuiTextAreaBuilder>,
			EditableProperty.PropertyBuilderExtension<SuiTextAreaBuilder>,
			OnTextChangedEventProperty.PropertyBuilderExtension<SuiTextAreaBuilder>,
			OnTextEnteredEventProperty.PropertyBuilderExtension<SuiTextAreaBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return create(
					SuiTextArea.class,
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
		registry.registerBaseFxNodeBuilder(SuiTextArea.class, new FxNodeBuilder());
		registry.registerProperties(SuiTextArea.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiTextArea.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiTextArea.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiTextArea.class, List.of(
				PropertyEntry.of(TextContentProperty.class, new TextContentProperty.TextInputControlUpdatingBuilder()),
				PropertyEntry.of(PromptTextProperty.class, new PromptTextProperty.TextInputControlUpdatingBuilder()),
				PropertyEntry.of(EditableProperty.class, new EditableProperty.TextInputControlUpdatingBuilder()),
				PropertyEntry.of(WrapTextProperty.class, new WrapTextProperty.TextAreaUpdatingBuilder()),
				PropertyEntry.of(OnTextChangedEventProperty.class, new OnTextChangedEventProperty.TextAreaUpdatingBuilder()),
				PropertyEntry.of(OnTextEnteredEventProperty.class, new OnTextEnteredEventProperty.TextAreaUpdatingBuilder()),
				PropertyEntry.of(TooltipProperty.class, new TooltipProperty.ControlUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<TextArea> {


		@Override
		public TextArea build(final SuiNode node) {
			return new TextArea();
		}

	}


}
