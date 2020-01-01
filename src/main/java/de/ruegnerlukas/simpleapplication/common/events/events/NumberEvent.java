package de.ruegnerlukas.simpleapplication.common.events.events;

import de.ruegnerlukas.simpleapplication.common.events.GenericEventSource;
import de.ruegnerlukas.simpleapplication.common.events.listeners.GenericEventListener;

public class NumberEvent extends GenericEvent<Number> {


	/**
	 * @param data the data
	 */
	public NumberEvent(final Number data) {
		super(data);
	}




	/**
	 * An implementation of a {@link GenericEventSource} for {@link NumberEvent}s.
	 */
	public static class NumberEventSource extends GenericEventSource<NumberEvent> {


		/**
		 * Triggers a {@link NumberEvent} with the given string.
		 *
		 * @param value the number
		 */
		public void trigger(final Number value) {
			super.trigger(new NumberEvent(value));
		}




		@Override
		public void trigger(final NumberEvent event) {
			super.trigger(event);
		}

	}






	/**
	 * A listener interface for {@link NumberEvent}s.
	 */
	public interface NumberEventListener extends GenericEventListener<NumberEvent> {


		/**
		 * @param value the received number.
		 */
		void onEvent(Number value);

		@Override
		default void onReceivedEvent(final NumberEvent event) {
			onEvent(event.getData());
		}

	}


}
