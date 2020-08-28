package de.ruegnerlukas.simpleapplication.simpleui.streams.sources;

import de.ruegnerlukas.simpleapplication.simpleui.streams.PipelineImpl;
import javafx.beans.value.ObservableValue;

public class StreamSource<T> extends PipelineImpl<T, T> {


	/**
	 * @param observable the observable value
	 */
	public StreamSource(final ObservableValue<T> observable) {
		observable.addListener((value, prev, next) -> pushElementToNext(next));
	}




	@Override
	protected void process(final T element) {
		// do nothing here
	}

}
