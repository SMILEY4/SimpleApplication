package de.ruegnerlukas.simpleapplication.simpleui.core.windows;

import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.stage.Modality;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class WindowConfig {


	private final String windowId;

	private final String title;

	private final double width;

	private final double height;

	private final boolean wait;

	private final Modality modality;

	private final String ownerWindowId;

	private SuiState state;

	private NodeFactory nodeFactory;

	private Runnable onClose;

}
