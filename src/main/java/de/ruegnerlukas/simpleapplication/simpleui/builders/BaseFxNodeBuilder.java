package de.ruegnerlukas.simpleapplication.simpleui.builders;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import javafx.scene.Node;

public interface BaseFxNodeBuilder<T extends Node> {


	/**
	 * Builds the base fx-node of the given {@link SNode}.
	 *
	 * @param context the scene context
	 * @param node    the {@link SNode}
	 * @return the base fx-node.
	 */
	T build(SceneContext context, SNode node);


}
