package de.ruegnerlukas.guiceTest;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class TestMain {


	public static void main(String[] args) {
		// Add following VM Option to resolve Illegal-reflection-access-exception-thingy
		// --add-opens java.base/java.lang=com.google.guice
		Injector injector = Guice.createInjector(new BasicModule());
		TestClass test = injector.getInstance(TestClass.class);
		test.test();
	}

}
