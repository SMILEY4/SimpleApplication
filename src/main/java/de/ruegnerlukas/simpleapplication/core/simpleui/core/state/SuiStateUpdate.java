package de.ruegnerlukas.simpleapplication.core.simpleui.core.state;

public interface SuiStateUpdate<T> {


	/**
	 * Update the given state.
	 *
	 * @param state the state to modify
	 */
	void doUpdate(T state);

}
