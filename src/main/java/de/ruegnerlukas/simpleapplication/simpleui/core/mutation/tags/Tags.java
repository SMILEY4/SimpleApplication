package de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import lombok.Getter;

import java.util.Collections;
import java.util.List;

public class Tags {


	/**
	 * An empty tags object.
	 */
	private static final Tags EMPTY = new Tags(List.of());


	/**
	 * The tags
	 */
	@Getter
	private final List<String> tags;




	/**
	 * @param tags the list of tags
	 */
	public Tags(final List<String> tags) {
		Validations.INPUT.notNull(tags).exception("The list of tags may not be null.");
		Validations.INPUT.containsNoNull(tags).exception("The list of tags may not contain null-elements.");
		this.tags = Collections.unmodifiableList(tags);
	}




	/**
	 * Creates a new empty {@link Tags} object.
	 *
	 * @return the created object
	 */
	public static Tags empty() {
		return EMPTY;
	}




	/**
	 * Creates a new {@link Tags} object from the given string tags.
	 *
	 * @param tags the tags
	 * @return the created object
	 */
	public static Tags from(final List<String> tags) {
		return (tags == null || tags.isEmpty()) ? empty() : new Tags(tags);
	}




	/**
	 * Creates a new {@link Tags} object from the given string tags.
	 *
	 * @param tags the tags
	 * @return the created object
	 */
	public static Tags from(final String... tags) {
		return (tags == null || tags.length == 0) ? empty() : new Tags(List.of(tags));
	}


}
