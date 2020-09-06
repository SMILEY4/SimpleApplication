package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.HashSet;
import java.util.Set;
import java.util.function.BiConsumer;

public class ChangeListenerProxy<T> {


	/***/
	private final BiConsumer<T, T> listener;

	/***/
	private final ChangeListener<T> changeListener;

	/***/
	private Set<ObservableValue<T>> observables = new HashSet<>();




	/***/
	public ChangeListenerProxy(final BiConsumer<T, T> listener) {
		this.listener = listener;
		this.changeListener = (value, prev, next) -> listener.accept(prev, next);
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
