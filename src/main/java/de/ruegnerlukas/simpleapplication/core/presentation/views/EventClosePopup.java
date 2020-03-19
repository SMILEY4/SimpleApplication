package de.ruegnerlukas.simpleapplication.core.presentation.views;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.Getter;

/**
 * The event when closing a popup window.
 */
@Getter
public final class EventClosePopup extends Publishable {



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
		super(Channel.type(EventClosePopup.class));
		this.viewId = viewId;
		this.windowHandle = windowHandle;
	}


}
