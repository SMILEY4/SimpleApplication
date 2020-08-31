package de.ruegnerlukas.simpleapplication.simpleui.streams;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.AccumulateStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.AsyncStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.CollectIntoStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.CollectIntoValueStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.DistinctStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.FilterStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.FlatMapIgnoreNullStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.FlatMapNullsStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.FlatMapStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.ForEachStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.HandleErrorStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.LastNStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.MapIgnoreNullsStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.MapNullsStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.MapStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.OnJFXStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.PeekStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.SkipStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.ToStringStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.UnpackStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.WaitForAndPackStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.WaitForStream;
import javafx.beans.value.WritableValue;
import javafx.util.Duration;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public abstract class PipelineImpl<IN, OUT> extends Pipeline<IN, OUT> {


	/**
	 * Creates a new pipeline with no source.
	 */
	public PipelineImpl() {
		super();
	}




	/**
	 * Creates a new pipeline with the given pipeline as a source. This pipeline is a subscriber/consumer of the given pipeline.
	 *
	 * @param source the source pipeline
	 */
	public PipelineImpl(final Pipeline<?, IN> source) {
		super(source);
	}




	@Override
	public SuiStream<OUT, OUT> suppressErrors() {
		return handleErrors(e -> {
			// do nothing
		});
	}




	@Override
	public SuiStream<OUT, OUT> handleErrors(final Consumer<Exception> handler) {
		Validations.INPUT.notNull(handler).exception("The handler may not be null.");
		return new HandleErrorStream<>(this, handler);
	}




	@Override
	public SuiStream<OUT, OUT> filter(final Predicate<OUT> predicate) {
		Validations.INPUT.notNull(predicate).exception("The predicate may not be null.");
		return new FilterStream<>(this, predicate);
	}




	@Override
	public SuiStream<OUT, OUT> filterNulls() {
		return new FilterStream<>(this, Objects::nonNull);
	}




	@Override
	public <R> SuiStream<OUT, R> map(final Function<OUT, R> mapping) {
		Validations.INPUT.notNull(mapping).exception("The mapping may not be null.");
		return new MapStream<>(this, mapping);
	}




	@Override
	public <R> SuiStream<OUT, R> mapIgnoreNulls(final Function<OUT, R> mapping) {
		Validations.INPUT.notNull(mapping).exception("The mapping may not be null.");
		return new MapIgnoreNullsStream<>(this, mapping);
	}




	@Override
	public SuiStream<OUT, OUT> mapNulls(final Supplier<OUT> mapping) {
		Validations.INPUT.notNull(mapping).exception("The mapping may not be null.");
		return new MapNullsStream<>(this, mapping);
	}




	@Override
	public SuiStream<OUT, String> mapToString() {
		return new ToStringStream<>(this);
	}




	@Override
	public <R> SuiStream<OUT, R> flatMap(final Function<OUT, List<R>> mapping) {
		Validations.INPUT.notNull(mapping).exception("The mapping may not be null.");
		return new FlatMapStream<>(this, mapping);
	}




	@Override
	public <R> SuiStream<OUT, R> flatMapIgnoreNulls(final Function<OUT, List<R>> mapping) {
		Validations.INPUT.notNull(mapping).exception("The mapping may not be null.");
		return new FlatMapIgnoreNullStream<>(this, mapping);
	}




	@Override
	public SuiStream<OUT, OUT> flatMapNulls(final Supplier<List<OUT>> mapping) {
		Validations.INPUT.notNull(mapping).exception("The mapping may not be null.");
		return new FlatMapNullsStream<>(this, mapping);
	}




	@Override
	public void forEach(final Consumer<OUT> consumer) {
		Validations.INPUT.notNull(consumer).exception("The consumer may not be null.");
		new ForEachStream<>(this, consumer);
	}




	@Override
	public SuiStream<OUT, OUT> peek(final Consumer<OUT> consumer) {
		Validations.INPUT.notNull(consumer).exception("The consumer may not be null.");
		return new PeekStream<>(this, consumer);
	}




	@Override
	public SuiStream<OUT, OUT> onJavaFxThread() {
		return new OnJFXStream<>(this);
	}




	@Override
	public void collectInto(final Collection<OUT> collection) {
		Validations.INPUT.notNull(collection).exception("The target collection may not be null.");
		new CollectIntoStream<>(this, collection);
	}




	@Override
	public void collectInto(final WritableValue<OUT> value) {
		Validations.INPUT.notNull(value).exception("The target value may not be null.");
		new CollectIntoValueStream<>(this, value);
	}




	@Override
	public SuiStream<OUT, OUT> distinct() {
		return new DistinctStream<>(this);
	}




	@Override
	public SuiStream<OUT, OUT> waitFor(final boolean includeMatching, final Predicate<OUT> predicate) {
		Validations.INPUT.notNull(predicate).exception("The predicate may not be null.");
		return new WaitForStream<>(this, predicate, includeMatching);
	}




	@Override
	public SuiStream<OUT, List<OUT>> waitForAndPack(final boolean includeMatching, final Predicate<OUT> predicate) {
		Validations.INPUT.notNull(predicate).exception("The predicate may not be null.");
		return new WaitForAndPackStream<>(this, predicate, includeMatching);
	}




	@Override
	public SuiStream<OUT, OUT> async() {
		return async(-1);
	}




	@Override
	public SuiStream<OUT, OUT> async(final int poolSize) {
		return new AsyncStream<>(this, poolSize);
	}




	@Override
	public SuiStream<OUT, OUT> skip(final Supplier<Boolean> skipFlag) {
		Validations.INPUT.notNull(skipFlag).exception("The supplier for the skip-flag may not be null.");
		return new SkipStream<>(this, skipFlag);
	}




	@Override
	public SuiStream<OUT, List<OUT>> lastN(final int n) {
		return new LastNStream<>(this, n);
	}




	@Override
	public <R> SuiStream<OUT, R> unpack(final Class<R> expectedType) {
		return new UnpackStream<>(this);
	}




	@Override
	public SuiStream<OUT, List<OUT>> accumulate(final int maxAmount) {
		return accumulate(maxAmount, Duration.millis(Long.MAX_VALUE));
	}




	@Override
	public SuiStream<OUT, List<OUT>> accumulate(final Duration timeout) {
		Validations.INPUT.notNull(timeout).exception("The timeout may not be null.");
		return accumulate(Integer.MAX_VALUE, timeout);
	}




	@Override
	public SuiStream<OUT, List<OUT>> accumulate(final int maxAmount, final Duration timeout) {
		Validations.INPUT.notNull(timeout).exception("The timeout may not be null.");
		return new AccumulateStream<>(this, maxAmount, timeout);
	}

}
