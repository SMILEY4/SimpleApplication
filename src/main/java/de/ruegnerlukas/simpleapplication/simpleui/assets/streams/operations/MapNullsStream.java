package de.ruegnerlukas.simpleapplication.simpleui.assets.streams.operations;

import de.ruegnerlukas.simpleapplication.simpleui.assets.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.simpleui.assets.streams.PipelineImpl;

import java.util.function.Supplier;

public class MapNullsStream<T> extends PipelineImpl<T, T> {


	/**
	 * The function to map the null input element to an output element
	 */
	private final Supplier<T> mapping;




	/**
	 * @param source  the source pipeline
	 * @param mapping the function to map the null input element to an output element
	 */
	public MapNullsStream(final Pipeline<?, T> source, final Supplier<T> mapping) {
		super(source);
		this.mapping = mapping;
	}




	@Override
	protected void process(final T element) {
		if (element != null) {
			pushElementToNext(element);
		} else {
			pushElementToNext(mapping.get());
		}
	}

}
