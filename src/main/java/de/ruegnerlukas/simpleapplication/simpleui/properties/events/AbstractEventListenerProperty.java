package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;

import java.util.Objects;

public abstract class AbstractEventListenerProperty<T> extends Property {


	/**
	 * @param key the key or type of this property.
	 */
	public AbstractEventListenerProperty(final Class<? extends Property> key) {
		super(key);
	}




	/**
	 * @return the event listener of this property
	 */
	public abstract SUIEventListener<T> getListener();




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return Objects.equals(getListener(), ((AbstractEventListenerProperty<?>) other).getListener());
	}




	@Override
	public String printValue() {
		return getListener() != null ? getListener().toString() : "null";
	}

}
