## The Event System

The framework provides a simple but flexible independent event source and subscriber system. In addition, a global event-bus allows different components and plugins to communicate with each other via channels.



### 1. Event Sources and Subscribers



#### 1.1 Event Sources

Event sources can trigger events. Listeners can subscribe to event sources to receive the events triggered by these sources.

```java
// Create a new event source that triggers events of the type "string".
EventSource<String> source = new EventSource<>();

// listen to events of the source.
source.subscribe(event -> { ... });

// trigger a new event.
source.trigger("My Event");
```

Event sources are composed of a "listenable-event-source" and a "triggerable-event-source". This can be useful if we want to give another object access to an event source but restrict access to a functionality, e.g. triggering events.

```java
// create new event source.
EventSource<String> source = new EventSource<>();

void handleTriggerEvents(TriggerableEventSource<String> triggerableSource) {
    // no (direct) access to "subscribe" / "unsubscribe" here.
    triggerableSource.trigger("My Event");
}

void handleListenEvents(ListenableEventSource<String> listenableSource) {
    // no (direct) access to "trigger" here.
    listenableSource.subscribe(event -> {...});
}
```

If we want an event source that always triggers the same event, we can use the "FixedEventSource" (which extends the normal event source). We can define the event to trigger when creating the source. This event source can then only trigger that predefined event.

```java
// Create a new fixed event source that always triggers the event "My Event".
EventSource<String> fixedSource = new FixedEventSource<>("My Event");
fixedSource.trigger();
```



####  1.2 Predefined Event Types

When we know what type the event to trigger is (e.g. a string), we can use the more specialized event source and listeners, instead of the generic event source. 

##### Boolean Events

```java
BooleanEventSource source = new BooleanEventSource();
FixedBooleanEventSource fixedSource = new FixedBooleanEventSource();
BooleanEventListener listener = event -> { ... };
```

##### String Events

```java
StringEventSource source = new StringEventSource();
FixedStringEventSource fixedSource = new FixedStringEventSource();
StringEventListener listener = event -> { ... };
```

##### Number Events

```java
NumberEventSource source = new NumberEventSource();
FixedNumberEventSource fixedSource = new FixedNumberEventSource();
NumberEventListener listener = event -> { ... };
```

##### Empty Events

Empty events to not hold any data and just notify the listeners.

```java
EmptyEventSource source = new EmptyEventSource();
EmptyEventListener listener = event -> { ... };
```



#### 1.3 Event Groups

If we need to pass multiple event sources around, it might be easier to group them together. "ListenableEventSourceGroups "and "TriggerableEventSourceGroups "exist.

EventSource can be added to a group together with an unique channel identifying the source. Sources can then be requested from the group with the identifying channel.

A "ListenableEventSourceGroup" only returns "TriggerableEventSources" and a "ListenableEventSourceGroups " only "ListenableEventSources".  This helps to manage access to event source functionalities when passing the sources/groups to other systems.

```java
// create event sources
StringEventSource sourceA = new StringEventSource();
BooleanEventSource sourceB = new BooleanEventSource();

// create triggerable group and add event sources identified by an unique channel.
TriggerableEventSourceGroup triggerableGroup = new TriggerableEventSourceGroup();
triggerableGroup.add(Channel.name("event.a"), sourceA);
triggerableGroup.add(Channel.name("event.b"), sourceB);

// create listenable group and add event sources identified by an unique channel.
ListenableEventSourceGroup listenableGroup = new ListenableEventSourceGroup();
listenableGroup.add(Channel.name("event.a"), sourceA);
listenableGroup.add(Channel.name("event.b"), sourceB);

// find a listenable source from the group and subscribe to it
ListenableEventSource<Boolean> listenable = groupListenable.find(Channel.name("event.b"));
listenable.subscribe(e -> { ... });

// find a triggerable source from the group and trigger an event
TriggerableEventSource<String> triggerable = groupTriggerable.find(Channel.name("event.a"));
triggerable.trigger("My Event");
```



### 2. Event System / Bus

The instance of the EventService can be acquired via a Provider.

```java
Provider<EventService> eventServiceProvider = new Provider<>(EventService.class);
EventService eventService = eventServiceProvider.get();
```



#### 2.1 Publishing Events

Example Event that can be broadcast on a given channel. Events that should be published via the event bus must extend "Publishable".

```java
public class MyEvent extends Publishable {
    public MyEvent(final Channel channel) {
        super(channel);
    }
}
```

Events can then be published on specific channels. Only listeners that are subscribed to that channel receive that event.

```java
eventService.publish(new MyEvent(Channel.name("name.of.event")));
```

If we do not need to send any data with the event, we can use the  "publishEmpty" method.

```java
eventService.publishEmpty(Channel.name("name.of.event"));
```



####  2.2. Channels

Events can be broadcast to specific channels. Listeners can then subscribe to a channel to receive any event published on that channel. Channels can be identified by either a string or a class-type. If an event is published on a channel that does not exist, it will be automatically created.

