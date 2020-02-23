package de.ruegnerlukas.simpleapplication.common.validation;

public class ValidatePresenceException extends RuntimeException {


	/**
	 * An exception thrown by the {@link Validations#PRESENCE}-validator
	 *
	 * @param message the message of this exception
	 */
	public ValidatePresenceException(final String message) {
		super(message);
	}

}
