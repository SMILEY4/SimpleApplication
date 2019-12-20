package de.ruegnerlukas.test.testlisteners;

import de.ruegnerlukas.simpleapplication.common.listeners.annotations.AppListener;
import de.ruegnerlukas.simpleapplication.common.listeners.annotations.OnAppClose;
import de.ruegnerlukas.simpleapplication.common.listeners.annotations.OnAppStartup;

@AppListener
public class TestListener {


	@OnAppStartup
	public void onStartup() {
		System.out.println("TestListener.onStartup");
	}




	@OnAppClose
	public void onExit() {
		System.out.println("TestListener.onExit");
	}

}
