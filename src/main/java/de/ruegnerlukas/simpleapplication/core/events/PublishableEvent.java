package de.ruegnerlukas.simpleapplication.core.events;

import de.ruegnerlukas.simpleapplication.common.events.EventListener;
import de.ruegnerlukas.simpleapplication.common.events.EventSource;
import de.ruegnerlukas.simpleapplication.common.events.FixedEventSource;
import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class PublishableEvent {


	@Getter
	@AllArgsConstructor
	public static class PublishableEventSource extends EventSource<Publishable> {


		/**
		 * The {@link Channel} of the event service to publish the {@link Publishable} in.
		 */
		private final Channel channel;




		@Override
		public void trigger(final Publishable event) {
			event.setChannel(channel);
			super.trigger(event);
		}

	}






	public interface PublishableEventListener extends EventListener<Publishable> {


	}






	public static class FixedPublishableEventSource extends FixedEventSource<Publishable> {


		/**
		 * @param event the event to trigger
		 */
		public FixedPublishableEventSource(final Publishable event) {
			super(event);
		}

	}

}
