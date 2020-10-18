package de.ruegnerlukas.simpleapplication.simpleui.core.tags;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;

public class TagsImpl implements Tags {


	/**
	 * The tags
	 */
	@Getter
	private final Set<String> tags;




	/**
	 * @param tags the list of tags
	 */
	public TagsImpl(final Set<String> tags) {
		Validations.INPUT.notNull(tags).exception("The list of tags may not be null.");
		Validations.INPUT.containsNoNull(tags).exception("The list of tags may not contain null-elements.");
		this.tags = Collections.unmodifiableSet(tags);
	}


}
