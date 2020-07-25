package de.ruegnerlukas.simpleapplication.simpleui;

public interface SUIStateUpdate<T> {


	/**
	 * Update the given state.
	 *
	 * @param state the state to modify
	 */
	void doUpdate(T state);

}
