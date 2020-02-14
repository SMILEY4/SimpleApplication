package de.ruegnerlukas.simpleapplication.core.presentation.views;

import javafx.geometry.Dimension2D;
import javafx.scene.Parent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class View {


	/**
	 * THe default min size of a view.
	 */
	public static final Dimension2D DEFAULT_SIZE_MIN = new Dimension2D(0, 0);


	/**
	 * THe default max size of a view.
	 */
	public static final Dimension2D DEFAULT_SIZE_MAX = new Dimension2D(Integer.MAX_VALUE, Integer.MAX_VALUE);


	/**
	 * The unique id of this view.
	 */
	private final String id;

	/**
	 * The size of this view in pixels.
	 */
	private final Dimension2D size;

	/**
	 * The min size of this view in pixels.
	 */
	private final Dimension2D minSize;

	/**
	 * The max size of this view in pixels.
	 */
	private final Dimension2D maxSize;

	/**
	 * The title of this view.
	 */
	private final String title;

	/**
	 * The root node of this view.
	 */
	private final Parent node;






	/**
	 * The custom builder for this view
	 */
	public static class ViewBuilder {

		// Necessary so the lombok builder includes minSize and maxSize but also uses the default values if not specified.

		/**
		 * The min size initialized with the default value.
		 */
		private Dimension2D minSize = DEFAULT_SIZE_MIN;

		/**
		 * The max size initialized with the default value
		 */
		private Dimension2D maxSize = DEFAULT_SIZE_MAX;

	}

}
