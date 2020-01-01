package de.ruegnerlukas.simpleapplication.common.events.events;

import de.ruegnerlukas.simpleapplication.common.events.GenericEventSource;
import de.ruegnerlukas.simpleapplication.common.events.listeners.GenericEventListener;

public class StringEvent extends GenericEvent<String> {


	/**
	 * @param data the data
	 */
	public StringEvent(final String data) {
		super(data);
	}




	/**
	 * An implementation of a {@link GenericEventSource} for {@link StringEvent}s.
	 */
	public static class StringEventSource extends GenericEventSource<StringEvent> {


		/**
		 * Triggers a {@link StringEvent} with the given string.
		 *
		 * @param data the string
		 */
		public void trigger(final String data) {
			super.trigger(new StringEvent(data));
		}




		@Override
		public void trigger(final StringEvent event) {
			super.trigger(event);
		}

	}






	/**
	 * A listener interface for {@link StringEvent}s.
	 */
	public interface StringEventListener extends GenericEventListener<StringEvent> {


		/**
		 * @param string the received string.
		 */
		void onEvent(String string);

		@Override
		default void onReceivedEvent(final StringEvent event) {
			onEvent(event.getData());
		}

	}


}
