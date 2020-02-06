package de.ruegnerlukas.simpleapplication.common.instanceproviders;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.GenericFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ProviderConfiguration {


	private final List<GenericFactory<?, ?>> factories = new ArrayList<>();




	protected void add(final GenericFactory<?, ?> factory) {
		factories.add(factory);
	}




	public List<GenericFactory<?, ?>> getFactories() {
		return Collections.unmodifiableList(factories);
	}




	public abstract void configure();

}
