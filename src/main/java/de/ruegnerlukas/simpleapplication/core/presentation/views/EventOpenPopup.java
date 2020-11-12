package de.ruegnerlukas.simpleapplication.core.presentation.views;

import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;
import lombok.Builder;
import lombok.Getter;

/**
 * The event when opening a new popup window.
 */
@Getter
@Builder
public final class EventOpenPopup {


	public static final String EVENT_TYPE = "event.type.view.popup.open";

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
	public EventOpenPopup(final String viewId, final WindowHandle windowHandle) {
		this.viewId = viewId;
		this.windowHandle = windowHandle;
	}


}
