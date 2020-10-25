package de.ruegnerlukas.simpleapplication.core.presentation.simpleui;

import de.ruegnerlukas.simpleapplication.core.presentation.views.WindowHandleData;
import de.ruegnerlukas.simpleapplication.core.presentation.views.WindowHandleDataFactory;

public class SUIWindowHandleDataFactory implements WindowHandleDataFactory {


	/**
	 * The factory to create the simpleui scene context
	 */
	private final SUISceneContextFactory sceneContextFactory;




	/**
	 * @param sceneContextFactory the factory to create the simpleui scene context
	 */
	public SUIWindowHandleDataFactory(final SUISceneContextFactory sceneContextFactory) {
		this.sceneContextFactory = sceneContextFactory;
	}




	@Override
	public WindowHandleData build() {
		return new SuiWindowHandleData(sceneContextFactory.build());
	}

}
