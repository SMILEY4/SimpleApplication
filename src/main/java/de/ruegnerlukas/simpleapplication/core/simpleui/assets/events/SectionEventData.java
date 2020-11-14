package de.ruegnerlukas.simpleapplication.core.simpleui.assets.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class SectionEventData {


	/**
	 * The title of the expanded/collapsed section
	 */
	private final String sectionTitle;

	/**
	 * false, if the section was collapsed and NO OTHER section was expanded instead.
	 */
	private final boolean expanded;

}
