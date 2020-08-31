package de.ruegnerlukas.simpleapplication.simpleui.streams.sources;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;

import java.util.function.Consumer;

public class EventStreamSource<T> implements SUIEventListener<T> {


	/**
	 * The bridge between the sui event and the stream
	 */
	private final SimpleObjectProperty<T> bridgeProperty = new SimpleObjectProperty<>();




	/**
	 * @param bridge the consumer providing the bridge between the event listener and the streams.
	 */
	public EventStreamSource(final Consumer<ObservableValue<T>> bridge) {
		Validations.INPUT.notNull(bridge).exception("The consumer for the bridge value may not be null.");
		bridge.accept(bridgeProperty);
	}




	@Override
	public void onEvent(final T eventData) {
		bridgeProperty.setValue(eventData);
	}

}
