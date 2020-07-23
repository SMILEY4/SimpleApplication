package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.utils.SUIStateListener;

import java.util.ArrayList;
import java.util.List;

public class SUIState {


	/**
	 * Listeners listening to the state of this context.
	 */
	private List<SUIStateListener> listeners = new ArrayList<>();




	/**
	 * Modifies this state via the given update.
	 * The update is executed by the linked scene context.
	 *
	 * @param update the state update
	 */
	public void update(final SUIStateUpdate update) {
		Validations.INPUT.notNull(update).exception("The state update may not be null.");
		listeners.forEach(listener -> listener.beforeUpdate(this, update));
		update.doUpdate(this);
		listeners.forEach(listener -> listener.stateUpdated(this, update));
	}




	/**
	 * Adds the given listener to this state. Any listener is only added once to this state.
	 *
	 * @param listener the listener to add
	 */
	public void addStateListener(final SUIStateListener listener) {
		Validations.INPUT.notNull(listener).exception("The state listener to add may not be null.");
		if (!listeners.contains(listener)) {
			listeners.add(listener);
		}
	}




	/**
	 * Removes the given listener from this state.
	 *
	 * @param listener the listener to remove
	 */
	public void removeStateListener(final SUIStateListener listener) {
		listeners.remove(listener);
	}

}
