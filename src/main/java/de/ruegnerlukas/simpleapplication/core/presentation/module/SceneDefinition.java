package de.ruegnerlukas.simpleapplication.core.presentation.module;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.AbstractFactory;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SceneDefinition {


	/**
	 * The unique name of this {@link SceneDefinition}.
	 */
	private final String sceneId;

	/**
	 * The title of the scene.
	 */
	private final String title;

	/**
	 * The width of the scene in pixels.
	 */
	private final int width;

	/**
	 * The height of the scene in pixels.
	 */
	private final int height;


	/**
	 * The factory building the root module of the scene.
	 */
	private final AbstractFactory<Module> rootModuleFactory;


}
