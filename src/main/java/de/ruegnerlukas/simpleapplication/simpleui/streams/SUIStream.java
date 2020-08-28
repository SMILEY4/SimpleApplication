package de.ruegnerlukas.simpleapplication.simpleui.streams;

import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.streams.sources.EventStreamSource;
import de.ruegnerlukas.simpleapplication.simpleui.streams.sources.ObservableStreamSource;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface SUIStream<IN, OUT> {


	/**
	 * Creates a new stream from the given observable value.
	 *
	 * @param observable the observable value
	 */
	static <T> SUIStream<T, T> from(final ObservableValue<T> observable) {
		return new ObservableStreamSource<>(observable);
	}

	/**
	 * Used to create a stream that can listen to simpleui-events.
	 *
	 * @param bridge the consumer providing the bridge between the event listener and the streams.
	 *               Use this to create the stream with {@link SUIStream#from(ObservableValue)}
	 * @param <T>    the generic type of the event data
	 * @return the event listener to add to the event property.
	 */
	static <T> SUIEventListener<T> eventStream(final Consumer<ObservableValue<T>> bridge) {
		return new EventStreamSource<>(bridge);
	}

	/**
	 * @param mapping the function to map the input element to an output element.
	 * @return a stream with the the results of applying the given function to the elements of this stream.
	 */
	<R> SUIStream<OUT, R> map(Function<OUT, R> mapping);

	/**
	 * @param mapping the function to map the non-null input element to an output element.
	 *                'Null'-elements will be passed along without applying the function.
	 * @return a stream with the the results of applying the given function to the elements of this stream.
	 */
	<R> SUIStream<OUT, R> mapIgnoreNulls(Function<OUT, R> mapping);

	/**
	 * @param mapping the function to map the null input element to an output element.
	 *                Non-null elements will be passed along without applying the function.
	 * @return a stream with the the results of applying the given function to the elements of this stream.
	 */
	SUIStream<OUT, OUT> mapNulls(Supplier<OUT> mapping);

	/**
	 * @param mapping the function to map the input element to any amount of output elements.
	 * @return a stream with the results of applying the given function to the elements of this stream as individual elements.
	 */
	<R> SUIStream<OUT, R> flatMap(Function<OUT, List<R>> mapping);

	/**
	 * @param mapping the function to map the non-null input element to any amount of output elements.
	 *                'Null'-elements will be passed along without applying the function.
	 * @return a stream with the results of applying the given function to the elements of this stream as individual elements.
	 */
	<R> SUIStream<OUT, R> flatMapIgnoreNulls(Function<OUT, List<R>> mapping);

	/**
	 * @param mapping the function to map the null input element to any amount of output elements.
	 *                Non-null elements will be passed along without applying the function.
	 * @return a stream with the results of applying the given function to the elements of this stream as individual elements.
	 */
	SUIStream<OUT, OUT> flatMapNulls(Supplier<List<OUT>> mapping);

	/**
	 * @param predicate the predicate to match
	 * @return a stream with the elements of this stream matching the given predicate.
	 */
	SUIStream<OUT, OUT> filter(Predicate<OUT> predicate);

	/**
	 * @return a stream with the elements of this stream except for null elements
	 */
	SUIStream<OUT, OUT> filterNulls();

	/**
	 * @param consumer the function called with each element of the stream.
	 *                 Performs the given action on each element in this stream.
	 */
	void forEach(Consumer<OUT> consumer);

	/**
	 * @param consumer the function called with each element of the stream.
	 * @return a stream consisting of the elements of this stream. Also calls the given function for each element.
	 */
	SUIStream<OUT, OUT> peek(Consumer<OUT> consumer);

	/**
	 * @return a stream on the java-fx thread consisting of the elements on this stream.
	 * Uses {@link javafx.application.Platform#runLater} to make the switch to the java-fx main thread.
	 * Its is not guaranteed that the order of the elements is kept the same as in this stream.
	 */
	SUIStream<OUT, OUT> onJavaFxThread();

	/**
	 * Adds the elements of this stream to the given collection as they come in.
	 *
	 * @param collection the collection to add the elements to
	 */
	void collectInto(Collection<OUT> collection);

	/**
	 * Sets the given value to the latest element of this stream
	 *
	 * @param value the value
	 */
	void collectInto(WritableValue<OUT> value);


	/**
	 * @return a stream with the distinct elements from this stream.
	 * If an element is followed by the same element, the second element will be passed along.
	 */
	SUIStream<OUT, OUT> distinct();

	/**
	 * Holds back all elements until an element matches the given predicate. Then emits all held back elements.
	 *
	 * @param predicate       the predicate to match
	 * @param includeMatching whether to pass along the matching element
	 * @return a new stream
	 */
	SUIStream<OUT, OUT> waitFor(boolean includeMatching, Predicate<OUT> predicate);

	/**
	 * Holds back all elements until an element matches the given predicate. Then emits all held back elements as a single list.
	 *
	 * @param predicate       the predicate to match
	 * @param includeMatching whether to pass along the matching element
	 * @return a new stream.
	 */
	SUIStream<OUT, List<OUT>> waitForAndPack(boolean includeMatching, Predicate<OUT> predicate);

	/**
	 * @return a new stream that passes along the elements of this stream asynchronously on (a) new thread(s).
	 */
	SUIStream<OUT, OUT> async();

	/**
	 * @param poolSize defines the number of threads in the thread pool used by the stream
	 * @return a new stream that passes along the elements of this stream asynchronously on (a) new thread(s).
	 */
	SUIStream<OUT, OUT> async(int poolSize);

	/**
	 * Skips and drops all elements of this stream as long as the given flag is "true".
	 *
	 * @param skipFlag as long as this is supplier returns true, no elements will be passed along
	 * @return the new stream
	 */
	SUIStream<OUT, OUT> skip(Supplier<Boolean> skipFlag);


	/**
	 * Emits the last n elements (including the latest element) for every element.
	 *
	 * @param n the number of elements to emit
	 * @return a new stream
	 */
	SUIStream<OUT, List<OUT>> lastN(int n);


	/**
	 * Emits single elements for each collection in this stream.
	 *
	 * @return a new stream
	 */
	<R> SUIStream<OUT, R> unpack(Class<R> expectedType);

}
