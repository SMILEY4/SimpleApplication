package de.ruegnerlukas.simpleapplication.core.simpleui.assets.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class HoverEventData {


	/**
	 * Whether the element is now being hovered over.
	 */
	private final boolean hover;

}
