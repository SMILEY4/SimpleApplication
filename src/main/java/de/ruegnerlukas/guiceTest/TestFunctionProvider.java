package de.ruegnerlukas.guiceTest;

import com.google.inject.Provider;

public class TestFunctionProvider implements Provider<TestFunction> {


	@Override
	public TestFunction get() {
		return new TestFunctionImpl("Some Text");
	}

}
