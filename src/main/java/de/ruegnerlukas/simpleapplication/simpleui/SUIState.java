package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.utils.SUIStateListener;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

public class SUIState {


	/**
	 * Listeners listening to the state of this context.
	 */
	private List<SUIStateListener> listeners = new ArrayList<>();




	/**
	 * Modifies this state via the given update.
	 * The update will always be executed on the main javafx-thread (via {@link javafx.application.Platform#runLater(Runnable)}).
	 *
	 * @param update the update to apply to this state
	 */
	public synchronized void update(final SUIStateUpdate update) {
		Validations.INPUT.notNull(update).exception("The state update may not be null.");
		Platform.runLater(() -> updateUnsafe(update));
	}




	/**
	 * Modifies this state via the given update.
	 * The update will always be executed on the main javafx-thread (via {@link javafx.application.Platform#runLater(Runnable)}).
	 *
	 * @param update the update to apply to this state
	 * @param silent true, to not notify listeners and this not modifying the interface.
	 */
	public synchronized void update(final boolean silent, final SUIStateUpdate update) {
		Validations.INPUT.notNull(update).exception("The state update may not be null.");
		Platform.runLater(() -> updateUnsafe(silent, update));
	}




	/**
	 * Modifies this state via the given update.
	 * The update will be executed on the current thread and can cause problems with javafx if not handled otherwise.
	 *
	 * @param update the update to apply to this state
	 */
	public synchronized void updateUnsafe(final SUIStateUpdate update) {
		Validations.INPUT.notNull(update).exception("The state update may not be null.");
		updateUnsafe(false, update);
	}




	/**
	 * Modifies this state via the given update.
	 * The update will be executed on the current thread and can cause problems with javafx if not handled otherwise.
	 *
	 * @param update the update to apply to this state
	 * @param silent true, to not notify listeners and this not modifying the interface.
	 */
	public synchronized void updateUnsafe(final boolean silent, final SUIStateUpdate update) {
		Validations.INPUT.notNull(update).exception("The state update may not be null.");
		if (!silent) {
			listeners.forEach(listener -> listener.beforeUpdate(this, update));
		}
		update.doUpdate(this);
		if (!silent) {
			listeners.forEach(listener -> listener.stateUpdated(this, update));
		}
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
