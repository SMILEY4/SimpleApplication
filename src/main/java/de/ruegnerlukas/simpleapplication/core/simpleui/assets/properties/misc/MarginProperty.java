package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.util.function.BiFunction;

@Getter
public class MarginProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<MarginProperty, MarginProperty, Boolean> COMPARATOR =
			(a, b) -> NumberUtils.isEqual(a.getTop(), b.getTop())
					&& NumberUtils.isEqual(a.getBottom(), b.getBottom())
					&& NumberUtils.isEqual(a.getLeft(), b.getLeft())
					&& NumberUtils.isEqual(a.getRight(), b.getRight());

	/**
	 * The value for the top margin.
	 */
	private final Number top;

	/**
	 * The value for the bottom margin.
	 */
	private final Number bottom;

	/**
	 * The value for the left margin.
	 */
	private final Number left;

	/**
	 * The value for the right margin.
	 */
	private final Number right;




	/**
	 * @param top    the value for the top margin.
	 * @param bottom the value for the bottom margin.
	 * @param left   the value for the left margin.
	 * @param right  the value for the right margin.
	 */
	public MarginProperty(final Number top, final Number bottom, final Number left, final Number right) {
		super(MarginProperty.class, COMPARATOR);
		Validations.INPUT.notNull(top).exception("The top margin may not be null.");
		Validations.INPUT.notNull(bottom).exception("The bottom margin may not be null.");
		Validations.INPUT.notNull(left).exception("The left margin may not be null.");
		Validations.INPUT.notNull(right).exception("The right margin may not be null.");
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param sides the value for the margin on all sides
		 * @return this builder for chaining
		 */
		default T margin(final Number sides) {
			return margin(sides, sides, sides, sides);
		}

		/**
		 * @param top    the value for the top margin
		 * @param bottom the value for the bottom margin
		 * @param left   the value for the left margin
		 * @param right  the value for the right margin
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T margin(final Number top, final Number bottom, final Number left, final Number right) {
			getBuilderProperties().add(new MarginProperty(top, bottom, left, right));
			return (T) this;
		}

	}






	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<MarginProperty, Node> {


		@Override
		public void build(final SuiNode node, final MarginProperty property, final Node fxNode) {
			setMargin(fxNode, property);
		}




		@Override
		public MutationResult update(final MarginProperty property, final SuiNode node, final Node fxNode) {
			setMargin(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MarginProperty property, final SuiNode node, final Node fxNode) {
			VBox.setMargin(fxNode, Insets.EMPTY);
			return MutationResult.MUTATED;
		}




		/**
		 * @param node     the javafx node
		 * @param property the property
		 */
		private void setMargin(final Node node, final MarginProperty property) {
			VBox.setMargin(node, new Insets(
					property.getTop().doubleValue(),
					property.getRight().doubleValue(),
					property.getBottom().doubleValue(),
					property.getLeft().doubleValue()));
			HBox.setMargin(node, new Insets(
					property.getTop().doubleValue(),
					property.getRight().doubleValue(),
					property.getBottom().doubleValue(),
					property.getLeft().doubleValue()));
		}


	}


}
