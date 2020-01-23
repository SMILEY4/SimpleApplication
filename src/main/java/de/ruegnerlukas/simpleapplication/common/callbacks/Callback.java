package de.ruegnerlukas.simpleapplication.common.callbacks;

public interface Callback<T> {


	/**
	 * When this callback is executed.
	 *
	 * @param t the input value
	 * @return a result
	 */
	T execute(T t);

}
