package de.ruegnerlukas.simpleapplication.simpleui.events;

import javafx.scene.input.ScrollEvent;
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
	 * The vertical scroll amount
	 */
	private final double dy;

	/**
	 * The horizontal scroll amount
	 */
	private final double dx;

	/**
	 * The multiplier used to convert vertical mouse wheel rotation units to pixels.
	 */
	private final double pixelMultiplierY;

	/**
	 * The multiplier used to convert horizontal mouse wheel rotation units to pixels.
	 */
	private final double pixelMultiplierX;

	/**
	 * The original javafx event.
	 */
	private final ScrollEvent source;

}
