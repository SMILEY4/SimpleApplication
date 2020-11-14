package de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams;

import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.sources.CollectionStreamSource;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.sources.EventStreamSource;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.sources.ObservableStreamSource;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.events.SuiEventListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;
import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.WritableValue;
import javafx.util.Duration;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface SuiStream<IN, OUT> {


	/**
	 * Creates a new stream from the given observable value.
	 *
	 * @param observable the observable value
	 */
	static <T> SuiStream<T, T> from(final ObservableValue<T> observable) {
		return new ObservableStreamSource<>(observable);
	}


	/**
	 * Used to create a stream that can listen to simpleui-events.
	 *
	 * @param sourceType the type of the source elements
	 * @param stream     the consumer providing the created stream for further actions.
	 * @param <T>        the generic type of the event data
	 * @return the event listener to add to the event property.
	 */
	static <T> SuiEventListener<T> eventStream(final Class<T> sourceType, final Consumer<SuiStream<T, T>> stream) {
		return eventStreamBridge(bridge -> stream.accept(SuiStream.from(bridge)));
	}


	/**
	 * Used to create a stream that can listen to simpleui-events.
	 *
	 * @param bridge the consumer providing the bridge between the event listener and the streams.
	 *               Use this to create the stream with {@link SuiStream#from(ObservableValue)}
	 * @param <T>    the generic type of the event data
	 * @return the event listener to add to the event property.
	 */
	static <T> SuiEventListener<T> eventStreamBridge(final Consumer<ObservableValue<T>> bridge) {
		return new EventStreamSource<>(bridge);
	}

	/**
	 * Creates a new stream from the given collection. All elements in the collection will be processed immediately and synchronously.
	 *
	 * @param collection the collection of elements
	 * @param stream     the consumer providing the created stream for further actions.
	 */
	static <T> void from(final Collection<T> collection, final Consumer<SuiStream<T, T>> stream) {
		final CollectionStreamSource<T> collectionStreamSource = new CollectionStreamSource<>();
		stream.accept(collectionStreamSource);
		collectionStreamSource.acceptCollection(collection);
	}


	/**
	 * Suppresses all unhandled exceptions that are thrown in all following steps.
	 *
	 * @return a stream with the same elements as this stream
	 */
	SuiStream<OUT, OUT> suppressErrors();

	/**
	 * Handles all unhandled exceptions that are thrown in all following steps.
	 *
	 * @param handler the handler called when an exception is thrown
	 * @return a stream with the same elements as this stream
	 */
	SuiStream<OUT, OUT> handleErrors(Consumer<Exception> handler);

	/**
	 * @param mapping the function to map the input element to an output element.
	 * @return a stream with the the results of applying the given function to the elements of this stream.
	 */
	<R> SuiStream<OUT, R> map(Function<OUT, R> mapping);

	/**
	 * @param mapping the function to map the non-null input element to an output element.
	 *                'Null'-elements will be passed along without applying the function.
	 * @return a stream with the the results of applying the given function to the elements of this stream.
	 */
	<R> SuiStream<OUT, R> mapIgnoreNulls(Function<OUT, R> mapping);

	/**
	 * @param mapping the function to map the null input element to an output element.
	 *                Non-null elements will be passed along without applying the function.
	 * @return a stream with the the results of applying the given function to the elements of this stream.
	 */
	SuiStream<OUT, OUT> mapNulls(Supplier<OUT> mapping);

	/**
	 * @return a stream where all elements of this stream where mapped to strings (using Object.toString).
	 */
	SuiStream<OUT, String> mapToString();

	/**
	 * @param mapping the function to map the input element to any amount of output elements.
	 * @return a stream with the results of applying the given function to the elements of this stream as individual elements.
	 */
	<R> SuiStream<OUT, R> flatMap(Function<OUT, List<R>> mapping);

	/**
	 * @param mapping the function to map the non-null input element to any amount of output elements.
	 *                'Null'-elements will be passed along without applying the function.
	 * @return a stream with the results of applying the given function to the elements of this stream as individual elements.
	 */
	<R> SuiStream<OUT, R> flatMapIgnoreNulls(Function<OUT, List<R>> mapping);

	/**
	 * @param mapping the function to map the null input element to any amount of output elements.
	 *                Non-null elements will be passed along without applying the function.
	 * @return a stream with the results of applying the given function to the elements of this stream as individual elements.
	 */
	SuiStream<OUT, OUT> flatMapNulls(Supplier<List<OUT>> mapping);

	/**
	 * @param predicate the predicate to match
	 * @return a stream with the elements of this stream matching the given predicate.
	 */
	SuiStream<OUT, OUT> filter(Predicate<OUT> predicate);

	/**
	 * @return a stream with the elements of this stream except for null elements
	 */
	SuiStream<OUT, OUT> filterNulls();

	/**
	 * @param consumer the function called with each element of the stream.
	 *                 Performs the given action on each element in this stream.
	 */
	void forEach(Consumer<OUT> consumer);

	/**
	 * @param consumer the function called with each element of the stream.
	 * @return a stream consisting of the elements of this stream. Also calls the given function for each element.
	 */
	SuiStream<OUT, OUT> peek(Consumer<OUT> consumer);

	/**
	 * @return a stream on the java-fx thread consisting of the elements on this stream.
	 * Uses {@link javafx.application.Platform#runLater} to make the switch to the java-fx main thread.
	 * Its is not guaranteed that the order of the elements is kept the same as in this stream.
	 */
	SuiStream<OUT, OUT> onJavaFxThread();

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
	SuiStream<OUT, OUT> distinct();

	/**
	 * Holds back all elements until an element matches the given predicate. Then emits all held back elements.
	 *
	 * @param predicate       the predicate to match
	 * @param includeMatching whether to pass along the matching element
	 * @return a new stream
	 */
	SuiStream<OUT, OUT> waitFor(boolean includeMatching, Predicate<OUT> predicate);

	/**
	 * Holds back all elements until an element matches the given predicate. Then emits all held back elements as a single list.
	 *
	 * @param predicate       the predicate to match
	 * @param includeMatching whether to pass along the matching element
	 * @return a new stream.
	 */
	SuiStream<OUT, List<OUT>> waitForAndPack(boolean includeMatching, Predicate<OUT> predicate);

	/**
	 * @return a new stream that passes along the elements of this stream asynchronously on (a) new thread(s).
	 */
	SuiStream<OUT, OUT> async();

	/**
	 * @param poolSize defines the number of threads in the thread pool used by the stream
	 * @return a new stream that passes along the elements of this stream asynchronously on (a) new thread(s).
	 */
	SuiStream<OUT, OUT> async(int poolSize);

	/**
	 * Skips and drops all elements of this stream as long as the given flag is "true".
	 *
	 * @param skipFlag as long as this is supplier returns true, no elements will be passed along
	 * @return the new stream
	 */
	SuiStream<OUT, OUT> skip(Supplier<Boolean> skipFlag);


	/**
	 * Emits the last n elements (including the latest element) for every element.
	 *
	 * @param n the number of elements to emit
	 * @return a new stream
	 */
	SuiStream<OUT, List<OUT>> lastN(int n);


	/**
	 * Emits single elements for each collection in this stream.
	 *
	 * @return a new stream
	 */
	<R> SuiStream<OUT, R> unpack(Class<R> expectedType);

	/**
	 * When an element arrives, it and other incoming elements are held back until the max amount is reached.
	 * They are then emitted together as a single list. The new elements might not be emitted on the same thread.
	 *
	 * @return a new stream
	 */
	SuiStream<OUT, List<OUT>> accumulate(int maxAmount);

	/**
	 * When an element arrives, it and other incoming elements are held back until the timer runs out.
	 * They are then emitted together as a single list. The new elements might not be emitted on the same thread.
	 *
	 * @return a new stream
	 */
	SuiStream<OUT, List<OUT>> accumulate(Duration timeout);

	/**
	 * When an element arrives, it and other incoming elements are held back until the timer runs out or the max amount is reached.
	 * They are then emitted together as a single list.
	 *
	 * @return a new stream
	 */
	SuiStream<OUT, List<OUT>> accumulate(int maxAmount, Duration timeout);

	/**
	 * Updates the state for each element in this stream.
	 *
	 * @param stateType      the marker for the type of the state
	 * @param state          the state to update
	 * @param updateFunction the function updating the state
	 * @param <T>            the generic type of the state
	 */
	<T extends SuiState> void updateState(Class<T> stateType, T state, BiConsumer<T, OUT> updateFunction);

	/**
	 * Updates the state silently for each element in this stream.
	 *
	 * @param stateType      the marker for the type of the state
	 * @param state          the state to update
	 * @param updateFunction the function updating the state
	 * @param <T>            the generic type of the state
	 */
	<T extends SuiState> void updateStateSilent(Class<T> stateType, T state, BiConsumer<T, OUT> updateFunction);

	/**
	 * Forwards all elements to the simpleui-event bus.
	 *
	 * @param tags tags to add to all events
	 */
	void emitAsSuiEvent(Tags tags);


}
