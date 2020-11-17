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
public class ActionEventData {


	/**
	 * The original javafx event.
	 */
	private final ActionEvent source;

}
