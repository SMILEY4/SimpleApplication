package de.ruegnerlukas.simpleapplication.common.dependencyinjection;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;

import java.util.HashMap;
import java.util.Map;

public class Provider<T> {


	/**
	 * All registered {@link ObjectFactory}s.
	 */
	private static final Map<Class<?>, ObjectFactory<?>> FACTORIES = new HashMap<>();

	/**
	 * All created singleton instances.
	 */
	private static final Map<Class<?>, Object> SINGLETON_INSTANCES = new HashMap<>();




	/**
	 * Registers the given {@link ObjectFactory}.
	 *
	 * @param factory the factory to register
	 */
	public static void registerFactory(final ObjectFactory<?> factory) {
		FACTORIES.put(factory.getProvidedType(), factory);
	}




	/**
	 * Requests an instance of the given type.
	 *
	 * @param type     the type of the requested instance
	 * @param provider the provider requesting the instance
	 * @param <T>      the Generic Type
	 * @return the requested instance
	 */
	private static <T> T requestInstance(final Class<?> type, final Provider<T> provider) {
		final ObjectFactory<?> factory = FACTORIES.get(type);
		Validations.STATE.notNull(factory).exception("No factory for type {} registered.", type);
		if (ObjectType.SINGLETON == factory.getType()) {
			if (!SINGLETON_INSTANCES.containsKey(factory.getProvidedType())) {
				SINGLETON_INSTANCES.put(factory.getProvidedType(), factory.buildObject());
			}
			return (T) SINGLETON_INSTANCES.get(factory.getProvidedType());
		} else {
			return (T) factory.buildObject();
		}
	}




	/**
	 * The type of the instance this {@link Provider} should provide.
	 */
	private final Class<T> type;

	/**
	 * The instance provided by this provider.
	 */
	private T instance;




	/**
	 * @param type the type of the instance this {@link Provider} should provide.
	 */
	public Provider(final Class<T> type) {
		this.type = type;
	}




	/**
	 * @return an instance of the type T
	 */
	public T get() {
		if (instance == null) {
			instance = Provider.requestInstance(type, this);
		}
		return this.instance;
	}


}
