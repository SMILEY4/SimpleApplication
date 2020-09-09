package de.ruegnerlukas.simpleapplication.simpleui.assets.streams.operations;

import de.ruegnerlukas.simpleapplication.simpleui.assets.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.simpleui.assets.streams.PipelineImpl;

public class ToStringStream<IN> extends PipelineImpl<IN, String> {


	/**
	 * @param source the source pipeline
	 */
	public ToStringStream(final Pipeline<?, IN> source) {
		super(source);
	}




	@Override
	protected void process(final IN element) {
		if (element == null) {
			pushElementToNext("null");
		} else {
			pushElementToNext(element.toString());
		}
	}

}
