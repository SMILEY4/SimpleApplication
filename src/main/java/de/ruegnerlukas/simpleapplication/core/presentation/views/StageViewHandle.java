package de.ruegnerlukas.simpleapplication.core.presentation.views;

import javafx.stage.Stage;
import lombok.Getter;

@Getter
public class StageViewHandle extends ViewHandle {


	/**
	 * The stage showing the view.
	 */
	private final Stage stage;




	/**
	 * @param handleId the id of this view handle
	 * @param view     the view
	 * @param stage    the stage showing the view
	 */
	public StageViewHandle(final String handleId, final View view, final Stage stage) {
		super(handleId, view);
		this.stage = stage;
	}

}
