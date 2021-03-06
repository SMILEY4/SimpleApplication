package de.ruegnerlukas.simpleapplication.core.simpleui.core.state;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.common.tags.Tags;
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
	 * The update will always be executed on the main javafx-thread.
	 *
	 * @param stateType the type of this state (used to infer generic type)
	 * @param update    the update to apply to this state
	 */
	public synchronized <T extends SuiState> void update(final Class<T> stateType, final SuiStateUpdate<T> update) {
		Validations.INPUT.notNull(update).exception("The state update may not be null.");
		runOnJFXThread(() -> updateUnsafe(stateType, update));
	}




	/**
	 * Modifies this state via the given update.
	 * The update will always be executed on the main javafx-thread.
	 *
	 * @param stateType the type of this state (used to infer generic type)
	 * @param silent    true, to not notify listeners and this not modifying the interface.
	 * @param update    the update to apply to this state
	 */
	public synchronized <T extends SuiState> void update(final Class<T> stateType,
														 final boolean silent,
														 final SuiStateUpdate<T> update) {
		Validations.INPUT.notNull(update).exception("The state update may not be null.");
		runOnJFXThread(() -> updateUnsafe(stateType, silent, update));
	}




	/**
	 * Runs the given runnable on the javafx application thread.
	 * Either immediately if we already are on the correct thread or later via {@code Platform.runLater}.
	 *
	 * @param runnable the action to run
	 */
	private void runOnJFXThread(final Runnable runnable) {
		if (Platform.isFxApplicationThread()) {
			runnable.run();
		} else {
			Platform.runLater(runnable);
		}
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
	public synchronized <T extends SuiState> void updateUnsafe(final Class<T> stateType,
															   final boolean silent,
															   final SuiStateUpdate<T> update) {
		Validations.INPUT.notNull(update).exception("The state update may not be null.");
		if (!silent) {
			listeners.forEach(listener -> listener.beforeUpdate(this, update));
		}
		final Tags tags = doUpdate(update);
		if (!silent) {
			final List<SuiStateListener> openListeners = new ArrayList<>(listeners);
			openListeners.forEach(listener -> listener.stateUpdated(this, update, tags));
		}
	}




	/**
	 * @param update the state update
	 * @param <T>    the generic type of this state
	 * @return the tags attached to the update (or an empty list).
	 */
	private <T> Tags doUpdate(final SuiStateUpdate<T> update) {
		@SuppressWarnings ("unchecked") final T state = (T) this;
		Tags tags = Tags.empty();
		if (update instanceof TaggedSuiStateUpdate) {
			tags = ((TaggedSuiStateUpdate<T>) update).doTaggedUpdate(state);
		} else {
			update.doUpdate(state);
		}
		return tags;
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
