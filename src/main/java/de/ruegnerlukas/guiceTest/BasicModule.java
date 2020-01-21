package de.ruegnerlukas.guiceTest;

import com.google.inject.AbstractModule;

public class BasicModule extends AbstractModule {


	@Override
	protected void configure() {
		bind(TestFunction.class).toProvider(TestFunctionProvider.class);
//		bind(TestFunction.class).to(TestFunctionImpl.class);
	}

}
