package de.ruegnerlukas.simpleapplication.simpleui.assets.events;

import javafx.event.ActionEvent;
import javafx.scene.control.Tab;
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
	private final Tab tab;

	/**
	 * The original javafx event.
	 */
	private final ActionEvent source;

}
