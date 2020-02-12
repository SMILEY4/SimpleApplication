package de.ruegnerlukas.simpleapplication.core.presentation.views;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class ViewEvent {


	/**
	 * The id of the previous view (or null if no view existed before).
	 */
	private final String prevViewId;

	/**
	 * The id of the relevant view.
	 */
	private final String viewId;

	/**
	 * The relevant handle
	 */
	private final WindowHandle windowHandle;




	@Override
	public String toString() {
		return "ViewEvent@" + Integer.toHexString(this.hashCode())
				+ "{" + prevViewId + " -> " + viewId + ", handle=" + windowHandle.getHandleId() + "}";
	}

}
