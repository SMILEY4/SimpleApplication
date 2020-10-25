package de.ruegnerlukas.simpleapplication.simpleui.core.windows;

import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class SuiWindowCloseData implements WindowCloseData {


	/**
	 * The window to close
	 */
	private final Stage window;


}
