package de.ruegnerlukas.simpleapplication.core.presentation;

import de.ruegnerlukas.simpleapplication.core.presentation.uimodule.ModuleFactory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class PresentationConfig {


	/**
	 * The width of the window in pixels.
	 */
	private int width;

	/**
	 * The height of the window in pixels.
	 */
	private int height;

	/**
	 * The title of the window.
	 */
	private String title;

	/**
	 * The factory for the root/base module of this stage.
	 */
	private ModuleFactory baseModule;

	/**
	 * The id of this config.
	 */
	private String id;

}
