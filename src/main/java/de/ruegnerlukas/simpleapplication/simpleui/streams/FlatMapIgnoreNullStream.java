package de.ruegnerlukas.simpleapplication.simpleui.streams;

import java.util.List;
import java.util.function.Function;

public class FlatMapIgnoreNullStream<IN, OUT> extends PipelineImpl<IN, OUT> {


	/**
	 * The function to map the input element to any number of output elements
	 */
	private final Function<IN, List<OUT>> mapping;




	/**
	 * @param source  the source pipeline
	 * @param mapping the function to map the input element to any number of output elements
	 */
	public FlatMapIgnoreNullStream(final Pipeline<?, IN> source, final Function<IN, List<OUT>> mapping) {
		super(source);
		this.mapping = mapping;
	}




	@Override
	protected void process(final IN element) {
		if (element != null) {
			for (OUT out : mapping.apply(element)) {
				pushElementToNext(out);
			}
		} else {
			pushElementToNext(null);
		}
	}

}
