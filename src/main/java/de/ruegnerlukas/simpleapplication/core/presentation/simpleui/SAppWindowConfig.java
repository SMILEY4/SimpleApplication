package de.ruegnerlukas.simpleapplication.core.presentation.simpleui;

import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.core.windows.WindowConfig;
import javafx.geometry.Dimension2D;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SAppWindowConfig implements WindowConfig {


	/**
	 * The id of the view. If no view with the given id exists, a new one will be created and registered.
	 */
	private final String viewId;

	/**
	 * The unique id of the window
	 */
	private final String windowId;

	/**
	 * The title of the window
	 */
	private final String title;

	/**
	 * The minimum width and height of the window
	 */
	private final Dimension2D sizeMin;

	/**
	 * The initial width and height of the window
	 */
	private final Dimension2D size;

	/**
	 * The maximum width and height of the window
	 */
	private final Dimension2D sizeMax;

	/**
	 * Whether the process should wait for the window to close
	 */
	private final boolean wait;

	/**
	 * The window modality
	 */
	private final Modality modality;

	/**
	 * The style of the stage.
	 */
	private final StageStyle windowStyle;

	/**
	 * Whether the window should be always on top. Default is false
	 */
	private final boolean alwaysOnTop;

	/**
	 * The window-id of the window owning the new one (or null).
	 * {@link de.ruegnerlukas.simpleapplication.core.presentation.views.WindowHandle#ID_PRIMARY} can be used for the primary window.
	 */
	private final String ownerWindowId;

	/**
	 * the simpleui state
	 */
	private final SuiState state;

	/**
	 * the node factory of the root node
	 */
	private final NodeFactory nodeFactory;

	/**
	 * An optional action executed when the window closes
	 */
	private final Runnable onClose;

}
