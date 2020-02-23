package de.ruegnerlukas.simpleapplication.common.validation;

public class ValidateStateException extends RuntimeException {


	/**
	 * An exception thrown by the {@link Validations#STATE}-validator
	 *
	 * @param message the message of this exception
	 */
	public ValidateStateException(final String message) {
		super(message);
	}

}
