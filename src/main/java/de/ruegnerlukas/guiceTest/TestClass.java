package de.ruegnerlukas.guiceTest;

import com.google.inject.Inject;

public class TestClass {


	@Inject
	private TestFunction function;




	public void test() {
		function.print();
	}

}
