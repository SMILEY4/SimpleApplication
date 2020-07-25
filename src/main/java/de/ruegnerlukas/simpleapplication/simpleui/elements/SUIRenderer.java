package de.ruegnerlukas.simpleapplication.simpleui.elements;

import de.ruegnerlukas.simpleapplication.simpleui.SUIState;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;

public interface SUIRenderer<T extends SUIState> {


	/**
	 * Creates a node factory dependent on the given state.
	 *
	 * @param state the state
	 * @return the created node factory
	 */
	NodeFactory render(T state);

}
