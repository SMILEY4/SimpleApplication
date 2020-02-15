package de.ruegnerlukas.simpleapplication.common.instanceproviders.providers;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.ObjectType;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.RequestType;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.GenericFactory;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public final class ProviderService {


	/**
	 * All registered {@link GenericFactory}s.
	 */
	private static final Map<FactoryKey, GenericFactory<?, ?>> FACTORIES = new HashMap<>();

	/**
	 * All created singleton instances.
	 */
	private static final Map<FactoryKey, Object> SINGLETON_INSTANCES = new HashMap<>();




	/**
	 * Hidden constructor
	 */
	private ProviderService() {
	}




	/**
	 * Registers the given factory.
	 *
	 * @param factory the factory to register
	 */
	public static void registerFactory(final GenericFactory<?, ?> factory) {
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
		final FactoryKey factoryKey = FactoryKey.fromType(type);
		final GenericFactory<?, ?> factory = FACTORIES.get(factoryKey);
		Validations.STATE.notNull(factory).exception("No factory for the type {} registered.", type);
		if (ObjectType.SINGLETON == factory.getObjectType()) {
			if (!SINGLETON_INSTANCES.containsKey(factoryKey)) {
				SINGLETON_INSTANCES.put(factoryKey, factory.buildObject());
			}
			return (T) SINGLETON_INSTANCES.get(factoryKey);
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
		final FactoryKey factoryKey = FactoryKey.fromName(name);
		final GenericFactory<?, ?> factory = FACTORIES.get(factoryKey);
		Validations.STATE.notNull(factory).exception("No factory for the name {} registered.", name);
		if (ObjectType.SINGLETON == factory.getObjectType()) {
			if (!SINGLETON_INSTANCES.containsKey(factoryKey)) {
				SINGLETON_INSTANCES.put(factoryKey, factory.buildObject());
			}
			return (T) SINGLETON_INSTANCES.get(factoryKey);
		} else {
			return (T) factory.buildObject();
		}
	}




	/**
	 * Resets the {@link ProviderService} including removing all registered factories and all created instances.
	 */
	public static void cleanup() {
		FACTORIES.clear();
		SINGLETON_INSTANCES.clear();
	}



	private static final class FactoryKey {


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
