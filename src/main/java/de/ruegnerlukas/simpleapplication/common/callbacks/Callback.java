package de.ruegnerlukas.simpleapplication.common.callbacks;

public interface Callback<T, R> {


	/**
	 * When this callback is executed.
	 *
	 * @param t the input value
	 * @return a result
	 */
	R execute(T t);

}
