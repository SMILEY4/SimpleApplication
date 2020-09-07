package de.ruegnerlukas.simpleapplication.core.presentation.simpleui;

import de.ruegnerlukas.simpleapplication.core.presentation.views.WindowHandleData;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import javafx.scene.Parent;
import lombok.Getter;

public class SUIWindowHandleData implements WindowHandleData {


	/**
	 * The simpleui scene context of the window handle.
	 */
	@Getter
	private final SuiSceneController sceneContext;




	/**
	 * @param sceneContext the simpleui scene context
	 */
	public SUIWindowHandleData(final SuiSceneController sceneContext) {
		this.sceneContext = sceneContext;
	}




	@Override
	public Parent getNode() {
		// TODO validations:
		//  - root fx node must be of type "parent"
		//  - root node can not be rebuild during mutation
		return (Parent) sceneContext.getRootFxNode();
	}




	@Override
	public void dispose() {
		// TODO maybe remove interface of scene context
		sceneContext.getState().removeStateListener((SuiSceneController) sceneContext);
	}

}
