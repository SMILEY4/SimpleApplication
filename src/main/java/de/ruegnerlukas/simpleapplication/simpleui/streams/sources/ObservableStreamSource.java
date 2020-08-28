package de.ruegnerlukas.simpleapplication.simpleui.streams.sources;

import javafx.beans.value.ObservableValue;

public class ObservableStreamSource<T> extends StreamSource<T> {


	/**
	 * @param observable the observable value
	 */
	public ObservableStreamSource(final ObservableValue<T> observable) {
		observable.addListener((value, prev, next) -> pushElementToNext(next));
	}




}
