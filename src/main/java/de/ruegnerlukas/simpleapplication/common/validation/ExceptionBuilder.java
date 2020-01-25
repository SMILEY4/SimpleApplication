package de.ruegnerlukas.simpleapplication.common.validation;

public interface ExceptionBuilder {


	/**
	 * @param message the message (can be null).
	 * @return the create exception
	 */
	RuntimeException build(String message);


}
