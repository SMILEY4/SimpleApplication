package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;
import lombok.Getter;

import java.util.function.BiFunction;

public class TextContentProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<TextContentProperty, TextContentProperty, Boolean> COMPARATOR =
			(a, b) -> a.getText().equals(b.getText());

	/**
	 * The text content.
	 */
	@Getter
	private final String text;




	/**
	 * @param text the text content
	 */
	public TextContentProperty(final String text) {
		super(TextContentProperty.class, COMPARATOR);
		this.text = text;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param text the content
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T textContent(final String text) {
			getBuilderProperties().add(new TextContentProperty(text));
			return (T) this;
		}

	}






	public static class LabeledUpdatingBuilder implements PropFxNodeUpdatingBuilder<TextContentProperty, Labeled> {


		@Override
		public void build(final SuiNode node,
						  final TextContentProperty property,
						  final Labeled fxNode) {
			fxNode.setText(property.getText());
		}




		@Override
		public MutationResult update(final TextContentProperty property,
									 final SuiNode node,
									 final Labeled fxNode) {
			fxNode.setText(property.getText());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final TextContentProperty property,
									 final SuiNode node,
									 final Labeled fxNode) {
			fxNode.setText("");
			return MutationResult.MUTATED;
		}

	}






	public static class TextInputControlUpdatingBuilder implements PropFxNodeUpdatingBuilder<TextContentProperty, TextInputControl> {


		@Override
		public void build(final SuiNode node,
						  final TextContentProperty property,
						  final TextInputControl fxNode) {
			fxNode.setText(property.getText());
		}




		@Override
		public MutationResult update(final TextContentProperty property,
									 final SuiNode node,
									 final TextInputControl fxNode) {
			fxNode.setText(property.getText());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final TextContentProperty property,
									 final SuiNode node,
									 final TextInputControl fxNode) {
			fxNode.setText("");
			return MutationResult.MUTATED;
		}

	}

}
