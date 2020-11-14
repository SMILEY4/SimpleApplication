package de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.operations;

import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.PipelineImpl;

import java.util.Objects;

public class DistinctStream<T> extends PipelineImpl<T, T> {


	/**
	 * The last element to be passed along
	 */
	private T lastElement = null;




	/**
	 * @param source the source pipeline
	 */
	public DistinctStream(final Pipeline<?, T> source) {
		super(source);
	}




	@Override
	protected void process(final T element) {
		if (!Objects.equals(lastElement, element)) {
			lastElement = element;
			pushElementToNext(element);
		}
	}

}
