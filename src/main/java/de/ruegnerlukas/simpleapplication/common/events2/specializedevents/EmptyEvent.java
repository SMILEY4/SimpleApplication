package de.ruegnerlukas.simpleapplication.common.events2.specializedevents;

import de.ruegnerlukas.simpleapplication.common.events2.EventListener;
import de.ruegnerlukas.simpleapplication.common.events2.EventSource;
import de.ruegnerlukas.simpleapplication.common.events2.TriggerableEventSource;

public final class EmptyEvent {


	/**
	 * An instance of an empty event.
	 */
	public static final EmptyEvent INSTANCE = new EmptyEvent();




	/**
	 * Hidden constructor.
	 */
	private EmptyEvent() {
	}




	public static class EmptyEventSource extends EventSource<EmptyEvent> implements EmptyTriggerableEventSource {


		@Override
		public void trigger() {
			trigger(INSTANCE);
		}

	}






	public interface EmptyTriggerableEventSource extends TriggerableEventSource<EmptyEvent> {


		/**
		 * Triggers this event with {@link EmptyEvent#INSTANCE}.
		 */
		default void trigger() {
			trigger(INSTANCE);
		}

	}






	public interface EmptyEventListener extends EventListener<EmptyEvent> {


	}


}
