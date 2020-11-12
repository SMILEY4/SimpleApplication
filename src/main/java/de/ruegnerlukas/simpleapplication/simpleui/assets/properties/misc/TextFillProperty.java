package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.control.Labeled;
import javafx.scene.paint.Color;
import lombok.Getter;

import java.util.Objects;
import java.util.function.BiFunction;

public class TextFillProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<TextFillProperty, TextFillProperty, Boolean> COMPARATOR =
			(a, b) -> Objects.equals(a.getColor(), b.getColor());

	/**
	 * the text color.
	 */
	@Getter
	private final Color color;




	/**
	 * @param color the text color
	 */
	public TextFillProperty(final Color color) {
		super(TextFillProperty.class, COMPARATOR);
		this.color = color;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param color the text color
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T textColor(final Color color) {
			getBuilderProperties().add(new TextFillProperty(color));
			return (T) this;
		}

	}






	public static class LabeledUpdatingBuilder implements PropFxNodeUpdatingBuilder<TextFillProperty, Labeled> {


		@Override
		public void build(final SuiNode node, final TextFillProperty property, final Labeled fxNode) {
			fxNode.setTextFill(property.getColor());
		}




		@Override
		public MutationResult update(final TextFillProperty property, final SuiNode node, final Labeled fxNode) {
			fxNode.setTextFill(property.getColor());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final TextFillProperty property, final SuiNode node, final Labeled fxNode) {
			fxNode.setTextFill(null);
			return MutationResult.MUTATED;
		}

	}


}



