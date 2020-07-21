package de.ruegnerlukas.simpleapplication.core.presentation.simpleui;

import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewNodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.SUISceneContext;
import javafx.scene.Parent;

import java.util.Optional;

public class SUIViewNodeFactory implements ViewNodeFactory {


	/**
	 * The factory to create the simpleui scene context
	 */
	private final SUISceneContextFactory sceneContextFactory;

	/**
	 * The simpleui scene context (if created by the factory)
	 */
	private SUISceneContext sceneContext;




	/**
	 * @param sceneContextFactory the factory to create the simpleui scene context
	 */
	public SUIViewNodeFactory(final SUISceneContextFactory sceneContextFactory) {
		this.sceneContextFactory = sceneContextFactory;
	}




	/**
	 * @return the simpleui scene context (if available) created by the scene context factory
	 */
	public Optional<SUISceneContext> getSceneContext() {
		return Optional.ofNullable(sceneContext);
	}




	@Override
	public Parent buildNode() {
		if (sceneContext == null) {
			sceneContext = sceneContextFactory.build();
		}
		// TODO validations:
		//  - root fx node must be of type "parent"
		//  - root node can not be rebuild during mutation
		return (Parent) sceneContext.getRootFxNode();
	}

}
