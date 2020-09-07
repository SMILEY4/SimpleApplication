package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;

import java.util.Objects;
import java.util.Optional;

@Getter
public class AnchorProperty extends Property {


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
		super(AnchorProperty.class);
		this.top = top;
		this.bottom = bottom;
		this.left = left;
		this.right = right;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		final AnchorProperty anchorOther = (AnchorProperty) other;
		return Objects.equals(getTop(), anchorOther.getTop())
				&& Objects.equals(getBottom(), anchorOther.getBottom())
				&& Objects.equals(getLeft(), anchorOther.getLeft())
				&& Objects.equals(getRight(), anchorOther.getRight());
	}




	@Override
	public String printValue() {
		return "t=" + getTop() + " b=" + getBottom() + " l=" + getLeft() + " r=" + getRight();
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<AnchorProperty, Node> {


		@Override
		public void build(final SuiBaseNode node,
						  final AnchorProperty property,
						  final Node fxNode) {
			setAnchors(property, fxNode);
		}




		@Override
		public MutationResult update(final AnchorProperty property,
									 final SuiBaseNode node,
									 final Node fxNode) {
			setAnchors(property, fxNode);
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




		@Override
		public MutationResult remove(final AnchorProperty property,
									 final SuiBaseNode node,
									 final Node fxNode) {
			AnchorPane.setTopAnchor(fxNode, null);
			AnchorPane.setBottomAnchor(fxNode, null);
			AnchorPane.setLeftAnchor(fxNode, null);
			AnchorPane.setRightAnchor(fxNode, null);
			return MutationResult.MUTATED;
		}

	}


}
