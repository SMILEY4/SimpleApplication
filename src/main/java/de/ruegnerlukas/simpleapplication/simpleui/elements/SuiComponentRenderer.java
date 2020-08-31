package de.ruegnerlukas.simpleapplication.simpleui.elements;

import de.ruegnerlukas.simpleapplication.simpleui.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;

public interface SuiComponentRenderer<T extends SuiState> {


	/**
	 * Creates a node factory dependent on the given state.
	 *
	 * @param state the state
	 * @return the created node factory
	 */
	NodeFactory render(T state);

}
