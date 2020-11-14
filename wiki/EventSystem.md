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



### 2. Event Bus

The instance of the EventBus can be acquired via a Provider.

```java
Provider<EventBus> eventBusProvider = new Provider<>(EventBus.class);
EventBus eventBus = eventBusProvider.get();
```



#### 2.1 Publishing Events

Any Object can be published via the event bus. Events are then identified by their class-type of added tags.

```java
// publish the object "myEvent" without tags
eventBus.publish(myEvent);

// publish the object "myEvent" with the given tag
eventBus.publish(Tags.from("tag1", "tag2"), myEvent);

// publish an empty event with the given tags
eventBus.publishEmpty(Tags.from("tag1", "tag2"));
```



#### 2.2 Listening to Events

Listeners can subscribe to all or some events.



##### Event/Class Type

Subscribers can subscribe to events of any type or only to events of a specific (class) type.

```java
// subscribe to all events
eventBus.subscribe(SubscriptionData.anyType(), objEvent -> { ... });

// subscribe to all events of type "String"
eventBus.subscribe(SubscriptionData.ofType(String.class), strEvent -> { ... });
```

This system also works with polymorphism. For example, if we have a "DogEvent" which extends "AnimalEvent", we can then subscribe to "AnimalEvent" and will also receive "DogEvents" (but not the other way around)



##### Filter by Tags

Events can be tagged when published. Subscribers can then filter events via these tags.

```java
// subbscribe to all events tagged with "tag1"
eventBus.subscribe(SubscriptionData.anyType(Tags.contains("tag1")), objEvent -> { ... })
eventBus.subscribe(SubscriptionData.anyType().filter(Tags.contains("tag1")), objEvent -> { ... })
    
// subbscribe to all events of type "String" tagged with "tag1"
eventBus.subscribe(SubscriptionData.ofType(String.class, Tags.contains("tag1")), strEvent -> { ... })
eventBus.subscribe(SubscriptionData.ofType(String.class).filter(Tags.contains("tag1")), strEvent -> { ... })
```



##### Subscribing to multiple "sources"

The same subscriber can listen to multiple sources, i.e. can subscribe multiple times with different filters. A subscriber will receive an event only once, event if the event matches multiple filters.

```java
Consumer<String> subscriber = strEvent -> { ... };

eventBus.subscribe(SubscriptionData.ofType(String.class, Tags.contains("tag1")), strEvent -> { ... });
eventBus.subscribe(SubscriptionData.ofType(String.class, Tags.contains("tag2")), strEvent -> { ... });

eventBus.publish(Tags.from("tag1"), myEvent); // subscriber receives event
eventBus.publish(Tags.from("tag2"), myEvent); // subscriber receives event
eventBus.publish(Tags.from("tag1, tag2"), myEvent); // subscriber receives event (only once)

```



##### Unsubscribing

Unsubscribing the listener from the event bus will stop it from receiving any events.

```java
Consumer<String> subscriber = e -> { ... };
//...
eventBus.unsubscribe(subscriber);
```



##### Thread Modes

The Thread-mode of a subscriber will define how and in what thread it receives the events.

```java
eventBus.subscribe(
    SubscriptionData.anyType().threadMode(ThreadMode.POSTING), // set the thread mode to "POSTING" for this subscription
    strEvent -> System.out.print(Thread.currentThread().getName())
);
```

- **Posting**

  The event is received on the same thread as it was published on.

  ```
  1. Subscribe with thread mode "Posting" on "Thread A"
  2. Publish event on "Thread B"
  3. Event is received on "Thread B" 
  ```

- **Async**

  The event is received on a "new" thread (provided by an executor service).

  ```
  1. Subscribe with thread mode "Posting" on "Thread A"
  2. Publish event on "Thread B"
  3. Event is received on "Thread C" 
  ```

- **JFX**

  The event is received on the main JavaFX-Thread

  ```
  1. Subscribe with thread mode "Posting" on "Thread A"
  2. Publish event on "Thread B"
  3. Event is received on "JavaFX Thread" 
  ```

Because the thread mode is specified by the subscribers, the same event can be received on different threads. Because of that, the order in which subscribers receive an event is not guaranteed.



##### Plugin-Linking

Subscriptions can be linked to plugins or components via their id. If the linked plugin is unloaded the subscription gets removed from the event bus.

```java
eventBus.subscribe(SubscriptionData.ofType(String.class).linkedPlugin("my.plugin"), e -> { ... });
// -> unloading "my.plugin" automatically removes the subscription to "String"-events
```





#### 2.4 Existing Application Events

##### Application Started

- fired after everything is done loading (core systems, all plugins, views, ...)

##### Application Stopping

- fired when the application received the request to stop / exit

##### EventPluginRegistered

- fired after a new plugin was registered
- holds the id of the registered plugin

##### EventPluginDeregistered

- fired after a plugin was re-registered
- holds the id of the de-registered plugin

##### EventPluginLoaded

- fired after a plugin was loaded
- holds the id of the loaded plugin

##### EventPluginUnloaded

- fired after a plugin was unloaded
- holds the id of the unloaded plugin

##### EventComponentLoaded

- fired after a component was loaded
- holds the id of the loaded component

##### EventComponentUnloaded

- fired after a component was unloaded
- holds the id of the unloaded component