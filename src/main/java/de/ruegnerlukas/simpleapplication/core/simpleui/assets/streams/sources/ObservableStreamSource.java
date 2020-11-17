package de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.sources;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import javafx.beans.value.ObservableValue;

public class ObservableStreamSource<T> extends StreamSource<T> {


	/**
	 * @param observable the observable value
	 */
	public ObservableStreamSource(final ObservableValue<T> observable) {
		Validations.INPUT.notNull(observable).exception("The observable value may not be null.");
		observable.addListener((value, prev, next) -> pushElementToNext(next));
	}




}
