package de.ruegnerlukas.simpleapplication.common.events2.specializedevents;

import de.ruegnerlukas.simpleapplication.common.events2.EventListener;
import de.ruegnerlukas.simpleapplication.common.events2.EventPackage;

public interface EventBusListener<T> extends EventListener<EventPackage<T>> {


}
