package de.ruegnerlukas.simpleapplication.common.validation;

public class ValidateInputException extends RuntimeException {


	/**
	 * An exception thrown by the {@link Validations#INPUT}-validator
	 *
	 * @param message the message of this exception
	 */
	public ValidateInputException(final String message) {
		super(message);
	}

}
