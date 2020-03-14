package de.ruegnerlukas.simpleapplication.common.instanceproviders.providers;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.RequestType;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.Optional;

public abstract class AbstractProvider<R, P> {


	/**
	 * Whether to provide instances by class-type or by name
	 */
	@Getter (AccessLevel.PROTECTED)
	private final RequestType requestType;

	/**
	 * The type of the instance this provider should provide.
	 */
	@Getter (AccessLevel.PROTECTED)
	private final Class<R> type;

	/**
	 * The name of the instance this provider should provide
	 */
	@Getter (AccessLevel.PROTECTED)
	private final String name;


	/**
	 * The instance provided by this provider.
	 */
	private P instance;




	/**
	 * @param type        the type of the instance this provider should provide.
	 * @param name        the name of the instance this provider should provide.
	 * @param requestType the {@link RequestType}
	 */
	protected AbstractProvider(final Class<R> type, final String name, final RequestType requestType) {
		this.requestType = requestType;
		this.type = type;
		this.name = name;
	}




	/**
	 * @return an instance of the type T
	 */
	public P get() {
		if (instance == null) {
			if (RequestType.BY_TYPE == getRequestType()) {
				instance = ProviderService.requestInstanceByType(getType());
			}
			if (RequestType.BY_NAME == getRequestType()) {
				instance = ProviderService.requestInstanceByName(getName());
			}
		}
		return this.instance;
	}




	/**
	 * @return an optional with the instance of the type T
	 */
	public Optional<P> getOptional() {
		return Optional.ofNullable(get());
	}


}
