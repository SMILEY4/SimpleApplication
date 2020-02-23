package de.ruegnerlukas.simpleapplication.common.validation;

import de.ruegnerlukas.simpleapplication.common.callbacks.Callback;
import de.ruegnerlukas.simpleapplication.common.callbacks.EmptyCallback;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.helpers.MessageFormatter;

@AllArgsConstructor
@Slf4j
public class ValidationResult {


	/**
	 * Whether the validation failed.
	 */
	private final boolean failed;

	/**
	 * The builder for the exception.
	 */
	private final ExceptionBuilder exceptionBuilder;




	/**
	 * Formats the given message and inserts the given arguments.
	 *
	 * @param message the message
	 * @param args    the arguments
	 * @return the formatted message
	 */
	private String formatMessage(final String message, final Object... args) {
		String result = message;
		if (args != null && args.length > 0) {
			result = MessageFormatter.arrayFormat(message, args).getMessage();
		}
		return result;
	}




	/**
	 * Throws the exception of the specific validator without a message.
	 */
	private void throwException() {
		throwException("");
	}




	/**
	 * Throws the exception of the specific validator with the given message.
	 */
	private void throwException(final String message) {
		throw exceptionBuilder.build(message);
	}




	/**
	 * Throws the given {@link RuntimeException}.
	 */
	private void throwException(final RuntimeException e) {
		throw e;
	}




	/**
	 * @return whether the validation failed.
	 */
	public boolean failed() {
		return this.failed;
	}




	/**
	 * @param callback the callback to execute. The parameter of the callback is a boolean indicating whether the validation failed.
	 */
	public ValidationResult then(final Callback<Boolean> callback) {
		callback.execute(failed);
		return this;
	}




	/**
	 * @param callback the callback to execute when the validation failed.
	 */
	public ValidationResult onFail(final EmptyCallback callback) {
		if (this.failed) {
			callback.execute();
		}
		return this;
	}




	/**
	 * @param callback the callback to execute when the validation was successful
	 */
	public ValidationResult onSuccess(final EmptyCallback callback) {
		if (!this.failed) {
			callback.execute();
		}
		return this;
	}




	/**
	 * Logs a failed validation with the given error message (as a warning).
	 *
	 * @param errorMesssage the error message
	 * @param args          the arguments for the message
	 */
	public ValidationResult log(final String errorMesssage, final Object... args) {
		if (this.failed) {
			log.warn(errorMesssage, args);
		}
		return this;
	}




	/**
	 * Throws an appropriate exception when the validation failed.
	 */
	public ValidationResult exception() {
		if (this.failed) {
			throwException();
		}
		return this;
	}




	/**
	 * Throws the given exception when the validation failed.
	 */
	public ValidationResult exception(final RuntimeException exception) {
		if (this.failed) {
			throwException(exception);
		}
		return this;
	}




	/**
	 * Logs the given message and throws an appropriate exception when the validation failed.
	 */
	public ValidationResult exception(final String errorMessage, final Object... args) {
		if (this.failed) {
			throwException(formatMessage(errorMessage, args));
		}
		return this;
	}

}
