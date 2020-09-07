package de.ruegnerlukas.simpleapplication.simpleui.elements.basenode;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import javafx.scene.Node;

import java.util.Optional;

public class FxNodeStore {


	/**
	 * the javafx node
	 */
	private Node node;




	/**
	 * @return whether a javafx node is present.
	 */
	public boolean isPresent() {
		return node != null;
	}




	/**
	 * @param node the new node. Must not be null.
	 */
	public void set(final Node node) {
		Validations.INPUT.notNull(node).exception("New javafx node may not be null.");
		this.node = node;
	}




	/**
	 * Removes the currently present javafx node from this store.
	 */
	public void clear() {
		this.node = null;
	}




	/**
	 * @return the javafx node
	 */
	public Optional<Node> get() {
		return Optional.ofNullable(getUnsafe());
	}




	/**
	 * @return the javafx node or null
	 */
	public Node getUnsafe() {
		return node;
	}


}
