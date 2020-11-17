package de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.operations;

import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.PipelineImpl;

import java.util.function.Function;

public class MapIgnoreNullsStream<IN, OUT> extends PipelineImpl<IN, OUT> {


	/**
	 * The function to map the input element to an output element
	 */
	private final Function<IN, OUT> mapping;




	/**
	 * @param source  the source pipeline
	 * @param mapping the function to map the input element to an output element
	 */
	public MapIgnoreNullsStream(final Pipeline<?, IN> source, final Function<IN, OUT> mapping) {
		super(source);
		this.mapping = mapping;
	}




	@Override
	protected void process(final IN element) {
		if (element != null) {
			pushElementToNext(mapping.apply(element));
		} else {
			pushElementToNext(null);
		}
	}

}
