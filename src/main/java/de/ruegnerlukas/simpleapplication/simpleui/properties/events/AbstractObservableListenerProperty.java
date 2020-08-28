package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import javafx.beans.value.ChangeListener;
import lombok.Getter;

public abstract class AbstractObservableListenerProperty<T, O> extends AbstractEventListenerProperty<T> {


	/**
	 * The listener listening to the observable.
	 */
	@Getter
	private final ChangeListener<O> changeListener;


	/**
	 * @param key the key or type of this property.
	 */
	public AbstractObservableListenerProperty(final Class<? extends Property> key, final ChangeListener<O> changeListener) {
		super(key);
		this.changeListener = changeListener;
	}

}
