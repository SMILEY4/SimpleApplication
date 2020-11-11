package de.ruegnerlukas.simpleapplication.common.utils;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public final class Identity {


	/**
	 * Hidden constructor for utility class
	 */
	private Identity() {
		// hidden
	}




	/**
	 * @param <T> the generic type of the input and output of the function
	 * @return returns a {@link Function} that does nothing
	 */
	public static <T> Function<T, T> function() {
		return t -> t;
	}




	/**
	 * @param <T> the generic type of the input of the consumer
	 * @return returns a {@link Consumer} that does nothing
	 */
	public static <T> Consumer<T> consumer() {
		return t -> {
		};
	}




	/**
	 * @param <L> the generic type of the first input of the bi-consumer
	 * @param <R> the generic type of the second input of the bi-consumer
	 * @return returns a {@link BiConsumer} that does nothing
	 */
	public static <L, R> BiConsumer<L, R> biConsumer() {
		return (l, r) -> {
		};
	}

}
