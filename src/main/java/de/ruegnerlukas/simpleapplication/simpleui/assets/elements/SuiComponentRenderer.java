package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;

public interface SuiComponentRenderer<T extends SuiState> {


	/**
	 * Creates a node factory dependent on the given state.
	 *
	 * @param state the state
	 * @return the created node factory
	 */
	NodeFactory render(T state);

}
