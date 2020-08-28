package de.ruegnerlukas.simpleapplication.simpleui.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class SelectedItemEventData<T> {


	/**
	 * The now selected item.
	 */
	private final T item;

	/**
	 * The previously selected item.
	 */
	private final T prevItem;

}
