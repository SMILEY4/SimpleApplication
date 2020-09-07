package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;
import lombok.Getter;

public class WrapTextProperty extends Property {


	/**
	 * Whether the text content should wrap.
	 */
	@Getter
	private final boolean wrap;




	/**
	 * @param wrap whether the text content should wrap.
	 */
	public WrapTextProperty(final boolean wrap) {
		super(WrapTextProperty.class);
		this.wrap = wrap;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return wrap == ((WrapTextProperty) other).isWrap();
	}




	@Override
	public String printValue() {
		return wrap ? "wrap" : "dontWrap";
	}




	public static class LabeledUpdatingBuilder implements PropFxNodeUpdatingBuilder<WrapTextProperty, Labeled> {


		@Override
		public void build(final SuiBaseNode node,
						  final WrapTextProperty property,
						  final Labeled fxNode) {
			fxNode.setWrapText(property.isWrap());
		}




		@Override
		public MutationResult update(final WrapTextProperty property,
									 final SuiBaseNode node,
									 final Labeled fxNode) {
			fxNode.setWrapText(property.isWrap());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final WrapTextProperty property,
									 final SuiBaseNode node,
									 final Labeled fxNode) {
			fxNode.setWrapText(false);
			return MutationResult.MUTATED;
		}

	}






	public static class TextAreaUpdatingBuilder implements PropFxNodeUpdatingBuilder<WrapTextProperty, TextArea> {


		@Override
		public void build(final SuiBaseNode node,
						  final WrapTextProperty property,
						  final TextArea fxNode) {
			fxNode.setWrapText(property.isWrap());
		}




		@Override
		public MutationResult update(final WrapTextProperty property,
									 final SuiBaseNode node,
									 final TextArea fxNode) {
			fxNode.setWrapText(property.isWrap());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final WrapTextProperty property,
									 final SuiBaseNode node,
									 final TextArea fxNode) {
			fxNode.setWrapText(false);
			return MutationResult.MUTATED;
		}

	}


}
