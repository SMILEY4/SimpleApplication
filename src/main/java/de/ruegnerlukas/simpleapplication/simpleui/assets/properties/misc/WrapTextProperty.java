package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;

import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextArea;
import lombok.Getter;

import java.util.function.BiFunction;

public class WrapTextProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<WrapTextProperty, WrapTextProperty, Boolean> COMPARATOR =
			(a, b) -> a.isWrap() == b.isWrap();
	/**
	 * Whether the text content should wrap.
	 */
	@Getter
	private final boolean wrap;




	/**
	 * @param wrap whether the text content should wrap.
	 */
	public WrapTextProperty(final boolean wrap) {
		super(WrapTextProperty.class, COMPARATOR);
		this.wrap = wrap;
	}




	public static class LabeledUpdatingBuilder implements PropFxNodeUpdatingBuilder<WrapTextProperty, Labeled> {


		@Override
		public void build(final SuiNode node,
						  final WrapTextProperty property,
						  final Labeled fxNode) {
			fxNode.setWrapText(property.isWrap());
		}




		@Override
		public MutationResult update(final WrapTextProperty property,
									 final SuiNode node,
									 final Labeled fxNode) {
			fxNode.setWrapText(property.isWrap());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final WrapTextProperty property,
									 final SuiNode node,
									 final Labeled fxNode) {
			fxNode.setWrapText(false);
			return MutationResult.MUTATED;
		}

	}






	public static class TextAreaUpdatingBuilder implements PropFxNodeUpdatingBuilder<WrapTextProperty, TextArea> {


		@Override
		public void build(final SuiNode node,
						  final WrapTextProperty property,
						  final TextArea fxNode) {
			fxNode.setWrapText(property.isWrap());
		}




		@Override
		public MutationResult update(final WrapTextProperty property,
									 final SuiNode node,
									 final TextArea fxNode) {
			fxNode.setWrapText(property.isWrap());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final WrapTextProperty property,
									 final SuiNode node,
									 final TextArea fxNode) {
			fxNode.setWrapText(false);
			return MutationResult.MUTATED;
		}

	}


}
