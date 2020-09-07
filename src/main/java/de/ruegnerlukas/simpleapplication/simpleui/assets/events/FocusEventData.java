package de.ruegnerlukas.simpleapplication.simpleui.assets.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class FocusEventData {


	/**
	 * Whether the element has focus now.
	 */
	private final boolean focused;

}
