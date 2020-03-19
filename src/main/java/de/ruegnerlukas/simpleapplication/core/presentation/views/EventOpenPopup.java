package de.ruegnerlukas.simpleapplication.core.presentation.views;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.Builder;
import lombok.Getter;

/**
 * The event when opening a new popup window.
 */
@Getter
@Builder
public final class EventOpenPopup extends Publishable {


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
		super(Channel.type(EventOpenPopup.class));
		this.viewId = viewId;
		this.windowHandle = windowHandle;
	}


}
