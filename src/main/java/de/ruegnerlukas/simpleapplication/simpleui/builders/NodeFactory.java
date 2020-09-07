package de.ruegnerlukas.simpleapplication.simpleui.builders;


import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.SuiState;

public interface NodeFactory {


	/**
	 * Create a node from the given state.
	 *
	 * @param state the current state
	 * @return the created node
	 */
	SuiNode create(SuiState state);


}
