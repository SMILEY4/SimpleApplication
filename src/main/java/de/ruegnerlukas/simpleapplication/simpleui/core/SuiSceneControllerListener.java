package de.ruegnerlukas.simpleapplication.simpleui.core;

import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;

public interface SuiSceneControllerListener {


	/**
	 * Called when the simpleui root node of a context is being rebuild completely.
	 *
	 * @param newRoot the new root node
	 */
	void onNewSuiRootNode(SuiBaseNode newRoot);


}
