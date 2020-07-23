package de.ruegnerlukas.simpleapplication.simpleui;

import javafx.scene.Node;

public interface SUISceneContext {


	/**
	 * @return the simpleui root node. Builds the root node with the node factory if necessary.
	 */
	SUINode getRootNode();

	/**
	 * @return the javafx node of the simpleui root node.
	 * Builds the root node with the node factory if necessary.
	 */
	Node getRootFxNode();


	/**
	 * @return the current simpleui state managed by this context.
	 */
	SUIState getState();

	/**
	 * @return the primary node handlers
	 * (like {@link de.ruegnerlukas.simpleapplication.simpleui.builders.MasterFxNodeBuilder},
	 * {@link de.ruegnerlukas.simpleapplication.simpleui.mutation.MasterNodeMutator})
	 */
	MasterNodeHandlers getMasterNodeHandlers();


}
