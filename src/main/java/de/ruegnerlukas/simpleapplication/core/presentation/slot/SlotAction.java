package de.ruegnerlukas.simpleapplication.core.presentation.slot;

import javafx.scene.Node;

public interface SlotAction {


	/**
	 * Accept the given node
	 *
	 * @param node the node
	 * @param args additional values if needed
	 * @return true, if the node was accepted
	 */
	boolean onAccept(Node node, Object... args);

	/**
	 * Remove the given node from this slot
	 *
	 * @param node the node to remove
	 * @return true, if the node was removed
	 */
	boolean onRemove(Node node);

	/**
	 * Remove the content of this slot
	 *
	 * @return true, if content was removed
	 */
	boolean onRemoveContent();

}
