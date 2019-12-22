package de.ruegnerlukas.simpleapplication.core.presentation.slot;

import javafx.scene.Node;
import lombok.Getter;

public class Slot {


	/**
	 * The unique identifier of this slot.
	 */
	@Getter
	private final String id;

	/**
	 * The {@link SlotAction} of this slot.
	 */
	private final SlotAction action;




	/**
	 * @param id     the unique identifier of this slot
	 * @param action the {@link SlotAction} of this slot
	 */
	Slot(final String id, final SlotAction action) {
		this.id = id;
		this.action = action;
	}




	/**
	 * Accepts the given node
	 *
	 * @param node the node
	 * @param args additional values if needed
	 * @return true, if the node was accepted
	 */
	public boolean accept(final Node node, final Object... args) {
		if (action != null) {
			return action.onAccept(node, args);
		} else {
			return false;
		}
	}




	/**
	 * Removes the content of this slot
	 *
	 * @return true, if content was removed
	 */
	public boolean removeContent() {
		if (action != null) {
			return action.onRemoveContent();
		} else {
			return false;
		}
	}




	/**
	 * Removes the given node from this slot
	 *
	 * @param node the node to remove
	 * @return true, if the node was removed
	 */
	public boolean remove(final Node node) {
		if (action != null) {
			return action.onRemove(node);
		} else {
			return false;
		}
	}

}
