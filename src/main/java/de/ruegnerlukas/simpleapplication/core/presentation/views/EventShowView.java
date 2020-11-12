package de.ruegnerlukas.simpleapplication.core.presentation.views;

import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;
import lombok.Getter;

/**
 * The event when showing a new view in existing windows.
 */
@Getter
public final class EventShowView {


	public static final String EVENT_TYPE = "event.type.view.show";

	public static final Tags TAGS = Tags.from(EVENT_TYPE);


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
		this.prevViewId = prevViewId;
		this.viewId = viewId;
		this.windowHandle = windowHandle;
	}


}
