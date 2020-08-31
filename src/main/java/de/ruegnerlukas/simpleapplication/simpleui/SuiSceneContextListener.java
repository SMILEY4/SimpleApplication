package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;

public interface SuiSceneContextListener {


	/**
	 * Called when the simpleui root node of a context is beeing rebuild completly.
	 *
	 * @param prevRoot the previous root node
	 * @param newRoot  the new root node
	 */
	void onNewSUIRootNode(SuiNode prevRoot, SuiNode newRoot);


}
