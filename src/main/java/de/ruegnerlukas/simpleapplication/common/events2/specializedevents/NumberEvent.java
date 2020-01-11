package de.ruegnerlukas.simpleapplication.common.events2.specializedevents;

import de.ruegnerlukas.simpleapplication.common.events2.EventListener;
import de.ruegnerlukas.simpleapplication.common.events2.EventSource;
import de.ruegnerlukas.simpleapplication.common.events2.FixedEventSource;

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
