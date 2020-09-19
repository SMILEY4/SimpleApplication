package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnValueChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.EditableProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SpinnerFactoryProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.scene.control.Spinner;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiSpinner {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiSpinner() {
		// do nothing
	}




	/**
	 * Creates a new spinner
	 *
	 * @param properties the properties
	 * @return the factory for a spinner
	 */
	public static NodeFactory spinner(final SuiProperty... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiSpinner.class, properties);
		Validations.INPUT.contains(
				Stream.of(properties).map(SuiProperty::getKey).collect(Collectors.toSet()), SpinnerFactoryProperty.class)
				.exception("Property '{}' missing. this property is required,", SpinnerFactoryProperty.class.getSimpleName());
		return (state, tags) -> SuiNode.create(
				SuiSpinner.class,
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
		registry.registerBaseFxNodeBuilder(SuiSpinner.class, new FxNodeBuilder());
		registry.registerProperties(SuiSpinner.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiSpinner.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiSpinner.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiSpinner.class, List.of(
				PropertyEntry.of(SpinnerFactoryProperty.class, new SpinnerFactoryProperty.SpinnerUpdatingBuilder()),
				PropertyEntry.of(EditableProperty.class, new EditableProperty.SpinnerUpdatingBuilder()),
				PropertyEntry.of(OnValueChangedEventProperty.class, new OnValueChangedEventProperty.SpinnerUpdatingBuilder<>())
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<Spinner<?>> {


		@Override
		public Spinner<?> build(final SuiNode node) {
			Spinner<?> spinner = new Spinner<>();
			addTextInputSanitizers(spinner);
			return spinner;
		}




		/**
		 * Prevent errors when the user enters invalid text.
		 * This does not prevent exceptions when the editor looses focus with an invalid content, only when the enter-key was pressed.
		 *
		 * @param spinner the spinner
		 */
		private void addTextInputSanitizers(final Spinner spinner) {
			spinner.getEditor().addEventHandler(KeyEvent.KEY_PRESSED, event -> {
				if (event.getCode() == KeyCode.ENTER) {
					sanitizeTextInput(spinner);
				}
			});
		}




		/**
		 * Cleans the text input to prevent errors with invalid input.
		 *
		 * @param spinner the spinner
		 */
		private void sanitizeTextInput(final Spinner spinner) {
			final StringConverter converter = spinner.getValueFactory().getConverter();
			if (converter != null) {
				try {
					converter.fromString(spinner.getEditor().getText());
				} catch (Exception e) {
					spinner.getEditor().setText(
							String.valueOf(converter.toString(spinner.getValue())));
				}
			}
		}


	}


}
