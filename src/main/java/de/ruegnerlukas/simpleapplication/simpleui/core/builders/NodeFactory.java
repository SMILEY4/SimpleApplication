package de.ruegnerlukas.simpleapplication.simpleui.core.builders;


import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;

public interface NodeFactory {


	/**
	 * Create a node from the given state.
	 *
	 * @param state the current state
	 * @return the created node
	 */
	SuiBaseNode create(SuiState state);


}
