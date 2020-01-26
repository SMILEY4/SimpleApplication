package de.ruegnerlukas.simpleapplication.common.dependencyinjection;

public class Provider<T> {


	/**
	 * Whether to provide instances by class-type or by name
	 */
	private final RequestType requestType;

	/**
	 * The type of the instance this {@link Provider} should provide.
	 */
	private final Class<T> type;

	/**
	 * The name of the instance this {@link Provider} should provide
	 */
	private final String name;

	/**
	 * The instance provided by this provider.
	 */
	private T instance;




	/**
	 * @param type the type of the instance this {@link Provider} should provide.
	 */
	public Provider(final Class<T> type) {
		this(type, null, RequestType.BY_TYPE);
	}




	/**
	 * @param name the name of the instance this {@link Provider} should provide.
	 */
	public Provider(final String name) {
		this(null, name, RequestType.BY_NAME);
	}




	/**
	 * @param type         the type of the instance this {@link Provider} should provide.
	 * @param name         the name of the instance this {@link Provider} should provide.
	 * @param requestType the {@link RequestType}
	 */
	private Provider(final Class<T> type, final String name, final RequestType requestType) {
		this.requestType = requestType;
		this.type = type;
		this.name = name;
	}




	/**
	 * @return an instance of the type T
	 */
	public T get() {
		if (instance == null) {
			if (RequestType.BY_TYPE == requestType) {
				instance = ProviderService.requestInstanceByType(type);
			}
			if (RequestType.BY_NAME == requestType) {
				instance = ProviderService.requestInstanceByName(name);
			}
		}
		return this.instance;
	}


}
