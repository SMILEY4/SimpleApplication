package de.ruegnerlukas.simpleapplication.simpleui.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.EditableProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PromptTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.properties.TextContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.WrapTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnTextChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnTextEnteredEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import javafx.scene.control.TextArea;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.get;

public final class SuiTextArea {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiTextArea() {
		// do nothing
	}




	/**
	 * Creates a new text area
	 *
	 * @param properties the properties
	 * @return the factory for a text area
	 */
	public static NodeFactory textArea(final Property... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiTextArea.class, get().getEntry(SuiTextArea.class).getProperties(), properties);
		return state -> new SuiNode(SuiTextArea.class, List.of(properties), state, null);
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
				PropertyEntry.of(OnTextEnteredEventProperty.class, new OnTextEnteredEventProperty.TextAreaUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements BaseFxNodeBuilder<TextArea> {


		@Override
		public TextArea build(final MasterNodeHandlers nodeHandlers, final SuiNode node) {
			return new TextArea();
		}

	}


}
