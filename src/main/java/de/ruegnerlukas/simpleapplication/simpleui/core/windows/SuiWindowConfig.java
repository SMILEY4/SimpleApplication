package de.ruegnerlukas.simpleapplication.simpleui.core.windows;

import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.geometry.Dimension2D;
import javafx.stage.Modality;
import javafx.stage.StageStyle;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SuiWindowConfig implements WindowConfig {

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
	 * The window-id of the window owning the new one (or null)
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
