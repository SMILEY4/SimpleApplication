package de.ruegnerlukas.simpleapplication.simpleui.builders;


import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import javafx.scene.Node;

public interface BaseFxNodeBuilder<T extends Node> {


	/**
	 * Builds the base fx-node of the given {@link SuiNode}.
	 *
	 * @param node         the {@link SuiNode}
	 * @param nodeHandlers the primary node handlers (builder, mutator)
	 * @return the base fx-node.
	 */
	T build(MasterNodeHandlers nodeHandlers, SuiNode node);


}
