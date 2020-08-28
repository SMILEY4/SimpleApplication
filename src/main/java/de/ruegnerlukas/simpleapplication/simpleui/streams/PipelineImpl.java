package de.ruegnerlukas.simpleapplication.simpleui.streams;

import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.AsyncStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.CollectIntoStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.CollectIntoValueStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.DistinctStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.FilterStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.FlatMapIgnoreNullStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.FlatMapNullsStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.FlatMapStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.ForEachStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.LastNStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.MapIgnoreNullsStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.MapNullsStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.MapStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.OnJFXStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.PeekStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.SkipStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.UnpackStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.WaitForAndPackStream;
import de.ruegnerlukas.simpleapplication.simpleui.streams.operations.WaitForStream;
import javafx.beans.value.WritableValue;

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
	public SUIStream<OUT> filter(final Predicate<OUT> predicate) {
		return new FilterStream<>(this, predicate);
	}




	@Override
	public SUIStream<OUT> filterNulls() {
		return new FilterStream<>(this, Objects::nonNull);
	}




	@Override
	public <R> SUIStream<R> map(final Function<OUT, R> mapping) {
		return new MapStream<>(this, mapping);
	}




	@Override
	public <R> SUIStream<R> mapIgnoreNulls(final Function<OUT, R> mapping) {
		return new MapIgnoreNullsStream<>(this, mapping);
	}




	@Override
	public SUIStream<OUT> mapNulls(final Supplier<OUT> mapping) {
		return new MapNullsStream<>(this, mapping);
	}




	@Override
	public <R> SUIStream<R> flatMap(final Function<OUT, List<R>> mapping) {
		return new FlatMapStream<>(this, mapping);
	}




	@Override
	public <R> SUIStream<R> flatMapIgnoreNulls(final Function<OUT, List<R>> mapping) {
		return new FlatMapIgnoreNullStream<>(this, mapping);
	}




	@Override
	public SUIStream<OUT> flatMapNulls(final Supplier<List<OUT>> mapping) {
		return new FlatMapNullsStream<>(this, mapping);
	}




	@Override
	public void forEach(final Consumer<OUT> consumer) {
		new ForEachStream<>(this, consumer);
	}




	@Override
	public SUIStream<OUT> peek(final Consumer<OUT> consumer) {
		return new PeekStream<>(this, consumer);
	}




	@Override
	public SUIStream<OUT> onJavaFxThread() {
		return new OnJFXStream<>(this);
	}




	@Override
	public void collectInto(final Collection<OUT> collection) {
		new CollectIntoStream<>(this, collection);
	}




	@Override
	public void collectInto(final WritableValue<OUT> value) {
		new CollectIntoValueStream<>(this, value);
	}




	@Override
	public SUIStream<OUT> distinct() {
		return new DistinctStream<>(this);
	}




	@Override
	public SUIStream<OUT> waitFor(final boolean includeMatching, final Predicate<OUT> predicate) {
		return new WaitForStream<>(this, predicate, includeMatching);
	}




	@Override
	public Pipeline<OUT, List<OUT>> waitForAndPack(final boolean includeMatching, final Predicate<OUT> predicate) {
		return new WaitForAndPackStream<>(this, predicate, includeMatching);
	}




	@Override
	public SUIStream<OUT> async() {
		return async(-1);
	}




	@Override
	public SUIStream<OUT> async(final int poolSize) {
		return new AsyncStream<>(this, poolSize);
	}




	@Override
	public SUIStream<OUT> skip(final Supplier<Boolean> skipFlag) {
		return new SkipStream<>(this, skipFlag);
	}




	@Override
	public Pipeline<OUT, List<OUT>> lastN(final int n) {
		return new LastNStream<>(this, n);
	}




	@Override
	public <R> SUIStream<R> unpack() {
		return new UnpackStream<OUT,R>(this);
	}

}
