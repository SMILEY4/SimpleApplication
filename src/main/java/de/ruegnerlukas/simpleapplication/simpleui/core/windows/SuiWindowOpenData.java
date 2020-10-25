package de.ruegnerlukas.simpleapplication.simpleui.core.windows;

import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.geometry.Dimension2D;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.function.Consumer;

@Builder
@Getter
@AllArgsConstructor
public class SuiWindowOpenData implements WindowOpenData {


	/**
	 * The size of the window.
	 */
	private final Dimension2D size;

	/**
	 * The title of this window.
	 */
	private final String title;

	/**
	 * The node factory for the root node.
	 */
	private final NodeFactory rootNodeFactory;

	/**
	 * The ui-state
	 */
	private final SuiState state;

	/**
	 * The owner of the new window
	 */
	private final Window owner;

	/**
	 * The modality of the new window.
	 */
	private final Modality modality;

	/**
	 * Whether to wait for the opened window
	 */
	private final boolean wait;

	/**
	 * A listener called when the stage is shown (called before when {@link SuiWindowOpenData#wait} is set to true).
	 */
	private final Consumer<Stage> onOpen;

	/**
	 * A listener called when the stage is closed.
	 */
	private final Consumer<Stage> onClose;

}
