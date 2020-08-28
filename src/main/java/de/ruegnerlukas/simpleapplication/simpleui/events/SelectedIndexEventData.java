package de.ruegnerlukas.simpleapplication.simpleui.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class SelectedIndexEventData {


	/**
	 * The now selected index.
	 */
	private final Integer index;

	/**
	 * The previously selected index
	 */
	private final Integer prevIndex;

}
