package de.ruegnerlukas.simpleapplication.common.validation;

public class NotFoundException extends RuntimeException {


	/**
	 * A not-found-exception.
	 *
	 * @param message the message of this exception
	 */
	public NotFoundException(final String message) {
		super(message);
	}

}
