package de.ruegnerlukas.simpleapplication.common.dependencyinjection;

public class Provider<T> {


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
			instance = ProviderService.requestInstance(type, this);
		}
		return this.instance;
	}


}
