package de.ruegnerlukas.simpleapplication.simpleui.streams;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

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
	public Stream<OUT> filter(final Predicate<OUT> predicate) {
		return new FilterStream<>(this, predicate);
	}




	@Override
	public Stream<OUT> filterNulls() {
		return new FilterStream<>(this, Objects::nonNull);
	}




	@Override
	public <R> Stream<R> map(final Function<OUT, R> mapping) {
		return new MapStream<>(this, mapping);
	}




	@Override
	public <R> Stream<R> flatMap(final Function<OUT, List<R>> mapping) {
		return new FlatMapStream<>(this, mapping);
	}




	@Override
	public void forEach(final Consumer<OUT> consumer) {
		new ForEachStream<>(this, consumer);
	}




	@Override
	public Stream<OUT> peek(final Consumer<OUT> consumer) {
		return new PeekStream<>(this, consumer);
	}

}