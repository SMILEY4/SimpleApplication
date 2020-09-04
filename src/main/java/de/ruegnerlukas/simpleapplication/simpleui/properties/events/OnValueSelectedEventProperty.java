package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.events.ValueEventData;
import lombok.Getter;

public class OnValueSelectedEventProperty<T> extends AbstractObservableListenerProperty<ValueEventData<T>, T> {


	/**
	 * The identifying string of the event.
	 */
	public static final String EVENT_ID = "selection.item";

	/**
	 * The listener for events with {@link ValueEventData}.
	 */
	@Getter
	private final SUIEventListener<ValueEventData<T>> listener;




	/**
	 * @param listener the listener for events with {@link ValueEventData}.
	 */
	public OnValueSelectedEventProperty(final SUIEventListener<ValueEventData<T>> listener) {
		super(OnValueSelectedEventProperty.class, (value, prev, next) -> {
			listener.onEvent(
					new ValueEventData<>(prev)
			);
		});
		this.listener = listener;
	}


}
