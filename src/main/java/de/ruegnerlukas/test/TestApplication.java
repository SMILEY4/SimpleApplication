package de.ruegnerlukas.test;

import de.ruegnerlukas.simpleapplication.SimpleApplication;
import de.ruegnerlukas.simpleapplication.common.listeners.annotations.AppListener;
import de.ruegnerlukas.simpleapplication.common.listeners.annotations.OnAppStartup;

public class TestApplication {


	public static void main(String[] args) {
		SimpleApplication.setProjectRootPackage("de.ruegnerlukas");
		SimpleApplication.startApplication();
		SimpleApplication.closeApplication();
	}




	@AppListener
	public static class InnerListener {


		@OnAppStartup
		public void onStart1() {
			System.out.println("InnerListener.onStart1");
		}




		@OnAppStartup
		public void onStart2() {
			System.out.println("InnerListener.onStart2");
		}


	}


}
