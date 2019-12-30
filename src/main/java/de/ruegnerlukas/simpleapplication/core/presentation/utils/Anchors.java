package de.ruegnerlukas.simpleapplication.core.presentation.utils;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

/**
 * Utility class for setting anchors of an {@link AnchorPane}.
 */
public final class Anchors {


	/**
	 * Private constructor.
	 */
	private Anchors() {
	}




	/**
	 * Sets the anchors for the given node for each side.
	 *
	 * @param node   the node
	 * @param top    the anchor for the top side. Null to add no constraint
	 * @param bottom the anchor for the bottom side. Null to add no constraint
	 * @param left   the anchor for the left side. Null to add no constraint
	 * @param right  the anchor for the right side. Null to add no constraint
	 */
	public static void setAnchors(final Node node, final Number top, final Number bottom, final Number left, final Number right) {
		Validations.INPUT.notNull(node, "The node can not be null.");
		AnchorPane.setTopAnchor(node, (top == null ? null : top.doubleValue()));
		AnchorPane.setBottomAnchor(node, (bottom == null ? null : bottom.doubleValue()));
		AnchorPane.setLeftAnchor(node, (left == null ? null : left.doubleValue()));
		AnchorPane.setRightAnchor(node, (right == null ? null : right.doubleValue()));
	}




	/**
	 * Sets the anchors of the given node on all sides to the same value.
	 *
	 * @param node   the node
	 * @param anchor the value for anchors on all sides
	 */
	public static void setAnchors(final Node node, final int anchor) {
		Validations.INPUT.notNull(node, "The node can not be null.");
		setAnchors(node, (double) anchor);
	}




	/**
	 * Sets the anchors of the given node on all sides to the same value.
	 *
	 * @param node   the node
	 * @param anchor the value for anchors on all sides
	 */
	public static void setAnchors(final Node node, final double anchor) {
		Validations.INPUT.notNull(node, "The node can not be null.");
		AnchorPane.setTopAnchor(node, anchor);
		AnchorPane.setBottomAnchor(node, anchor);
		AnchorPane.setLeftAnchor(node, anchor);
		AnchorPane.setRightAnchor(node, anchor);
	}


}
