package de.ruegnerlukas.simpleapplication.simpleui.streams.operations;

import de.ruegnerlukas.simpleapplication.simpleui.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.simpleui.streams.PipelineImpl;

import java.util.Collection;

public class UnpackStream<IN, OUT> extends PipelineImpl<IN, OUT> {


	/**
	 * @param source the source pipeline
	 */
	public UnpackStream(final Pipeline<?, IN> source) {
		super(source);
	}




	@Override
	protected void process(final IN element) {
		if (element instanceof Collection) {
			final Collection<OUT> collection = (Collection<OUT>) element;
			for (OUT e : collection) {
				pushElementToNext(e);
			}
		} else {
			System.out.println("DEBUG: element not a collection !!"); // todo
		}
	}


}
