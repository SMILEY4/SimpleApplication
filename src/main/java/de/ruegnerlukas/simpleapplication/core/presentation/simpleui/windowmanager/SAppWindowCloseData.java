package de.ruegnerlukas.simpleapplication.core.presentation.simpleui.windowmanager;

import de.ruegnerlukas.simpleapplication.core.presentation.views.WindowHandle;
import de.ruegnerlukas.simpleapplication.simpleui.core.windows.WindowCloseData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SAppWindowCloseData implements WindowCloseData {


	/**
	 * The handle of the window to close (or null to use the view-id)
	 */
	private final WindowHandle windowHandle;


	/**
	 * The id of the view(s) to close (used when {@link SAppWindowCloseData#windowHandle} is null)
	 */
	private final String viewId;

}
