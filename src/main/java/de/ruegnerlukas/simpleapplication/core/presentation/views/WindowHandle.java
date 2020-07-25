package de.ruegnerlukas.simpleapplication.core.presentation.views;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import javafx.scene.Parent;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

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
	private View view;


	/**
	 * The data of this window handle.
	 */
	@Getter
	@Setter (AccessLevel.PROTECTED)
	private WindowHandleData data;




	/**
	 * @param handleId the id of this handle
	 * @param stage    the stage of this handle
	 */
	protected WindowHandle(final String handleId, final Stage stage) {
		this.handleId = handleId;
		this.stage = stage;
	}




	/**
	 * Sets the view of this handle and creates the window handle data from the given view.
	 * Disposes of the previous {@link WindowHandleData}.
	 *
	 * @param view the view
	 */
	protected void setView(final View view) {
		disposeCurrentData();
		this.view = view;
		this.data = view.getDataFactory().build();
		Validations.STATE.notNull(data).exception("The window handle data created by the factory may not be null.");
	}




	/**
	 * @return the id of the view of this windows handle
	 */
	public String getViewId() {
		return Optional.ofNullable(view).map(View::getId).orElse(null);
	}




	/**
	 * Disposes of the current {@link WindowHandleData} attacked to this window handle.
	 * Call when the data is switched out or when the window is closed and the data no longer needed.
	 */
	public void disposeCurrentData() {
		if (data != null) {
			data.dispose();
			data = null;
		}
	}




	/**
	 * @return the current root node of this window.
	 */
	public Parent getCurrentRootNode() {
		return stage.getScene().getRoot();
	}


}
