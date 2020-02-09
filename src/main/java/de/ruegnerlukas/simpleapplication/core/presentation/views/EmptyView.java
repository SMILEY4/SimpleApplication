package de.ruegnerlukas.simpleapplication.core.presentation.views;

import javafx.scene.layout.AnchorPane;

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
		super(ID, WIDTH, HEIGHT, TITLE, new AnchorPane());
	}

}
