package de.ruegnerlukas.simpleapplication.simpleui.core.builders;


import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;

public interface NodeFactory {


	/**
	 * Create a node from the given state.
	 *
	 * @param state the current state
	 * @return the created node
	 */
	SuiNode create(SuiState state);


}
