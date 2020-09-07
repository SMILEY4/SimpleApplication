package de.ruegnerlukas.simpleapplication.simpleui.core.node;

import de.ruegnerlukas.simpleapplication.simpleui.core.SuiNode;
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
	 * A default child listener applicable to most cases..
	 */
	SuiNodeChildListener DEFAULT = node -> {
		final Pane pane = (Pane) node.getFxNode();
		if (node.hasChildren()) {
			pane.getChildren().setAll(node.streamChildren()
					.map(SuiNode::getFxNode)
					.collect(Collectors.toList()));
		} else {
			pane.getChildren().clear();
		}
	};


	/**
	 * The child nodes of the given parent node have changed.
	 *
	 * @param parent the parent node of the changed child nodes
	 */
	void onChange(SuiNode parent);

}
