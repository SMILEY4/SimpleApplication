package de.ruegnerlukas.simpleapplication.core.presentation.views;

import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;
import lombok.Getter;

/**
 * The event when closing a popup window.
 */
@Getter
public final class EventClosePopup {


	public static final String EVENT_TYPE = "event.type.view.popup.close";

	public static final Tags TAGS = Tags.from(EVENT_TYPE);



	/**
	 * The id of the relevant view.
	 */
	private final String viewId;

	/**
	 * The relevant handle.
	 */
	private final WindowHandle windowHandle;




	/**
	 * @param viewId       the id of the relevant view
	 * @param windowHandle the relevant handle
	 */
	public EventClosePopup(final String viewId, final WindowHandle windowHandle) {
		this.viewId = viewId;
		this.windowHandle = windowHandle;
	}


}
