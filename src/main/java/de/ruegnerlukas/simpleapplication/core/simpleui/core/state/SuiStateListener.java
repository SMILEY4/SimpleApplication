package de.ruegnerlukas.simpleapplication.core.simpleui.core.state;

import de.ruegnerlukas.simpleapplication.core.simpleui.core.tags.Tags;

public interface SuiStateListener {


	/**
	 * Called before the state is updated by the given state update
	 *
	 * @param state  the state before the given update is applied
	 * @param update the state update that modifies the given state
	 */
	default void beforeUpdate(SuiState state, SuiStateUpdate<?> update) {
		// do nothing by default
	}

	/**
	 * Called after the state has been updated by the given state update
	 *
	 * @param state  the state after the given update was applied
	 * @param update the state update that modified the given state
	 * @param tags   the tags attached to the given state update
	 */
	void stateUpdated(SuiState state, SuiStateUpdate<?> update, Tags tags);


}
