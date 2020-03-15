package de.ruegnerlukas.simpleapplication.core.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Publishable {


	/**
	 * The channel in which to publish instances.
	 */
	private final String channel;


}
