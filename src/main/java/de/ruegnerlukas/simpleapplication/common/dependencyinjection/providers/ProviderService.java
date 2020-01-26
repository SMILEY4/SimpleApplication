package de.ruegnerlukas.simpleapplication.common.dependencyinjection.providers;

import de.ruegnerlukas.simpleapplication.common.dependencyinjection.ObjectType;
import de.ruegnerlukas.simpleapplication.common.dependencyinjection.RequestType;
import de.ruegnerlukas.simpleapplication.common.dependencyinjection.factories.AbstractFactory;
import de.ruegnerlukas.simpleapplication.common.dependencyinjection.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class ProviderService {


	/**
	 * All registered {@link InstanceFactory}s.
	 */
	private static final Map<FactoryKey, AbstractFactory<?, ?>> FACTORIES = new HashMap<>();

	/**
	 * All created singleton instances.
	 */
	private static final Map<Class<?>, Object> SINGLETON_INSTANCES = new HashMap<>();




	/**
	 * Hidden constructor
	 */
	private ProviderService() {
	}




	/**
	 * Registers the given {@link InstanceFactory}.
	 *
	 * @param factory the factory to register
	 */
	public static void registerFactory(final AbstractFactory<?, ?> factory) {
		FactoryKey key;
		if (RequestType.BY_TYPE == factory.getRequestType()) {
			key = FactoryKey.fromType(factory.getProvidedType());
		} else {
			key = FactoryKey.fromName(factory.getProvidedName());
		}
		FACTORIES.put(key, factory);
	}




	/**
	 * Requests an instance of the given type.
	 *
	 * @param type the type of the requested instance
	 * @param <T>  the Generic Type
	 * @return the requested instance
	 */
	static synchronized <T> T requestInstanceByType(final Class<?> type) {
		final AbstractFactory<?, ?> factory = FACTORIES.get(FactoryKey.fromType(type));
		Validations.STATE.notNull(factory).exception("No factory for the type {} registered.", type);
		if (ObjectType.SINGLETON == factory.getObjectType()) {
			if (!SINGLETON_INSTANCES.containsKey(factory.getProvidedType())) {
				SINGLETON_INSTANCES.put(factory.getProvidedType(), factory.buildObject());
			}
			return (T) SINGLETON_INSTANCES.get(factory.getProvidedType());
		} else {
			return (T) factory.buildObject();
		}
	}




	/**
	 * Requests an instance with the given name.
	 *
	 * @param name the name of the requested instance
	 * @param <T>  the Generic Type
	 * @return the requested instance
	 */
	static synchronized <T> T requestInstanceByName(final String name) {
		final AbstractFactory<?, ?> factory = FACTORIES.get(FactoryKey.fromName(name));
		Validations.STATE.notNull(factory).exception("No factory for the name {} registered.", name);
		if (ObjectType.SINGLETON == factory.getObjectType()) {
			if (!SINGLETON_INSTANCES.containsKey(factory.getProvidedType())) {
				SINGLETON_INSTANCES.put(factory.getProvidedType(), factory.buildObject());
			}
			return (T) SINGLETON_INSTANCES.get(factory.getProvidedType());
		} else {
			return (T) factory.buildObject();
		}
	}




	private static final class FactoryKey {


		/**
		 * Creates a new {@link FactoryKey} from the given type.
		 *
		 * @param type the type of the instance/factory
		 * @return the created key
		 */
		public static FactoryKey fromType(final Class<?> type) {
			return new FactoryKey(null, type);
		}




		/**
		 * Creates a new {@link FactoryKey} from the given name.
		 *
		 * @param name the name of the instance/factory
		 * @return the created key
		 */
		public static FactoryKey fromName(final String name) {
			return new FactoryKey(name, null);
		}




		/**
		 * Used to calculate hash code.
		 */
		private static final int HASH_CODE_MULTIPLIER = 31;

		/**
		 * The name of the instance (or null)
		 */
		private final String name;

		/**
		 * The type of the instance (or null)
		 */
		private final Class<?> type;




		/**
		 * @param name the name of the instance (or null)
		 * @param type the type of the instance (or null)
		 */
		private FactoryKey(final String name, final Class<?> type) {
			this.name = name;
			this.type = type;
		}




		@Override
		public boolean equals(final Object o) {
			if (this == o) {
				return true;
			}
			if (o == null || getClass() != o.getClass()) {
				return false;
			}
			FactoryKey that = (FactoryKey) o;
			if (!Objects.equals(name, that.name)) {
				return false;
			}
			return Objects.equals(type, that.type);
		}




		@Override
		public int hashCode() {
			int result = name != null ? name.hashCode() : 0;
			result = HASH_CODE_MULTIPLIER * result + (type != null ? type.hashCode() : 0);
			return result;
		}

	}

}
