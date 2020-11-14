package de.ruegnerlukas.simpleapplication.core.simpleui.assets.events;

import javafx.event.ActionEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class TabActionEventData {


	/**
	 * The tab.
	 */
	private final String title;

	/**
	 * The original javafx event.
	 */
	private final ActionEvent source;

}
