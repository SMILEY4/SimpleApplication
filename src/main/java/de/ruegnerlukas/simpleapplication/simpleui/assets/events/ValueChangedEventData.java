package de.ruegnerlukas.simpleapplication.simpleui.assets.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class ValueChangedEventData<T> {


	/**
	 * The now selected value.
	 */
	private final T value;

	/**
	 * The previously selected value.
	 */
	private final T prevValue;

}
