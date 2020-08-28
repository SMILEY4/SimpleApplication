package de.ruegnerlukas.simpleapplication.simpleui.streams.sources;

import de.ruegnerlukas.simpleapplication.simpleui.streams.PipelineImpl;

public abstract class StreamSource<T> extends PipelineImpl<T, T> {


	@Override
	protected void process(final T element) {
		// do nothing here
	}

}
