package de.ruegnerlukas.simpleapplication.simpleui.streams.operations;

import de.ruegnerlukas.simpleapplication.simpleui.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.simpleui.streams.PipelineImpl;

import java.util.function.Predicate;

public class FilterStream<T> extends PipelineImpl<T, T> {


	/**
	 * The predicate to match.
	 */
	private final Predicate<T> predicate;




	/**
	 * @param source    the source pipeline
	 * @param predicate the predicate to match
	 */
	public FilterStream(final Pipeline<?, T> source, final Predicate<T> predicate) {
		super(source);
		this.predicate = predicate;
	}




	@Override
	protected void process(final T element) {
		if (predicate.test(element)) {
			pushElementToNext(element);
		}
	}

}
