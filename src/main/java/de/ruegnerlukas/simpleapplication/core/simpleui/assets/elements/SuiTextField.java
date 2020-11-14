package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.events.OnTextChangedEventProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.events.OnTextEnteredEventProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.EditableProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.FontProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.PromptTextProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.TextContentProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;
import javafx.scene.control.TextField;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiTextField {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiTextField() {
		// do nothing
	}




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
	public static SuiTextFieldBuilder create() {
		return new SuiTextFieldBuilder();
	}




	public static class SuiTextFieldBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiTextFieldBuilder>,
			RegionBuilderExtension<SuiTextFieldBuilder>,
			CommonEventBuilderExtension<SuiTextFieldBuilder>,
			TextContentProperty.PropertyBuilderExtension<SuiTextFieldBuilder>,
			AlignmentProperty.PropertyBuilderExtension<SuiTextFieldBuilder>,
			PromptTextProperty.PropertyBuilderExtension<SuiTextFieldBuilder>,
			TooltipProperty.PropertyBuilderExtension<SuiTextFieldBuilder>,
			EditableProperty.PropertyBuilderExtension<SuiTextFieldBuilder>,
			FontProperty.PropertyBuilderExtension<SuiTextFieldBuilder>,
			OnTextChangedEventProperty.PropertyBuilderExtension<SuiTextFieldBuilder>,
			OnTextEnteredEventProperty.PropertyBuilderExtension<SuiTextFieldBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return create(
					SuiTextField.class,
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
		registry.registerBaseFxNodeBuilder(SuiTextField.class, new FxNodeBuilder());
		registry.registerProperties(SuiTextField.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiTextField.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiTextField.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiTextField.class, List.of(
				PropertyEntry.of(TextContentProperty.class, new TextContentProperty.TextInputControlUpdatingBuilder()),
				PropertyEntry.of(AlignmentProperty.class, new AlignmentProperty.TextFieldUpdatingBuilder()),
				PropertyEntry.of(PromptTextProperty.class, new PromptTextProperty.TextInputControlUpdatingBuilder()),
				PropertyEntry.of(EditableProperty.class, new EditableProperty.TextInputControlUpdatingBuilder()),
				PropertyEntry.of(OnTextEnteredEventProperty.class, new OnTextEnteredEventProperty.TextFieldUpdatingBuilder()),
				PropertyEntry.of(OnTextChangedEventProperty.class, new OnTextChangedEventProperty.TextFieldUpdatingBuilder()),
				PropertyEntry.of(TooltipProperty.class, new TooltipProperty.ControlUpdatingBuilder()),
				PropertyEntry.of(FontProperty.class, new FontProperty.TextInputControlUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<TextField> {


		@Override
		public TextField build(final SuiNode node) {
			return new TextField();
		}

	}


}
