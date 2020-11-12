package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import javafx.geometry.Insets;
import javafx.scene.layout.Region;
import lombok.Getter;

import java.util.function.BiFunction;

@Getter
public class PaddingProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<PaddingProperty, PaddingProperty, Boolean> COMPARATOR =
			(a, b) -> NumberUtils.isEqual(a.getTop(), b.getTop())
					&& NumberUtils.isEqual(a.getBottom(), b.getBottom())
					&& NumberUtils.isEqual(a.getLeft(), b.getLeft())
					&& NumberUtils.isEqual(a.getRight(), b.getRight());

	/**
	 * The value for the top padding.
	 */
	private final Number top;

	/**
	 * The value for the bottom padding.
	 */
	private final Number bottom;

	/**
	 * The value for the left padding.
	 */
	private final Number left;

	/**
	 * The value for the right padding.
	 */
	private final Number right;




	/**
	 * @param top    the value for the top padding.
	 * @param bottom the value for the bottom padding.
	 * @param left   the value for the left padding.
	 * @param right  the value for the right padding.
	 */
	public PaddingProperty(final Number top, final Number bottom, final Number left, final Number right) {
		super(PaddingProperty.class, COMPARATOR);
		Validations.INPUT.notNull(top).exception("The top padding may not be null.");
		Validations.INPUT.notNull(bottom).exception("The bottom padding may not be null.");
		Validations.INPUT.notNull(left).exception("The left padding may not be null.");
		Validations.INPUT.notNull(right).exception("The right padding may not be null.");
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param sides the value for the padding on all sides
		 * @return this builder for chaining
		 */
		default T padding(final Number sides) {
			return padding(sides, sides, sides, sides);
		}

		/**
		 * @param top    the value for the top padding
		 * @param bottom the value for the bottom padding
		 * @param left   the value for the left padding
		 * @param right  the value for the right padding
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T padding(final Number top, final Number bottom, final Number left, final Number right) {
			getBuilderProperties().add(new PaddingProperty(top, bottom, left, right));
			return (T) this;
		}

	}






	public static class RegionUpdatingBuilder implements PropFxNodeUpdatingBuilder<PaddingProperty, Region> {


		@Override
		public void build(final SuiNode node, final PaddingProperty property, final Region fxNode) {
			fxNode.setPadding(new Insets(
					property.getTop().doubleValue(),
					property.getRight().doubleValue(),
					property.getBottom().doubleValue(),
					property.getLeft().doubleValue()
			));
		}




		@Override
		public MutationResult update(final PaddingProperty property, final SuiNode node, final Region fxNode) {
			fxNode.setPadding(new Insets(
					property.getTop().doubleValue(),
					property.getRight().doubleValue(),
					property.getBottom().doubleValue(),
					property.getLeft().doubleValue()
			));
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final PaddingProperty property, final SuiNode node, final Region fxNode) {
			fxNode.setPadding(Insets.EMPTY);
			return MutationResult.MUTATED;
		}


	}


}
