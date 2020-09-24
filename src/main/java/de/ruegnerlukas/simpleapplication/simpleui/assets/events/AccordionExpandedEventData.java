package de.ruegnerlukas.simpleapplication.simpleui.assets.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class AccordionExpandedEventData {


	/**
	 * The title of the expanded section
	 */
	private final String expandedTitle;

	/**
	 * The title of the previously expanded section
	 */
	private final String prevExpandedTitle;

}
