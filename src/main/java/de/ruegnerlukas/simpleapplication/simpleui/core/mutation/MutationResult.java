package de.ruegnerlukas.simpleapplication.simpleui.core.mutation;

/**
 * The result of a mutation.
 */
public enum MutationResult {

	/**
	 * The element can not be mutated and has to be rebuild.
	 */
	REQUIRES_REBUILD,

	/**
	 * The element was successfully mutated.
	 */
	MUTATED
}
