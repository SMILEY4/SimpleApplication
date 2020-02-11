package de.ruegnerlukas.simpleapplication.core.presentation.views;

import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;

@Getter
public class ViewHandle {


	/**
	 * The id of the view handle for the primary stage.
	 */
	public static final String ID_PRIMARY = "primary";


	/**
	 * The id of this handle.
	 */
	private final String handleId;

	/**
	 * The id of the view.
	 */
	private final String viewId;


	/**
	 * The view.
	 */
	@Getter (AccessLevel.PROTECTED)
	private final View view;




	/**
	 * @param handleId the id of this view handle
	 * @param view     the view
	 */
	public ViewHandle(final String handleId, final View view) {
		this.handleId = handleId;
		this.view = view;
		this.viewId = view.getId();
	}




	/**
	 * @return the stage of this view handle
	 */
	protected Stage getStage() {
		return null;
	}




	@Override
	public boolean equals(final Object other) {
		if (this == other) {
			return true;
		}
		if (other == null || getClass() != other.getClass()) {
			return false;
		}
		final ViewHandle otherHandle = (ViewHandle) other;
		return handleId.equals(otherHandle.handleId);
	}




	@Override
	public int hashCode() {
		return handleId.hashCode();
	}


}
