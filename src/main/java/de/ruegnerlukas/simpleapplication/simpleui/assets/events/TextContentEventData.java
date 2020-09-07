package de.ruegnerlukas.simpleapplication.simpleui.assets.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class TextContentEventData {


	/**
	 * The text content,
	 */
	private final String text;


	/**
	 * The text content before the event,
	 */
	private final String prevText;

}
