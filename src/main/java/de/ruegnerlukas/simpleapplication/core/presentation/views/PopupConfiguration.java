package de.ruegnerlukas.simpleapplication.core.presentation.views;

import javafx.stage.Modality;
import javafx.stage.StageStyle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PopupConfiguration {


	/**
	 * The {@link WindowHandle} of the parent stage/popup.
	 * Set to null to use the primary window handle
	 */
	private final WindowHandle parent;

	/**
	 * The modality for the stage. Default is {@link Modality#WINDOW_MODAL}.
	 */
	private final Modality modality;

	/**
	 * The style of the stage. Default is {@link StageStyle#DECORATED}.
	 */
	private final StageStyle style;

	/**
	 * Whether to wait for the popup to close. Default is false.
	 */
	private final boolean wait;

	/**
	 * Whether the window should be always on top. Default is false
	 */
	private final boolean alwaysOnTop;






	/**
	 * The custom builder for this popup configuration
	 */
	public static class PopupConfigurationBuilder {

		// Necessary so the lombok builder includes specific fields but also uses the default values if not specified.

		/**
		 * The modality for the stage.
		 */
		private Modality modality = Modality.WINDOW_MODAL;

		/**
		 * The style of the stage.
		 */
		private StageStyle style = StageStyle.DECORATED;

		/**
		 * Whether the window should be always on top.
		 */
		private boolean alwaysOnTop = false;

	}


}
