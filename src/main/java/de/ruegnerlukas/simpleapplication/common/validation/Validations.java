package de.ruegnerlukas.simpleapplication.common.validation;

/**
 * Utility class for validation.
 */
public final class Validations {


	/**
	 * Validator for validating input. Throws {@link ValidateInputException}.
	 */
	public static final Validator INPUT = new Validator((message) -> {
		if (message == null) {
			return new ValidateInputException("");
		} else {
			return new ValidateInputException(message);
		}
	});

	/**
	 * Validator for validating state. Throws {@link ValidateStateException}.
	 */
	public static final Validator STATE = new Validator((message) -> {
		if (message == null) {
			return new ValidateStateException("");
		} else {
			return new ValidateStateException(message);
		}
	});

	/**
	 * Validator for validating presence. Throws {@link ValidatePresenceException}.
	 */
	public static final Validator PRESENCE = new Validator((message) -> {
		if (message == null) {
			return new ValidatePresenceException("");
		} else {
			return new ValidatePresenceException(message);
		}
	});




	/**
	 * Utility class
	 */
	private Validations() {

	}


}
