package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.events;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class ChangeListenerProxy<T> {


	/**
	 * the change listener
	 */
	private final ChangeListener<T> changeListener;

	/**
	 * the currently observing values.
	 */
	private Set<ObservableValue<T>> observables = new HashSet<>();

	/**
	 * Whether this proxy should pass on events.
	 */
	@Getter
	@Setter
	private boolean muted = false;




	/**
	 * @param listener the function called on a value change
	 */
	public ChangeListenerProxy(final BiConsumer<T, T> listener) {
		this.changeListener = (value, prev, next) -> {
			if (!isMuted()) {
				listener.accept(prev, next);
			}
		};
	}




	/**
	 * Manually triggers a change event.
	 *
	 * @param prev the previous value
	 * @param next the new value
	 */
	public void fireManually(final T prev, final T next) {
		changeListener.changed(null, prev, next);
	}




	/**
	 * Adds this change listener to the given observable value.
	 *
	 * @param observableValue the observable value to observe.
	 */
	public void addTo(final ObservableValue<T> observableValue) {
		if (!observables.contains(observableValue)) {
			observables.add(observableValue);
			observableValue.addListener(this.changeListener);
		}
	}




	/**
	 * Removes this change listener from the given observable value.
	 *
	 * @param observableValue the observable value.
	 */
	public void removeFrom(final ObservableValue<T> observableValue) {
		observables.remove(observableValue);
		observableValue.removeListener(changeListener);
	}




	/**
	 * Removes this change listener from all observable value.
	 */
	public void removeFromAll() {
		observables.forEach(observableValue -> {
			observableValue.removeListener(changeListener);
		});
		observables.clear();
	}


}
