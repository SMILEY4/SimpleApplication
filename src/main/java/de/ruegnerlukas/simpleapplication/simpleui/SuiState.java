package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SuiState {


	/**
	 * Listeners listening to the state of this context.
	 */
	private List<SuiStateListener> listeners = new ArrayList<>();




	/**
	 * Modifies this state via the given update.
	 * The update will always be executed on the main javafx-thread (via {@link javafx.application.Platform#runLater(Runnable)}).
	 *
	 * @param stateType the type of this state (used to infer generic type)
	 * @param update    the update to apply to this state
	 */
	public synchronized <T extends SuiState> void update(final Class<T> stateType, final SuiStateUpdate<T> update) {
		Validations.INPUT.notNull(update).exception("The state update may not be null.");
		Platform.runLater(() -> updateUnsafe(stateType, update));
	}




	/**
	 * Modifies this state via the given update.
	 * The update will always be executed on the main javafx-thread (via {@link javafx.application.Platform#runLater(Runnable)}).
	 *
	 * @param stateType the type of this state (used to infer generic type)
	 * @param silent    true, to not notify listeners and this not modifying the interface.
	 * @param update    the update to apply to this state
	 */
	public synchronized <T extends SuiState> void update(
			final Class<T> stateType, final boolean silent, final SuiStateUpdate<T> update) {
		Validations.INPUT.notNull(update).exception("The state update may not be null.");
		Platform.runLater(() -> updateUnsafe(stateType, silent, update));
	}




	/**
	 * Modifies this state via the given update.
	 * The update will be executed on the current thread and can cause problems with javafx if not handled otherwise.
	 *
	 * @param stateType the type of this state (used to infer generic type)
	 * @param update    the update to apply to this state
	 */
	public synchronized <T extends SuiState> void updateUnsafe(final Class<T> stateType, final SuiStateUpdate<T> update) {
		Validations.INPUT.notNull(update).exception("The state update may not be null.");
		updateUnsafe(stateType, false, update);
	}




	/**
	 * Modifies this state via the given update.
	 * The update will be executed on the current thread and can cause problems with javafx if not handled otherwise.
	 *
	 * @param stateType the type of this state (used to infer generic type)
	 * @param silent    true, to not notify listeners and this not modifying the interface.
	 * @param update    the update to apply to this state
	 */
	public synchronized <T extends SuiState> void updateUnsafe(
			final Class<T> stateType, final boolean silent, final SuiStateUpdate<T> update) {
		Validations.INPUT.notNull(update).exception("The state update may not be null.");
		if (!silent) {
			listeners.forEach(listener -> listener.beforeUpdate(this, update));
		}
		update.doUpdate((T) this);
		if (!silent) {
			listeners.forEach(listener -> listener.stateUpdated(this, update));
		}
	}




	/**
	 * Adds the given listener to this state. Any listener is only added once to this state.
	 *
	 * @param listener the listener to add
	 */
	public void addStateListener(final SuiStateListener listener) {
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
	public void removeStateListener(final SuiStateListener listener) {
		listeners.remove(listener);
	}




	/**
	 * @return an unmodifiable list of the listeners listening to this state.
	 */
	public List<SuiStateListener> getListeners() {
		return Collections.unmodifiableList(listeners);
	}


}
