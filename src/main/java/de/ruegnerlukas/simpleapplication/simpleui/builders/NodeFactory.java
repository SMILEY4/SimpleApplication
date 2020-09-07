package de.ruegnerlukas.simpleapplication.simpleui.builders;


import de.ruegnerlukas.simpleapplication.simpleui.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiBaseNode;

public interface NodeFactory {


	/**
	 * Create a node from the given state.
	 *
	 * @param state the current state
	 * @return the created node
	 */
	SuiBaseNode create(SuiState state);


}
