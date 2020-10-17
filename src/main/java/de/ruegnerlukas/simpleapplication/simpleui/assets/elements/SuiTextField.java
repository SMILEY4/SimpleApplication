package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnTextChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnTextEnteredEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.EditableProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.PromptTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TextContentProperty;
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
import javafx.scene.control.TextField;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiTextField {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiTextField() {
		// do nothing
	}




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
			OnTextChangedEventProperty.PropertyBuilderExtension<SuiTextFieldBuilder>,
			OnTextEnteredEventProperty.PropertyBuilderExtension<SuiTextFieldBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return SuiNode.create(
					SuiTextField.class,
					getFactoryInternalProperties(),
					state,
					tags
			);
		}


	}




	/**
	 * Creates a new text field
	 *
	 * @param properties the properties
	 * @return the factory for a text field
	 */
	public static NodeFactory textField(final SuiProperty... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiTextField.class, properties);
		return (state, tags) -> SuiNode.create(
				SuiTextField.class,
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
				PropertyEntry.of(TooltipProperty.class, new TooltipProperty.ControlUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<TextField> {


		@Override
		public TextField build(final SuiNode node) {
			return new TextField();
		}

	}


}
