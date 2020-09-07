package de.ruegnerlukas.simpleapplication.simpleui.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiBaseNode;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.properties.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.EditableProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PromptTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.properties.TextContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnTextChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnTextEnteredEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import javafx.scene.control.TextField;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.get;

public final class SuiTextField {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiTextField() {
		// do nothing
	}




	/**
	 * Creates a new text field
	 *
	 * @param properties the properties
	 * @return the factory for a text field
	 */
	public static NodeFactory textField(final Property... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiTextField.class, get().getEntry(SuiTextField.class).getProperties(), properties);
		return state -> SuiBaseNode.create(
				SuiTextField.class,
				List.of(properties),
				state
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
				PropertyEntry.of(OnTextChangedEventProperty.class, new OnTextChangedEventProperty.TextFieldUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements BaseFxNodeBuilder<TextField> {


		@Override
		public TextField build(final MasterNodeHandlers nodeHandlers, final SuiNode node) {
			return new TextField();
		}

	}


}
