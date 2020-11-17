package de.ruegnerlukas.simpleapplication.core.simpleui.assets.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class ValueEventData<T> {


	/**
	 * The now selected value.
	 */
	private final T value;


}
