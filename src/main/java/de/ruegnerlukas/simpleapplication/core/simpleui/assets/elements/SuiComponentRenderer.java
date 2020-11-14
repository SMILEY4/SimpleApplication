package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.NodeFactory;

public interface SuiComponentRenderer<T extends SuiState> {


	/**
	 * Creates a node factory dependent on the given state.
	 *
	 * @param state the state
	 * @return the created node factory
	 */
	NodeFactory render(T state);

}
