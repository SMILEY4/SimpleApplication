package de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.sources;

import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.PipelineImpl;

public abstract class StreamSource<T> extends PipelineImpl<T, T> {


	@Override
	protected void process(final T element) {
		// do nothing here
	}

}
