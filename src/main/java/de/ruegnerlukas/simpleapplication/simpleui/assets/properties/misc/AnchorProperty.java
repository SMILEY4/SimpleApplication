package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.FactoryExtension;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;

import java.util.Optional;
import java.util.function.BiFunction;

@Getter
public class AnchorProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<AnchorProperty, AnchorProperty, Boolean> COMPARATOR =
			(a, b) -> NumberUtils.isEqual(a.getTop(), b.getTop())
					&& NumberUtils.isEqual(a.getBottom(), b.getBottom())
					&& NumberUtils.isEqual(a.getLeft(), b.getLeft())
					&& NumberUtils.isEqual(a.getRight(), b.getRight());

	/**
	 * The value for the top anchor or null.
	 */
	private final Number top;

	/**
	 * The value for the bottom anchor or null.
	 */
	private final Number bottom;

	/**
	 * The value for the left anchor or null.
	 */
	private final Number left;

	/**
	 * The value for the right anchor or null.
	 */
	private final Number right;




	/**
	 * @param top    the value for the top anchor or null.
	 * @param bottom the value for the bottom anchor or null.
	 * @param left   the value for the left anchor or null.
	 * @param right  the value for the right anchor or null.
	 */
	public AnchorProperty(final Number top, final Number bottom, final Number left, final Number right) {
		super(AnchorProperty.class, COMPARATOR);
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param top    the value for the top anchor or null.
		 * @param bottom the value for the bottom anchor or null.
		 * @param left   the value for the left anchor or null.
		 * @param right  the value for the right anchor or null.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T alignment(final Number top, final Number bottom, final Number left, final Number right) {
			getFactoryInternalProperties().add(new AnchorProperty(top, bottom, left, right));
			return (T) this;
		}

	}






	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<AnchorProperty, Node> {


		@Override
		public void build(final SuiNode node, final AnchorProperty property, final Node fxNode) {
			setAnchors(property, fxNode);
		}




		@Override
		public MutationResult update(final AnchorProperty property, final SuiNode node, final Node fxNode) {
			setAnchors(property, fxNode);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final AnchorProperty property, final SuiNode node, final Node fxNode) {
			AnchorPane.setTopAnchor(fxNode, null);
			AnchorPane.setBottomAnchor(fxNode, null);
			AnchorPane.setLeftAnchor(fxNode, null);
			AnchorPane.setRightAnchor(fxNode, null);
			return MutationResult.MUTATED;
		}




		/**
		 * Sets the anchor values of the given property for the given fx node
		 *
		 * @param property the anchor property
		 * @param fxNode   the fx node
		 */
		private void setAnchors(final AnchorProperty property, final Node fxNode) {
			AnchorPane.setTopAnchor(fxNode, Optional.ofNullable(property.getTop()).map(Number::doubleValue).orElse(null));
			AnchorPane.setBottomAnchor(fxNode, Optional.ofNullable(property.getBottom()).map(Number::doubleValue).orElse(null));
			AnchorPane.setLeftAnchor(fxNode, Optional.ofNullable(property.getLeft()).map(Number::doubleValue).orElse(null));
			AnchorPane.setRightAnchor(fxNode, Optional.ofNullable(property.getRight()).map(Number::doubleValue).orElse(null));
		}


	}


}
