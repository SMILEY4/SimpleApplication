package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.simpleui.utils.SUIStateListener;

public interface SUIState {


	/**
	 * Modifies this state via the given update.
	 * The update is executed by the linked scene context.
	 *
	 * @param update the state update
	 */
	void update(SUIStateUpdate update);


	/**
	 * Adds the given listener to this state. Any listener is only added once to this state.
	 *
	 * @param listener the listener to add
	 */
	void addStateListener(SUIStateListener listener);

	/**
	 * Removes the given listener from this state.
	 *
	 * @param listener the listener to remove
	 */
	void removeStateListener(SUIStateListener listener);

}
