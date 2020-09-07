package de.ruegnerlukas.simpleapplication.simpleui.assets.streams.operations;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.simpleui.assets.streams.PipelineImpl;

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
		if (element == null) {
			pushElementToNext(null);
		} else if (element instanceof Collection) {
			final Collection<OUT> collection = (Collection<OUT>) element;
			for (OUT e : collection) {
				pushElementToNext(e);
			}
		} else {
			Validations.INPUT.fail().exception("Current element is not a collection");
		}
	}


}
