package de.ruegnerlukas.simpleapplication.common.utils;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Loop {


	/**
	 * The number of available processors.
	 */
	private static final int CORE_COUNT = Runtime.getRuntime().availableProcessors();




	/**
	 * Iterates the given list asynchronously and applies the given function
	 *
	 * @param list     the input list
	 * @param function the function to apply
	 * @param <T>      the generic input type
	 * @param <R>      the generic output type
	 * @return the list of result elements
	 */
	public static <T, R> List<R> asyncCollectingLoop(final List<T> list, final Function<T, R> function) {
		return asyncCollectingLoop(list, false, function);
	}




	/**
	 * Iterates the given list asynchronously and applies the given function
	 *
	 * @param list        the input list
	 * @param removeNulls whether 'null'-results should be filtered out
	 * @param function    the function to apply
	 * @param <T>         the generic input type
	 * @param <R>         the generic output type
	 * @return the list of result elements
	 */
	public static <T, R> List<R> asyncCollectingLoop(final List<T> list, final boolean removeNulls, final Function<T, R> function) {
		final AsyncChunkProcessor<T, R> processor = new AsyncChunkProcessor<>(list, list.size() / CORE_COUNT,
				chunk -> chunk.stream()
						.map(function)
						.filter(e -> removeNulls || e != null)
						.collect(Collectors.toList())
		);
		return processor.get();
	}



}
