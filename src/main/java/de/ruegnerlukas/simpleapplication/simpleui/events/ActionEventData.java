package de.ruegnerlukas.simpleapplication.simpleui.events;

import javafx.event.ActionEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class ActionEventData<T> {


	/**
	 * The data associated with the action.
	 */
	private final T data;

	/**
	 * The original javafx event.
	 */
	private final ActionEvent source;

}
