package de.ruegnerlukas.simpleapplication.core.events;

import de.ruegnerlukas.simpleapplication.common.events.EventBusImpl;
import lombok.extern.slf4j.Slf4j;

/**
 * Service to handle global events in the application.
 * Events can be published (as {@link de.ruegnerlukas.simpleapplication.common.events.EventPackage}s) in one or more channels.
 * Channels do not have to be created before usage.
 * EventListeners can subscribe to one or more channels and will receive all events published in the specified channels.
 */
@Slf4j
public class EventService extends EventBusImpl {


}
