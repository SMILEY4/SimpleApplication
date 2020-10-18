package de.ruegnerlukas.simpleapplication.simpleui.core.node;


import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

public interface NodeFactory {


	/**
	 * Create a node from the given state.
	 *
	 * @param state the current state
	 * @return the created node
	 */
	SuiNode create(SuiState state, Tags tags);


}
