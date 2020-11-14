package de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.operations;

import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.PipelineImpl;

import java.util.List;
import java.util.function.Supplier;

public class FlatMapNullsStream<T> extends PipelineImpl<T, T> {


	/**
	 * The function to map the input element to any number of output elements
	 */
	private final Supplier<List<T>> mapping;




	/**
	 * @param source  the source pipeline
	 * @param mapping the function to map the input null element to any number of output elements
	 */
	public FlatMapNullsStream(final Pipeline<?, T> source, final Supplier<List<T>> mapping) {
		super(source);
		this.mapping = mapping;
	}




	@Override
	protected void process(final T element) {
		if (element != null) {
			pushElementToNext(element);
		} else {
			for (T out : mapping.get()) {
				pushElementToNext(out);
			}
		}
	}

}
