package de.ruegnerlukas.simpleapplication.core.simpleui.assets.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class DividerDraggedEventData {


	/**
	 * The index of the divider.
	 */
	private final int dividerIndex;

	/**
	 * The new position.
	 */
	private final Number nextPosition;

	/**
	 * The previous position.
	 */
	private final Number prevPosition;

}
