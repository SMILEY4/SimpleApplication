package de.ruegnerlukas.simpleapplication.simpleui.builders;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import javafx.scene.Node;

public interface BaseFxNodeBuilder<T extends Node> {


	/**
	 * Builds the base fx-node of the given {@link SUINode}.
	 *
	 * @param node         the {@link SUINode}
	 * @param nodeHandlers the primary node handlers (builder, mutator)
	 * @return the base fx-node.
	 */
	T build(MasterNodeHandlers nodeHandlers, SUINode node);


}
