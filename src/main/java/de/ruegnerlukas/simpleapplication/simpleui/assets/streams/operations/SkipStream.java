package de.ruegnerlukas.simpleapplication.simpleui.assets.streams.operations;

import de.ruegnerlukas.simpleapplication.simpleui.assets.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.simpleui.assets.streams.PipelineImpl;

import java.util.function.Supplier;

public class SkipStream<T> extends PipelineImpl<T, T> {


	/**
	 * As long as this provides "true", all elements get dropped.
	 */
	private Supplier<Boolean> skipFlag;




	/**
	 * @param source   the source pipeline
	 * @param skipFlag as long as this provides "true", all elements get dropped
	 */
	public SkipStream(final Pipeline<?, T> source, final Supplier<Boolean> skipFlag) {
		super(source);
		this.skipFlag = skipFlag;
	}




	@Override
	protected void process(final T element) {
		if (!skipFlag.get()) {
			pushElementToNext(element);
		}
	}

}
