package de.ruegnerlukas.test;

import de.ruegnerlukas.simpleapplication.SimpleApplication;

public final class TestApplication {


	/**
	 * Utility class
	 */
	private TestApplication() {
	}




	/**
	 * The main.
	 */
	public static void main(final String[] args) {
		SimpleApplication.setProjectRootPackage("de.ruegnerlukas");
		SimpleApplication.startApplication();
		SimpleApplication.closeApplication();
	}


}
