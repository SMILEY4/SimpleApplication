package de.ruegnerlukas.simpleapplication.simpleui.core.node;

import javafx.scene.layout.Pane;

import java.util.stream.Collectors;

public interface SuiNodeChildListener {


	/**
	 * A child listener that does nothing.
	 */
	SuiNodeChildListener NO_OP = node -> {
		// do nothing
	};

	/**
	 * A default child listener applicable to most cases (where the javafx node is a {@link Pane}).
	 */
	SuiNodeChildListener DEFAULT = node -> {
		final Pane pane = (Pane) node.getFxNodeStore().get();
		if (pane != null) {
			if (node.getChildNodeStore().hasChildren()) {
				pane.getChildren().setAll(node.getChildNodeStore().stream()
						.map(child -> child.getFxNodeStore().get())
						.collect(Collectors.toList()));
			} else {
				pane.getChildren().clear();
			}
		}
	};


	/**
	 * The child nodes of the given parent node have changed.
	 *
	 * @param parent the parent node of the changed child nodes
	 */
	void onChange(SuiNode parent);

}
