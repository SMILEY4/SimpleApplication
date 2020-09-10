package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.TextInputControl;
import lombok.Getter;

import java.util.function.BiFunction;

public class PromptTextProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<PromptTextProperty, PromptTextProperty, Boolean> COMPARATOR =
			(a, b) -> a.getText().equals(b.getText());

	/**
	 * The text prompt.
	 */
	@Getter
	private final String text;




	/**
	 * @param text the prompt text
	 */
	public PromptTextProperty(final String text) {
		super(PromptTextProperty.class, COMPARATOR);
		this.text = text;
	}




	public static class TextInputControlUpdatingBuilder implements PropFxNodeUpdatingBuilder<PromptTextProperty, TextInputControl> {


		@Override
		public void build(final SuiNode node,
						  final PromptTextProperty property,
						  final TextInputControl fxNode) {
			fxNode.setPromptText(property.getText());
		}




		@Override
		public MutationResult update(final PromptTextProperty property,
									 final SuiNode node,
									 final TextInputControl fxNode) {
			fxNode.setPromptText(property.getText());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final PromptTextProperty property,
									 final SuiNode node,
									 final TextInputControl fxNode) {
			fxNode.setPromptText("");
			return MutationResult.MUTATED;
		}

	}






	public static class ComboBoxBaseUpdatingBuilder<T> implements PropFxNodeUpdatingBuilder<PromptTextProperty, ComboBoxBase<T>> {


		@Override
		public void build(final SuiNode node,
						  final PromptTextProperty property,
						  final ComboBoxBase<T> fxNode) {
			fxNode.setPromptText(property.getText());
		}




		@Override
		public MutationResult update(final PromptTextProperty property,
									 final SuiNode node,
									 final ComboBoxBase<T> fxNode) {
			fxNode.setPromptText(property.getText());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final PromptTextProperty property,
									 final SuiNode node,
									 final ComboBoxBase<T> fxNode) {
			fxNode.setPromptText("");
			return MutationResult.MUTATED;
		}

	}


}
