package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MasterNodeMutator;
import de.ruegnerlukas.simpleapplication.simpleui.utils.SUIStateListener;
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
	 * @return the primary node handlers (like {@link MasterFxNodeBuilder}, {@link MasterNodeMutator})
	 */
	MasterNodeHandlers getMasterNodeHandlers();

	/**
	 * Applies the given state update to the state of this context.
	 * Modifies the state and updates the node tree.
	 *
	 * @param update the state update to process.
	 */
	void applyStateUpdate(SUIStateUpdate update);

	/**
	 * Adds the given listener to this context. The listener will listen to the state of this context.
	 *
	 * @param listener the listener to add
	 */
	void addStateListener(SUIStateListener listener);

	/**
	 * Removes the given listener from this context.
	 *
	 * @param listener the listener to remove
	 */
	void removeStateListener(SUIStateListener listener);

}
