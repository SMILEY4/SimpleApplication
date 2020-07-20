package de.ruegnerlukas.simpleapplication.core.presentation.simpleui;

import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewNodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.SUISceneContext;
import javafx.scene.Parent;

public class SimpleUIViewNodeFactory implements ViewNodeFactory {


	/**
	 * The simpleui scene context
	 */
	private final SUISceneContext context;




	/**
	 * @param context the simpleui scene context
	 */
	public SimpleUIViewNodeFactory(final SUISceneContext context) {
		this.context = context;
	}




	@Override
	public Parent buildNode() {
		// TODO: make sure that root node can never change (through mutation) and is always a "parent".
		return (Parent) this.context.getRootNode().getFxNode();
	}

}
