package de.ruegnerlukas.simpleapplication.core.presentation.simpleui.windowmanager;

import de.ruegnerlukas.simpleapplication.core.presentation.views.PopupConfiguration;
import de.ruegnerlukas.simpleapplication.core.presentation.views.WindowHandle;
import de.ruegnerlukas.simpleapplication.simpleui.core.windows.WindowOpenData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.function.Consumer;

@Getter
@Builder
@AllArgsConstructor
public class SAppWindowOpenData implements WindowOpenData {


	/**
	 * The id of the view to open
	 */
	private final String viewId;

	/**
	 * The configuration for the new window.
	 */
	private final PopupConfiguration popupConfig;

	/**
	 * A listener called when the window is opened
	 */
	private final Consumer<WindowHandle> onOpen;

}
