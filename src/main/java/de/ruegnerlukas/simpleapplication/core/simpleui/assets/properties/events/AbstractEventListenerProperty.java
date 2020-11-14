package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.core.simpleui.core.events.SuiEventListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;

import java.util.function.BiFunction;

public abstract class AbstractEventListenerProperty<T> extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	@SuppressWarnings ("rawtypes")
	private static final BiFunction<AbstractEventListenerProperty, AbstractEventListenerProperty, Boolean> COMPARATOR =
			(a, b) -> a.getListener().equals(b.getListener());




	/**
	 * @param key        the key or type of this property.
	 * @param propertyId see {@link SuiProperty#getPropertyId()}.
	 */
	public AbstractEventListenerProperty(final Class<? extends SuiProperty> key, final String propertyId) {
		super(key, COMPARATOR, propertyId);
	}




	/**
	 * @return the event listener of this property
	 */
	public abstract SuiEventListener<T> getListener();


}
