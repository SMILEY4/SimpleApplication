package de.ruegnerlukas.simpleapplication.core.presentation.views;

import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
public class WindowHandle {


	/**
	 * The id of the window handle for the primary stage.
	 */
	public static final String ID_PRIMARY = "primary";


	/**
	 * The id of this handle.
	 */
	private final String handleId;


	/**
	 * The stage
	 */
	@Getter (AccessLevel.PROTECTED)
	private final Stage stage;

	/**
	 * The view.
	 */
	@Getter (AccessLevel.PROTECTED)
	@Setter (AccessLevel.PROTECTED)
	private View view;




	/**
	 * @param handleId the id of this handle
	 * @param view     the view of this handle
	 * @param stage    the stage of this handle
	 */
	protected WindowHandle(final String handleId, final View view, final Stage stage) {
		this.handleId = handleId;
		this.view = view;
		this.stage = stage;
	}




	/**
	 * @return the id of the view of this windows handle
	 */
	public String getViewId() {
		return view.getId();
	}

}
