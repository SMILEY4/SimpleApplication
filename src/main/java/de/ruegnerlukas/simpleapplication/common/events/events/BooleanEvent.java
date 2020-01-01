package de.ruegnerlukas.simpleapplication.common.events.events;

import de.ruegnerlukas.simpleapplication.common.events.GenericEventSource;
import de.ruegnerlukas.simpleapplication.common.events.listeners.GenericEventListener;

public class BooleanEvent extends GenericEvent<Boolean> {


	/**
	 * @param data the data
	 */
	public BooleanEvent(final Boolean data) {
		super(data);
	}




	/**
	 * An implementation of a {@link GenericEventSource} for {@link BooleanEvent}s.
	 */
	public static class BooleanEventSource extends GenericEventSource<BooleanEvent> {


		/**
		 * Triggers a {@link BooleanEvent} with the given value.
		 *
		 * @param value the boolean
		 */
		public void trigger(final boolean value) {
			super.trigger(new BooleanEvent(value));
		}




		@Override
		public void trigger(final BooleanEvent event) {
			super.trigger(event);
		}

	}






	/**
	 * A listener interface for {@link BooleanEvent}s.
	 */
	public interface BooleanEventListener extends GenericEventListener<BooleanEvent> {


		/**
		 * @param value the received boolean value.
		 */
		void onEvent(boolean value);

		@Override
		default void onReceivedEvent(final BooleanEvent event) {
			onEvent(event.getData());
		}

	}


}
