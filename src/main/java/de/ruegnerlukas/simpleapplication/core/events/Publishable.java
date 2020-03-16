package de.ruegnerlukas.simpleapplication.core.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class Publishable {


	/**
	 * The channel in which to publish instances.
	 */
	private String channel;


}
