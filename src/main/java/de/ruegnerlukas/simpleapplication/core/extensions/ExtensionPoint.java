package de.ruegnerlukas.simpleapplication.core.extensions;

import de.ruegnerlukas.simpleapplication.common.events.EventListener;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
public class ExtensionPoint {


	/**
	 * The unique id of this extension point.
	 */
	@Getter
	private final String id;

	/**
	 * The supported types and listeners.
	 */
	private final Map<Class<?>, EventListener> listeners = new HashMap<>();

	/**
	 * The supported types that allow null values.
	 */
	private final Set<Class<?>> typesAllowNull = new HashSet<>();




	/**
	 * @param id the unique id of this extension point
	 */
	public ExtensionPoint(final String id) {
		this.id = id;
	}




	/**
	 * Adds a new type that can be passed to this extension point.
	 * When an object of the given type is passed to this point, the given listener is called.
	 *
	 * @param type     the type of the supported object
	 * @param listener the listener listening for objects of the given type
	 * @param <T>      the generic type
	 */
	public <T> void addSupportedType(final Class<T> type, final EventListener<T> listener) {
		Validations.INPUT.notNull(type).exception("The type may not be null.");
		Validations.INPUT.notNull(listener).exception("The listener may not be null.");
		if (listeners.containsKey(type)) {
			log.warn("The type '{}' is already supported by the extension point with the id '{}'. It will not be added again.",
					type.getName(), this.getId());
		} else {
			listeners.put(type, listener);
		}
	}




	/**
	 * Adds a new type that can be passed to this extension point. The passed value is allowed to be null.
	 * When an object of the given type is passed to this point, the given listener is called.
	 *
	 * @param type     the type of the supported object
	 * @param listener the listener listening for objects of the given type
	 * @param <T>      the generic type
	 */
	public <T> void addSupportedTypeAllowNull(final Class<T> type, final EventListener<T> listener) {
		Validations.INPUT.notNull(type).exception("The type may not be null.");
		Validations.INPUT.notNull(listener).exception("The listener may not be null.");
		if (listeners.containsKey(type)) {
			log.warn("The type '{}' is already supported by the extension point with the id '{}'. It will not be added again.",
					type.getName(), this.getId());
		} else {
			listeners.put(type, listener);
			typesAllowNull.add(type);
		}
	}




	/**
	 * @return a list of all types supported by this extension point.
	 */
	public List<Class<?>> getSupportedTypes() {
		return new ArrayList<>(listeners.keySet());
	}




	/**
	 * Check if the given type is supported by this extension point.
	 *
	 * @param type the type to check
	 * @return whether the given type is supported by this extension point.
	 */
	public boolean isSupported(final Class<?> type) {
		return listeners.containsKey(type);
	}




	/**
	 * Check if the given type allows null values.
	 *
	 * @param type the type to check
	 * @return whether the given type allows null values.
	 */
	public boolean allowsNull(final Class<?> type) {
		return typesAllowNull.contains(type);
	}




	/**
	 * Pass the given data to the extension point.
	 *
	 * @param type the type of the provided data
	 * @param data the data to provide
	 * @param <T>  the generic type
	 */
	public <T> void provide(final Class<T> type, final Object data) {
		Validations.INPUT.notNull(type).exception("The type may not be null.");
		Validations.INPUT.typeOf(data, type, allowsNull(type))
				.exception("The provided object is not of the same type '{}': {}.", type, data);
		if (isSupported(type)) {
			listeners.get(type).onEvent(data);
		} else {
			log.warn("The type '{}' is not supported by this extension point.", type);
		}
	}


}
