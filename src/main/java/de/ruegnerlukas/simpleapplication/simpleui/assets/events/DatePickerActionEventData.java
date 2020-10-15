package de.ruegnerlukas.simpleapplication.simpleui.assets.events;

import javafx.event.ActionEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class DatePickerActionEventData {


	/**
	 * The now selected date.
	 */
	private final LocalDate date;

	/**
	 * The original javafx event.
	 */
	private final ActionEvent source;

}
