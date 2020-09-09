package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;

import java.util.function.BiFunction;

public abstract class AbstractEventListenerProperty<T> extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	@SuppressWarnings ("rawtypes")
	private static final BiFunction<AbstractEventListenerProperty, AbstractEventListenerProperty, Boolean> COMPARATOR =
			(a, b) -> a.getListener().equals(b.getListener());




	/**
	 * @param key the key or type of this property.
	 */
	public AbstractEventListenerProperty(final Class<? extends SuiProperty> key) {
		super(key, COMPARATOR);
	}




	/**
	 * @return the event listener of this property
	 */
	public abstract SUIEventListener<T> getListener();


}
