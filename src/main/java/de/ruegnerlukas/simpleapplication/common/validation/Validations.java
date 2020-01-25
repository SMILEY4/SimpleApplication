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
	public static final Validator INPUT = new Validator((message) -> {
		if (message == null) {
			return new IllegalArgumentException();
		} else {
			return new IllegalArgumentException(message);
		}
	});

	/**
	 * Validator for validating state. Throws {@link IllegalStateException}.
	 */
	public static final Validator STATE = new Validator((message) -> {
		if (message == null) {
			return new IllegalStateException();
		} else {
			return new IllegalStateException(message);
		}
	});

	/**
	 * Validator for validating presence. Throws {@link NotFoundException}.
	 */
	public static final Validator PRESENCE = new Validator((message) -> {
		if (message == null) {
			return new NotFoundException("");
		} else {
			return new NotFoundException(message);
		}
	});


}
