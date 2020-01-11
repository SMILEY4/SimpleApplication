package de.ruegnerlukas.simpleapplication.common.events2.specializedevents;

import de.ruegnerlukas.simpleapplication.common.events2.EventListener;
import de.ruegnerlukas.simpleapplication.common.events2.EventSource;
import de.ruegnerlukas.simpleapplication.common.events2.FixedEventSource;

public class BooleanEvent {


	public static class BooleanEventSource extends EventSource<Boolean> {


	}






	public interface BooleanEventListener extends EventListener<Boolean> {


	}






	public static class FixedBooleanEventSource extends FixedEventSource<Boolean> {


		/**
		 * @param event the event to trigger
		 */
		public FixedBooleanEventSource(final Boolean event) {
			super(event);
		}

	}

}
