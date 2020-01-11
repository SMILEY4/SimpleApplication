package de.ruegnerlukas.simpleapplication.common.events.specializedevents;

import de.ruegnerlukas.simpleapplication.common.events.EventListener;
import de.ruegnerlukas.simpleapplication.common.events.EventPackage;

public interface EventBusListener<T> extends EventListener<EventPackage<T>> {


}