- **By String**

  ```java
  new MyEvent(Channel.name("name.of.event"));
  ```

- **By Class-Type**

  ```java
  new MyEvent(Channel.type(MyEvent.class));
  ```



#### 2.3 Listening to Events

Listeners can subscribe to specific channels with a priority. The listener with the larges priority value receives the event first. If no priority is specified, the default value of 0 will be used.

```java
// create a new event listener
PublishableEventListener listener = new PublishableEventListener() {
    @Override
    public void onEvent(final Publishable event) { ... }
};

// subscribe to "MyEvent"-channel with default priority ( = 0).
eventService.subscribe(Channel.type(MyEvent.class), listener);

// subscribe to "MyEvent"-channel with a priority of 10.
eventService.subscribe(Channel.type(MyEvent.class), 10, listener);

// subscribe to "MyEvent"-channel using a lamda-expression.
eventService.subscribe(Channel.type(MyEvent.class), publishable -> { ... });
```

A listener can also subscribe to all event channels and receive all published events. Listeners subscribed to all channels will be called after the listeners that have subscribed to a specific channel have been called.

```java
eventService.subscribe(publishable -> {...});
```

##### **Unsubscribing**

Listeners can unsubscribe from a specific channel to not receive new events anymore.

```java
PublishableEventListener listener = ... ;
...
eventService.unsubscribe(Channel.type(MyEvent.class), listener);
```

If a listener was subscribed to multiple channels at once, we can unsubscribe the listener from all channels at once.

```
eventService.unsubscribe(listener);
```

##### Canceling Events and Event-Metadata

- Events published via the event bus can be canceled. When an events gets canceled by a listener, the event stops and no other listener will receive the event anymore.

    ```java
    eventService.subscribe(Channel.name("my.event"), event -> event.cancel());
    ```

- Publishable events also hold a metadata object that has information about that specific event.
  - published: whether the event was already published via the event system  
  - canceled: whether the event was canceled (by a listener).
  - channel: the channel on which the event was published
  - timestamp: the timestamp when the event was published
  - numListeners: the number of subscribers, i.e. the number of potential receivers
  - numReceivers: the number of listeners that actually received the event

##### Annotated Listeners

Listeners can also be configured by annotating methods with the @Listener-annotation and registered at the event service.

```java
class MyListener {
    
    @Listener (name = "my.channel")
    public void listenToEvents1(final Publishable publishable) {
        // a method listening to "my.channel" with the default priority.
    }
    
    @Listener (name = "my.channel", priority)
    public void listenToEvents2(final Publishable publishable) {
        // a method listening to "my.channel" with a priority of 10.
    }
    
    @Listener (type = MyEvent.class)
    public void listenToEvents3(final Publishable publishable) {
        // a method listening to "MyEvent"-channel with the default priority.
    }
    
}

MyListener listener = new MyListener();

eventService.register(listener);
```

 If we only need to listen to events and do not need the listener itself as an instance, we can also annotate static methods and register the class.

```java
class MyListener {

	@Listener (name = "my.channel")
	public static void listenToEvents(final Publishable publishable) {
		// a static method listening to "my.channel" with the default priority.
	}

}

eventService.register(MyListener.class);
```



#### 2.4 Existing Application Events

##### EventApplicationStarted

- fired after everything is done loading (core systems, all plugins, views, ...)
- Channel.type( EventApplicationStarted.class )

##### EventApplicationStopping

- fired when the application received the request to stop / exit

- Channel.type( EventApplicationStopping.class )

##### EventPresentationInitialized

- fired right after the view-system is done initializing
- views can be (safely) registered and managed after this event
- Channel.type( EventPresentationInitialized.class )

##### EventShowView

- fired after a new view is being displayed in any stage (primary or popup)
- holds data about the previous and next view-Id and the window handle.

- Channel.type( EventShowView.class )

##### EventOpenPopup

- fired after a new popup window was opened
- holds data about the view-id and the window handle.
- Channel.type( EventOpenPopup.class )

##### EventClosePopup

- fired after a popup window was closed

- holds data about the view-id and the window handle.

- Channel.type( EventClosePopup.class )

##### EventPluginRegistered

- fired after a new plugin was registered
- holds the id of the registered plugin
- Channel.type( EventPluginRegistered.class )

##### EventPluginDeregistered

- fired after a plugin was re-registered
- holds the id of the de-registered plugin
- Channel.type( EventPluginDeregistered.class )

##### EventPluginLoaded

- fired after a plugin was loaded
- holds the id of the loaded plugin
- Channel.type( EventPluginLoaded.class )

##### EventPluginUnloaded

- fired after a plugin was unloaded
- holds the id of the unloaded plugin
- Channel.type( EventPluginUnoaded.class )

##### EventComponentLoaded

- fired after a component was loaded
- holds the id of the loaded component
- Channel.type( EventComponentLoaded.class )

##### EventComponentUnloaded

- fired after a component was unloaded
- holds the id of the unloaded component
- Channel.type( EventComponentUnloaded.class )