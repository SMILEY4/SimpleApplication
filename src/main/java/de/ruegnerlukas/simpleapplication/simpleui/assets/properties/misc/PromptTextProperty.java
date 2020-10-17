package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.FactoryExtension;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 *
		 * @param text the prompt text
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T promptText(final String text) {
			getFactoryInternalProperties().add(new PromptTextProperty(text));
			return (T) this;
		}

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






	public static class ListViewUpdatingBuilder implements PropFxNodeUpdatingBuilder<PromptTextProperty, ListView<?>> {


		@Override
		public void build(final SuiNode node, final PromptTextProperty property, final ListView<?> fxNode) {
			fxNode.setPlaceholder(buildPlaceholder(property.getText()));
		}




		@Override
		public MutationResult update(final PromptTextProperty property, final SuiNode node, final ListView<?> fxNode) {
			fxNode.setPlaceholder(buildPlaceholder(property.getText()));
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final PromptTextProperty property, final SuiNode node, final ListView<?> fxNode) {
			fxNode.setPlaceholder(null);
			return MutationResult.MUTATED;
		}




		/**
		 * Build the placeholder label with the given text.
		 *
		 * @param text the text to display
		 * @return the created label
		 */
		private Label buildPlaceholder(final String text) {
			Label label = new Label();
			label.setText(text);
			label.setAlignment(Pos.CENTER);
			return label;
		}

	}


}
