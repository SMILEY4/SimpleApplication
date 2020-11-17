package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Collection;

@AllArgsConstructor (access = AccessLevel.PRIVATE)
public class InjectionIndexMarker {


	/**
	 * Inject items at the beginning of the list, before the default items.
	 *
	 * @return the marker
	 */
	public static InjectionIndexMarker injectFirst() {
		return new InjectionIndexMarker(0);
	}




	/**
	 * Inject items at the end of the list, after the default items.
	 *
	 * @return the marker
	 */
	public static InjectionIndexMarker injectLast() {
		return new InjectionIndexMarker(null);
	}




	/**
	 * Inject items at the given index.
	 *
	 * @param index the index to inject the items at
	 * @return the marker
	 */
	public static InjectionIndexMarker injectAt(final int index) {
		return new InjectionIndexMarker(index);
	}




	/**
	 * The index. "Null" = at the end of the list.
	 */
	private final Integer index;




	/**
	 * Get the index for the given collection
	 *
	 * @param target the collection
	 * @return the index
	 */
	public int getIndex(final Collection<?> target) {
		if (index == null) {
			return target.size();
		} else {
			Validations.STATE.isValidIndex(index, target.size() + 1)
					.log("Index to inject content at is invalid. Trying to correct index.");
			return Math.max(0, Math.min(index, target.size()));
		}
	}


}
