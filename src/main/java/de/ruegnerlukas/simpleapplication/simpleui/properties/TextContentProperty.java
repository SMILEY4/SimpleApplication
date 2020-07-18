package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import javafx.scene.control.Labeled;
import lombok.Getter;

public class TextContentProperty extends Property {


	@Getter
	private final String text;




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




	public static String getText(SNode node) {
		return node.getPropertySafe(TextContentProperty.class)
				.map(TextContentProperty::getText)
				.orElse("");
	}




	public static class TextContentUpdatingBuilder implements PropFxNodeUpdatingBuilder<TextContentProperty, Labeled> {


		@Override
		public void build(final SceneContext context, final SNode node, final TextContentProperty property, final Labeled fxNode) {
			fxNode.setText(property.getText());
		}




		@Override
		public BaseNodeMutator.MutationResult update(final SceneContext context, final TextContentProperty property, final SNode node, final Labeled fxNode) {
			fxNode.setText(property.getText());
			return BaseNodeMutator.MutationResult.MUTATED;
		}




		@Override
		public BaseNodeMutator.MutationResult remove(final SceneContext context, final TextContentProperty property, final SNode node, final Labeled fxNode) {
			fxNode.setText("");
			return BaseNodeMutator.MutationResult.MUTATED;
		}

	}


}
