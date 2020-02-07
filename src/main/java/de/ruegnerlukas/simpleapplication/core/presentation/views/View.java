package de.ruegnerlukas.simpleapplication.core.presentation.views;

import javafx.scene.Parent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class View {


	/**
	 * The unique id of this view.
	 */
	private final String id;

	/**
	 * The width of this view in pixels.
	 */
	private final int width;

	/**
	 * The height of this view in pixels.
	 */
	private final int height;

	/**
	 * The title of this view.
	 */
	private final String title;

	/**
	 * The root node of this view.
	 */
	private final Parent node;

}
