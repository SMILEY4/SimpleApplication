## SimpleApplication - Events



### Publishing events

1. Get the event service

   ```java
   Provider<EventService> eventServiceProvider = new Provider<>(EventService.class);
   EventService eventService = eventServiceProvider.get();
   ```

2. Publish the event in the specified channel(s)

   ```java
   eventService.publish(myPublishable); // "myPublishable" defines the channel itself
   eventService.publishEmpty(myChannel);
   ```
   
   Listeners can then listen to the channel of "myPublishable" or to "myChannel"



### Subscribing to Events

1. Get the event service

   ```java
   Provider<EventService> eventServiceProvider = new Provider<>(EventService.class);
   EventService eventService = eventServiceProvider.get();
   ```

2. Subscribe to the event(-channel)

   ```java
   // channels by publishable-name or publishable-type
   myChannel = Channel.name("nameOfEvent");
   myChannel = Channel.type(MyEvent.class);
   
   // subscribe to events in "myChannel" with default priority
   eventService.subscribe(myChannel, publishable -> doSomething()); 
   
   // subscribe to events in "myChannel" with a higher priority
   eventService.subscribe(myChannel, 10, publishable -> doSomething());
   
   // subscribe to all channels
   eventService.subscribe(publishable -> doSomething());
   ```





### Available Events

- ##### Channel.type( EventApplicationStarted.class )
  
    - fired after everything is done loading (core systems, all plugins, views, ...)
    
    - Arguments: empty
    
- ##### Channel.type( EventApplicationStopping.class )
    
    - fired when the application received the request to stop / exit
    - before unloading everything (plugins, ...)
- Arguments: empty
    
- ##### Channel.type( EventPresentationInitialized.class )
    
    - fired right after the view-system is done initializing
    - views can be (safely) registered and managed after this event
    - Arguments: empty
    
- ##### Channel.type( EventShowView.class )
    
    - fired after a new view is visible in any stage (primary or popup)
- Arguments: ViewEvent with the window handle and the previous and current view ids
    
- ##### Channel.type( EventOpenPopup.class )
    
    - fired after a new popup is opened
- Arguments: ViewEvent with the window handle and the view id
    
- ##### Channel.type( EventClosePopup.class )
    
    - fired after a new popup is closed
    - Arguments: ViewEvent with the window handle and the view id
    
- ##### Channel.type( EventPluginRegistered.class )

    - fired after a new plugin was registered
    - Arguments: the id of the registered plugin as a string

- ##### Channel.type( EventPluginDeregistered.class )

    - fired after a plugin was deregistered
    - Arguments: the id of the deregistered plugin as a string

- ##### Channel.type( EventPluginLoaded.class )

    - fired after a plugin was loaded
    - Arguments: the id of the loaded plugin

- ##### Channel.type( EventComponentLoaded.class )

    - fired after a component was loaded
    - Arguments: the id of the loaded component

- ##### Channel.type( EventPluginUnloaded.class )

    * fired after a plugin was unloaded

    - Arguments: the id of the unloaded plugin

- ##### Channel.type( EventComponentUnloaded.class )

    - fired after a plugin was unloaded
    - Arguments: the id of the unloaded component