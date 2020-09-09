package de.ruegnerlukas.simpleapplication.simpleui.core;

import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;

public interface SuiSceneControllerListener {


	/**
	 * Called when the simpleui root node of a context is being rebuild completely.
	 *
	 * @param newRoot the new root node
	 */
	void onNewSuiRootNode(SuiNode newRoot);


}
