package de.ruegnerlukas.simpleapplication.common.validation;

/**
 * Utility class for validation.
 */
public final class Validations {


	/**
	 * Utility class
	 */
	private Validations() {

	}




	/**
	 * Validator for validating input. Throws {@link IllegalArgumentException}.
	 */
	public static final Validator INPUT = new Validator() {
		@Override
		protected void failedValidation(final String message) {
			throw new IllegalArgumentException(message);
		}
	};

	/**
	 * Validator for validating state. Throws {@link IllegalStateException}.
	 */
	public static final Validator STATE = new Validator() {
		@Override
		protected void failedValidation(final String message) {
			throw new IllegalStateException(message);
		}
	};

	/**
	 * Validator for validating presence. Throws {@link NotFoundException}.
	 */
	public static final Validator PRESENCE = new Validator() {
		@Override
		protected void failedValidation(final String message) {
			throw new NotFoundException(message);
		}
	};


}
