package de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.operations;

import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.PipelineImpl;
import javafx.application.Platform;

public class OnJFXStream<T> extends PipelineImpl<T, T> {


	/**
	 * @param source the source pipeline
	 */
	public OnJFXStream(final Pipeline<?, T> source) {
		super(source);
	}




	@Override
	protected void process(final T element) {
		Platform.runLater(() -> pushElementToNext(element));
	}

}
