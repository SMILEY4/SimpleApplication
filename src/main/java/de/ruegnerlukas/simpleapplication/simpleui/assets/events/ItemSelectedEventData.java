package de.ruegnerlukas.simpleapplication.simpleui.assets.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class ItemSelectedEventData<T> {


	/**
	 * The selected items.
	 */
	private final List<T> selection;


}
