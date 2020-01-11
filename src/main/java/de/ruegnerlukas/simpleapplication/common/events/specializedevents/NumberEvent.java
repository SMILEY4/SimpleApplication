package de.ruegnerlukas.simpleapplication.common.events.specializedevents;

import de.ruegnerlukas.simpleapplication.common.events.EventListener;
import de.ruegnerlukas.simpleapplication.common.events.EventSource;
import de.ruegnerlukas.simpleapplication.common.events.FixedEventSource;

public class NumberEvent {


	public static class NumberEventSource extends EventSource<Number> {


	}






	public interface NumberEventListener extends EventListener<Number> {


	}






	public static class FixedNumberEventSource extends FixedEventSource<Number> {


		/**
		 * @param event the event to trigger
		 */
		public FixedNumberEventSource(final Number event) {
			super(event);
		}

	}

}
