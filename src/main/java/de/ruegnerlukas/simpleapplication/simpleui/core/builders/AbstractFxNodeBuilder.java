package de.ruegnerlukas.simpleapplication.simpleui.core.builders;


import de.ruegnerlukas.simpleapplication.simpleui.core.SuiNode;
import javafx.scene.Node;

public interface AbstractFxNodeBuilder<T extends Node> {


	/**
	 * Builds the base fx-node of the given {@link SuiNode}.
	 *
	 * @param node         the {@link SuiNode}
	 * @param nodeHandlers the primary node handlers (builder, mutator)
	 * @return the base fx-node.
	 */
	T build(MasterNodeHandlers nodeHandlers, SuiNode node);


}
