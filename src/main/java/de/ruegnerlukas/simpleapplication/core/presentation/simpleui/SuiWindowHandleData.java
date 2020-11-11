package de.ruegnerlukas.simpleapplication.core.presentation.simpleui;

import de.ruegnerlukas.simpleapplication.core.presentation.views.WindowHandleData;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import javafx.scene.Parent;
import lombok.Getter;

public class SuiWindowHandleData implements WindowHandleData {


	/**
	 * The simpleui scene controller of the window handle.
	 */
	@Getter
	private final SuiSceneController controller;




	/**
	 * @param controller the simpleui scene controller
	 */
	public SuiWindowHandleData(final SuiSceneController controller) {
		this.controller = controller;
	}




	@Override
	public Parent getNode() {
		return null; // todo
//		final Node rootNode = controller.getRootFxNode();
//		Validations.STATE.typeOf(rootNode, Parent.class).exception("The root node must be of type 'Parent'.");
//		return (Parent) rootNode;
	}




	@Override
	public void dispose() {
		controller.dispose();
	}

}
