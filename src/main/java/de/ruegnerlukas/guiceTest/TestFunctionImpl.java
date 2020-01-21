package de.ruegnerlukas.guiceTest;

public class TestFunctionImpl implements TestFunction {


	private final String text;




	public TestFunctionImpl(String text) {
		this.text = text;
	}




	@Override
	public void print() {
		System.out.println("Hello from function: " + text);
	}

}
