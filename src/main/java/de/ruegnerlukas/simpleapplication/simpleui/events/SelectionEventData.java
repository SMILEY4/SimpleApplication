package de.ruegnerlukas.simpleapplication.simpleui.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class SelectionEventData<T> {


	/**
	 * The now selected item.
	 */
	private final T item;

	/**
	 * The now selected index.
	 */
	private final Integer index;

	/**
	 * The previously selected item.
	 */
	private final T prevItem;

	/**
	 * The previously selected index
	 */
	private final Integer prevIndex;

}
