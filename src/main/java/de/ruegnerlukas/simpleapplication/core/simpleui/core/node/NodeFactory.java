package de.ruegnerlukas.simpleapplication.core.simpleui.core.node;


import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;

public interface NodeFactory {


	/**
	 * A node that will not be added to the final scene tree
	 */
	SuiNode EMPTY_NODE = null;

	/**
	 * Create a node from the given state.
	 *
	 * @param state the current state
	 * @return the created node
	 */
	SuiNode create(SuiState state, Tags tags);


}
