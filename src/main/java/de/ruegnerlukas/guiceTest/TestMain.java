package de.ruegnerlukas.guiceTest;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class TestMain {


	public static void main(String[] args) {
		Injector injector = Guice.createInjector(new BasicModule());
		TestClass test = injector.getInstance(TestClass.class);
		test.test();
	}

}
