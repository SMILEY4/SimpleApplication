package de.ruegnerlukas.simpleapplication.simpleui.core.node;

import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.stage.Modality;

import java.util.function.Function;

public class WindowRootElement {


	public static WindowRootElement windowRoot() {
		return new WindowRootElement();
	}




	public WindowRootElement title(final String title) {
		return this;
	}




	public WindowRootElement size(final Number with, final Number height) {
		return this;
	}




	public WindowRootElement modality(final Modality modality) {
		return this;
	}


	public WindowRootElement icon(final Resource resource) {
		return this;
	}



	public <T extends SuiState> WindowRootElement content(Class<T> stateType, final Function<T, NodeFactory> rootElement) {
		return this;
	}


}
