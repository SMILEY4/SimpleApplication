package de.ruegnerlukas.simpleapplication.common.events2.specializedevents;

import de.ruegnerlukas.simpleapplication.common.events2.EventListener;
import de.ruegnerlukas.simpleapplication.common.events2.EventSource;
import de.ruegnerlukas.simpleapplication.common.events2.FixedEventSource;

public class StringEvent {


	public static class StringEventSource extends EventSource<String> {


	}






	public interface StringEventListener extends EventListener<String> {


	}






	public static class FixedStringEventSource extends FixedEventSource<String> {


		/**
		 * @param event the event to trigger
		 */
		public FixedStringEventSource(final String event) {
			super(event);
		}

	}

}
