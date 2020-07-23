package de.ruegnerlukas.simpleapplication.simpleui;

public interface SUISceneContextListener {


	/**
	 * Called when the simpleui root node of a context is beeing rebuild completly.
	 *
	 * @param prevRoot the previous root node
	 * @param newRoot  the new root node
	 */
	void onNewSUIRootNode(SUINode prevRoot, SUINode newRoot);


}
