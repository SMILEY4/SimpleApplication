package de.ruegnerlukas.simpleapplication.common.events.specializedevents;

import de.ruegnerlukas.simpleapplication.core.events.EventPackage;

public class EmptyEventPackage extends EventPackage<EmptyEvent> {


	/**
	 * Default constructor.
	 */
	public EmptyEventPackage() {
		super(EmptyEvent.INSTANCE);
	}

}
