package de.ruegnerlukas.simpleapplication.simpleui.streams.operations;

import de.ruegnerlukas.simpleapplication.simpleui.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.simpleui.streams.PipelineImpl;

import java.util.function.Function;

public class MapStream<IN, OUT> extends PipelineImpl<IN, OUT> {


	/**
	 * The function to map the input element to an output element
	 */
	private final Function<IN, OUT> mapping;




	/**
	 * @param source  the source pipeline
	 * @param mapping the function to map the input element to an output element
	 */
	public MapStream(final Pipeline<?, IN> source, final Function<IN, OUT> mapping) {
		super(source);
		this.mapping = mapping;
	}




	@Override
	protected void process(final IN element) {
		pushElementToNext(mapping.apply(element));
	}

}
