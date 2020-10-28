package de.ruegnerlukas.simpleapplication.simpleui.core.windows;

import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.stage.Modality;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WindowConfig {

	/**
	 * The unique id of the window
	 */
	private final String windowId;

	/**
	 * The title of the window
	 */
	private final String title;

	/**
	 * The initial width of the window
	 */
	private final double width;

	/**
	 * The initial height of the window
	 */
	private final double height;

	/**
	 * Whether the process should wait for the window to close
	 */
	private final boolean wait;

	/**
	 * The window modality
	 */
	private final Modality modality;

	/**
	 * The window owning the new window (or null)
	 */
	private final String ownerWindowId;

	/**
	 * the simpleui state
	 */
	private SuiState state;

	/**
	 * the node factory of the root node
	 */
	private NodeFactory nodeFactory;

	/**
	 * An optional action executed when the window closes
	 */
	private Runnable onClose;

}
