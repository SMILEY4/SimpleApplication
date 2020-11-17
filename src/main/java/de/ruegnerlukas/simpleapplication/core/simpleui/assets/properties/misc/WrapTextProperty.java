package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;

import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
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




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T wrapText() {
			return wrapText(true);
		}

		/**
		 * @param wrapText whether to wrap the text if it is too long
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T wrapText(final boolean wrapText) {
			getBuilderProperties().add(new WrapTextProperty(wrapText));
			return (T) this;
		}

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
