package de.ruegnerlukas.simpleapplication.common.instanceproviders;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.GenericFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ProviderConfiguration {


	/**
	 * All added factories.
	 */
	private final List<GenericFactory<?, ?>> factories = new ArrayList<>();




	/**
	 * Adds the given factory to this configuration.
	 *
	 * @param factory the {@link GenericFactory} to add.
	 */
	protected void add(final GenericFactory<?, ?> factory) {
		factories.add(factory);
	}




	/**
	 * @return An unmodifiable list of all factories of this configuration.
	 */
	public List<GenericFactory<?, ?>> getFactories() {
		return Collections.unmodifiableList(factories);
	}




	/**
	 * Configure and add all factories of this configuration here.
	 */
	public abstract void configure();

}
