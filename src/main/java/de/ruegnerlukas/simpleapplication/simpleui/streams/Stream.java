package de.ruegnerlukas.simpleapplication.simpleui.streams;

import javafx.beans.value.ObservableValue;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public interface Stream<T> {


	/**
	 * Creates a new stream from the given observable value.
	 *
	 * @param observable the observable value
	 * @param <T>        the generic type of the value and stream
	 */
	static <T> Stream<T> from(ObservableValue<T> observable) {
		return new StreamSource<T>(observable);
	}


	/**
	 * @param mapping the function to map the input element to an output element
	 * @return a stream with the the results of applying the given function to the elements of this stream.
	 */
	<R> Stream<R> map(Function<T, R> mapping);

	/**
	 * @param mapping the function to map the input element to any amount of output elements
	 * @return a stream with the results of applying the given function to the elements of this stream as individual elements.
	 */
	<R> Stream<R> flatMap(Function<T, List<R>> mapping);

	/**
	 * @param predicate the predicate to match
	 * @return a stream with the elements of this stream matching the given predicate.
	 */
	Stream<T> filter(Predicate<T> predicate);

	/**
	 * @return a stream with the elements of this stream except for null elements
	 */
	Stream<T> filterNulls();

	/**
	 * @param consumer the function called with each element of the stream.
	 *                 Performs the given action on each element in this stream.
	 */
	void forEach(Consumer<T> consumer);

	/**
	 * @param consumer the function called with each element of the stream.
	 * @return a stream consisting of the elements of this stream. Also calls the given function for each element.
	 */
	Stream<T> peek(Consumer<T> consumer);

}
