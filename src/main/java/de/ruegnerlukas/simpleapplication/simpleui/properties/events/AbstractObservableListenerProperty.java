package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import java.util.HashSet;
import java.util.Set;

public abstract class AbstractObservableListenerProperty<T, O> extends AbstractEventListenerProperty<T> {


	/**
	 * The listener listening to the observable.
	 */
	private final ChangeListener<O> changeListener;

	/**
	 * THe values the change listener is currently added to.
	 */
	private Set<ObservableValue<O>> observingValues = new HashSet<>();




	/**
	 * Adds the change listener of this property to the given observable value while making sure it is only added once.
	 *
	 * @param observableValue the value to listen to
	 */
	public void addChangeListenerTo(final ObservableValue<O> observableValue) {
		if (!observingValues.contains(observableValue)) {
			observingValues.add(observableValue);
			observableValue.addListener(changeListener);
		}
	}




	/**
	 * Removes the change listener of this property from the given observable value.
	 *
	 * @param observableValue the value to remove the listener from
	 */
	public void removeChangeListenerFrom(final ObservableValue<O> observableValue) {
		if (observingValues.contains(observableValue)) {
			observingValues.remove(observableValue);
			observableValue.removeListener(changeListener);
		}
	}




	/**
	 * @param key the key or type of this property.
	 */
	public AbstractObservableListenerProperty(final Class<? extends Property> key, final ChangeListener<O> changeListener) {
		super(key);
		this.changeListener = changeListener;
	}

}
