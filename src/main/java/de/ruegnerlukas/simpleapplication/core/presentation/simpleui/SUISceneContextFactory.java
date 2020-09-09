package de.ruegnerlukas.simpleapplication.core.presentation.simpleui;

import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;

public interface SUISceneContextFactory {


	/**
	 * @return the created simpleui scene context
	 */
	SuiSceneController build();

}
