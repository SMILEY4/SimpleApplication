package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextInputControl;
import lombok.Getter;

public class TextContentProperty extends Property {


	/**
	 * The text content.
	 */
	@Getter
	private final String text;




	/**
	 * @param text the text content
	 */
	public TextContentProperty(final String text) {
		super(TextContentProperty.class);
		this.text = text;
	}




	@Override
	public boolean isPropertyEqual(final Property other) {
		return getText().equals(((TextContentProperty) other).getText());
	}




	@Override
	public String printValue() {
		return getText();
	}




	public static class LabeledUpdatingBuilder implements PropFxNodeUpdatingBuilder<TextContentProperty, Labeled> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final TextContentProperty property,
						  final Labeled fxNode) {
			fxNode.setText(property.getText());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final TextContentProperty property,
									 final SuiNode node,
									 final Labeled fxNode) {
			fxNode.setText(property.getText());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final TextContentProperty property,
									 final SuiNode node,
									 final Labeled fxNode) {
			fxNode.setText("");
			return MutationResult.MUTATED;
		}

	}






	public static class TextInputControlUpdatingBuilder implements PropFxNodeUpdatingBuilder<TextContentProperty, TextInputControl> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final TextContentProperty property,
						  final TextInputControl fxNode) {
			fxNode.setText(property.getText());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final TextContentProperty property,
									 final SuiNode node,
									 final TextInputControl fxNode) {
			fxNode.setText(property.getText());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final TextContentProperty property,
									 final SuiNode node,
									 final TextInputControl fxNode) {
			fxNode.setText("");
			return MutationResult.MUTATED;
		}

	}

}
