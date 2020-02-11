package de.ruegnerlukas.simpleapplication.core.presentation.views;

import javafx.stage.Stage;
import lombok.Getter;

@Getter
public class PopupViewHandle extends ViewHandle {


	/**
	 * The popup showing the view.
	 */
	private final Popup popup;




	/**
	 * @param handleId the id of this view handle
	 * @param view     the view
	 * @param popup    the popup showing the view
	 */
	public PopupViewHandle(final String handleId, final View view, final Popup popup) {
		super(handleId, view);
		this.popup = popup;
	}




	@Override
	protected Stage getStage() {
		return popup.getStage();
	}

}
