package de.ruegnerlukas.simpleapplication.common.tags;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;

import java.util.Set;

public interface Tags {


	/**
	 * @return the tags as strings.
	 */
	Set<String> getTags();


	/**
	 * An empty tags object.
	 */
	Tags EMPTY = new TagsImpl(Set.of());

	/**
	 * Creates a new empty {@link Tags} object.
	 *
	 * @return the created object
	 */
	static Tags empty() {
		return EMPTY;
	}


	/**
	 * Creates a new {@link Tags} object from the given string tags.
	 *
	 * @param tags the tags
	 * @return the created object
	 */
	static Tags from(final Set<String> tags) {
		return (tags == null || tags.isEmpty()) ? empty() : new TagsImpl(tags);
	}


	/**
	 * Creates a new {@link Tags} object from the given string tags.
	 *
	 * @param tags the tags
	 * @return the created object
	 */
	static Tags from(final String... tags) {
		return (tags == null || tags.length == 0) ? empty() : new TagsImpl(Set.of(tags));
	}

	/**
	 * @param tag the tag that must be in the list of tags
	 * @return the created expression
	 */
	static TagConditionExpression contains(final String tag) {
		Validations.INPUT.notNull(tag).exception("The tag may not be null.");
		return new TagConditionExpression.ContainsConditionExpression(tag);
	}


	/**
	 * @param tags the tags that must be in the list of tags
	 * @return the created expression
	 */
	static TagConditionExpression containsAll(final String... tags) {
		Validations.INPUT.notNull(tags).exception("The tags may not be null.");
		Validations.INPUT.containsNoNull(tags).exception("The tags may not contain any null-elements.");
		return containsAll(Set.of(tags));
	}


	/**
	 * @param tags the tags that must be in the list of tags
	 * @return the created expression
	 */
	static TagConditionExpression containsAll(final Set<String> tags) {
		Validations.INPUT.notNull(tags).exception("The tags may not be null.");
		Validations.INPUT.containsNoNull(tags).exception("The tags may not contain any null-elements.");
		return new TagConditionExpression.ContainsAllConditionExpression(tags);
	}


	/**
	 * @param tags the tags. At least one of them must be in the list of tags.
	 * @return the created expression
	 */
	static TagConditionExpression containsAny(final String... tags) {
		Validations.INPUT.notNull(tags).exception("The tags may not be null.");
		Validations.INPUT.containsNoNull(tags).exception("The tags may not contain any null-elements.");
		return containsAny(Set.of(tags));
	}


	/**
	 * @param tags the tags. At least one of them must be in the list of tags.
	 * @return the created expression
	 */
	static TagConditionExpression containsAny(final Set<String> tags) {
		Validations.INPUT.notNull(tags).exception("The tags may not be null.");
		Validations.INPUT.containsNoNull(tags).exception("The tags may not contain any null-elements.");
		return new TagConditionExpression.ContainsAnyConditionExpression(tags);

	}


	/**
	 * @param expressions the expressions to combine with the 'or'-statement. At least one of them must result in 'true'.
	 * @return the created expression
	 */
	static TagConditionExpression or(final TagConditionExpression... expressions) {
		Validations.INPUT.notNull(expressions).exception("The expressions may not be null.");
		Validations.INPUT.containsNoNull(expressions).exception("The expressions may not contain any null-elements.");
		return or(Set.of(expressions));
	}


	/**
	 * @param expressions the expressions to combine with the 'or'-statement. At least one of them must result in 'true'.
	 * @return the created expression
	 */
	static TagConditionExpression or(final Set<TagConditionExpression> expressions) {
		Validations.INPUT.notNull(expressions).exception("The expressions may not be null.");
		Validations.INPUT.containsNoNull(expressions).exception("The expressions may not contain any null-elements.");
		return new TagConditionExpression.OrConditionExpression(expressions);
	}


	/**
	 * @param expressions the expressions to combine with the 'and'-statement. All of them must result in 'true'.
	 * @return the created expression
	 */
	static TagConditionExpression and(final TagConditionExpression... expressions) {
		Validations.INPUT.notNull(expressions).exception("The expressions may not be null.");
		Validations.INPUT.containsNoNull(expressions).exception("The expressions may not contain any null-elements.");
		return and(Set.of(expressions));
	}


	/**
	 * @param expressions the expressions to combine with the 'and'-statement. All of them must result in 'true'.
	 * @return the created expression
	 */
	static TagConditionExpression and(final Set<TagConditionExpression> expressions) {
		Validations.INPUT.notNull(expressions).exception("The expressions may not be null.");
		Validations.INPUT.containsNoNull(expressions).exception("The expressions may not contain any null-elements.");
		return new TagConditionExpression.AndConditionExpression(expressions);
	}


	/**
	 * @param expression the expressions to invert.
	 * @return the created expression
	 */
	static TagConditionExpression not(final TagConditionExpression expression) {
		Validations.INPUT.notNull(expression).exception("The expression may not be null.");
		return new TagConditionExpression.NotConditionExpression(expression);
	}

	/**
	 * @param result the result independent of the input tags.
	 * @return the created expression
	 */
	static TagConditionExpression constant(final boolean result) {
		if (result) {
			return TagConditionExpression.ConstantConditionExpression.TRUE;
		} else {
			return TagConditionExpression.ConstantConditionExpression.FALSE;
		}
	}


}
