package de.ruegnerlukas.simpleapplication.core.presentation.views;

import javafx.geometry.Dimension2D;
import javafx.scene.layout.AnchorPane;

import java.util.Set;

public class EmptyView extends View {


	/**
	 * The id of the empty view
	 */
	public static final String ID = "app.default.empty_view";

	/**
	 * The width of the empty view.
	 */
	private static final int WIDTH = 100;

	/**
	 * The height of the empty view.
	 */
	private static final int HEIGHT = 100;

	/**
	 * The title of the empty view.
	 */
	private static final String TITLE = "Application";




	/**
	 * A empty/dummy view with title, size and no content.
	 */
	public EmptyView() {
		super(ID, new Dimension2D(WIDTH, HEIGHT), DEFAULT_SIZE_MIN, DEFAULT_SIZE_MAX, TITLE, new AnchorPane(), Set.of());
	}

}
