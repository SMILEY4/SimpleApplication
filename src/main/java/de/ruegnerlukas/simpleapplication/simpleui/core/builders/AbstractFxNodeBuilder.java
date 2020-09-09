package de.ruegnerlukas.simpleapplication.simpleui.core.builders;


import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.scene.Node;

public interface AbstractFxNodeBuilder<T extends Node> {


	/**
	 * Builds the javafx node of the given simpleui node.
	 *
	 * @param node the simpleui node
	 * @return the javafx node.
	 */
	T build(SuiNode node);


}
