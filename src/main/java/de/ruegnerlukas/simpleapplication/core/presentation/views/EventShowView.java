package de.ruegnerlukas.simpleapplication.core.presentation.views;

import de.ruegnerlukas.simpleapplication.core.application.ApplicationConstants;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.Getter;

@Getter
public class EventShowView extends Publishable {


	/**
	 * The id of the previous view (or null if no view existed before).
	 */
	private final String prevViewId;

	/**
	 * The id of the relevant view.
	 */
	private final String viewId;

	/**
	 * The relevant handle.
	 */
	private final WindowHandle windowHandle;




	/**
	 * @param prevViewId   the id of the previous view (or null if no view existed before)
	 * @param viewId       the id of the relevant view
	 * @param windowHandle the relevant handle
	 */
	public EventShowView(final String prevViewId, final String viewId, final WindowHandle windowHandle) {
		super(ApplicationConstants.EVENT_SHOW_VIEW);
		this.prevViewId = prevViewId;
		this.viewId = viewId;
		this.windowHandle = windowHandle;
	}


}
