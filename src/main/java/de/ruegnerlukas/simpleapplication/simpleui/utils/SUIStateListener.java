package de.ruegnerlukas.simpleapplication.simpleui.utils;

import de.ruegnerlukas.simpleapplication.simpleui.SUISceneContextListener;
import de.ruegnerlukas.simpleapplication.simpleui.SUIState;
import de.ruegnerlukas.simpleapplication.simpleui.SUIStateUpdate;

public interface SUIStateListener {


	/**
	 * Called before the state is updated by the given state update
	 *
	 * @param state  the state before the given update is applied
	 * @param update the state update that modifies the given state
	 */
	default void beforeUpdate(SUIState state, SUIStateUpdate update) {
		// do nothing by default
	}

	/**
	 * Called after the state has been updated by the given state update
	 *
	 * @param state  the state after the given update was applied
	 * @param update the state update that modified the given state
	 */
	void stateUpdated(SUIState state, SUIStateUpdate update);

	/**
	 * Adds the given listener to this context. Any listener is only added once to this context.
	 *
	 * @param listener the listener to add
	 */
	void addListener(SUISceneContextListener listener);


	/**
	 * Removes the given listener from this context.
	 *
	 * @param listener the listener to remove
	 */
	void removeListener(SUISceneContextListener listener);

}
