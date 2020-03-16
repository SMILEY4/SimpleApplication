package de.ruegnerlukas.simpleapplication.core.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
public abstract class Publishable {


	/**
	 * The channel in which to publish instances.
	 */
	@Getter
	@Setter
	private String channel;


	/**
	 * Whether this publishable was cancelled
	 */
	@Getter
	private boolean cancelled = false;




	/**
	 * @param channel the channel in which to publish instances.
	 */
	public Publishable(final String channel) {
		this.channel = channel;
	}




	/**
	 * Stops the propagation of this publishable.
	 */
	public void cancel() {
		cancelled = true;
	}

}
