package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;

import java.util.Objects;

public abstract class AbstractEventListenerProperty<T> extends SuiProperty {


	/**
	 * @param key the key or type of this property.
	 */
	public AbstractEventListenerProperty(final Class<? extends SuiProperty> key) {
		super(key);
	}




	/**
	 * @return the event listener of this property
	 */
	public abstract SUIEventListener<T> getListener();




	@Override
	protected boolean isPropertyEqual(final SuiProperty other) {
		return Objects.equals(getListener(), ((AbstractEventListenerProperty<?>) other).getListener());
	}




	@Override
	public String printValue() {
		return String.valueOf(getListener());
	}

}
