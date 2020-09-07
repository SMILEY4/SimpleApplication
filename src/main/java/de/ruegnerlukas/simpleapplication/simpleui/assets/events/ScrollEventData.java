package de.ruegnerlukas.simpleapplication.simpleui.assets.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class ScrollEventData {


	/**
	 * The new vertical position after the scrolling happened.
	 */
	private final double yPos;

	/**
	 * The previous vertical position before the scrolling happened.
	 */
	private final double prevYPos;

	/**
	 * The vertical scroll amount.
	 */
	private final double dy;

	/**
	 * The new horizontal position after the scrolling happened.
	 */
	private final double xPos;

	/**
	 * The previous horizontal position before the scrolling happened.
	 */
	private final double prevXPos;

	/**
	 * The horizontal scroll amount.
	 */
	private final double dx;


}
