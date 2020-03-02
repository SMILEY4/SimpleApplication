## SimpleApplication - Events



### Publishing events

1. Get the event service

   ```java
   Provider<EventService> eventServiceProvider = new Provider<>(EventService.class);
   EventService eventService = eventServiceProvider.get();
   ```

2. Publish the event in the specified channel(s)

   ```java
   eventService.publish("my_empty_event");
   eventService.publish("my_string_event", new EventPackage<>("My String"));
   eventService.publish("event_a", "event_b", new EventPackage<>(someObject));
   ```





### Subscribing to Events

1. Get the event service

   ```java
   Provider<EventService> eventServiceProvider = new Provider<>(EventService.class);
   EventService eventService = eventServiceProvider.get();
   ```

2. Subscribe to the event(-channel)

   ```java
   eventService.subscribe("the_event", eventPackage -> doSomething());
   eventService.subscribe("event_a", "event_b", eventPackage -> doSomething());
   eventService.subscribe(eventPackage -> doSomething()); // subscribe to all channels
   ```





### Available Events

- ##### ApplicationConstants.EVENT_APPLICATION_STARTED
  
    - fired after everything is done loading (core systems, all plugins, views, ...)
    
- Arguments: empty
  
- ##### ApplicationConstants.EVENT_APPLICATION_STOPPING
    - fired when the application received the request to stop / exit
    - before unloading everything (plugins, ...)
    - Arguments: empty

- ##### ApplicationConstants.EVENT_PRESENTATION_INITIALIZED
    - fired right after the view-system is done initializing
    - views can be (safely) registered and managed after this event
    - Arguments: empty
    
- ##### ApplicationConstants.EVENT_SHOW_VIEW
    - fired after a new view is visible in any stage (primary or popup)
    - Arguments: ViewEvent with the window handle and the previous and current view ids

- ##### ApplicationConstants.EVENT_OPEN_POPUP
    - fired after a new popup is opened
    - Arguments: ViewEvent with the window handle and the view id

- ##### ApplicationConstants.EVENT_CLOSE_POPUP
    - fired after a new popup is closed
    - Arguments: ViewEvent with the window handle and the view id
    
- **ApplicationConstants.EVENT_PLUGIN_REGISTERED**

    - fired after a new plugin was registered
    - Arguments: the id of the registered plugin as a string

- **ApplicationConstants.EVENT_PLUGIN_DEREGISTERED**

    - fired after a plugin was deregistered
    - Arguments: the id of the deregistered plugin as a string

- **ApplicationConstants.EVENT_PLUGIN_LOADED**

    - fired after a plugin was loaded
    - Arguments: the id of the loaded plugin

- **ApplicationConstants.EVENT_COMPONENT_LOADED**

    - fired after a component was loaded
    - Arguments: the id of the loaded component

- **ApplicationConstants.EVENT_PLUGIN_UNLOADED**

    - fired after a plugin was unloaded
    - Arguments: the id of the unloaded plugin

- **ApplicationConstants.EVENT_COMPONENT_UNLOADED**

    - fired after a plugin was unloaded
    - Arguments: the id of the unloaded component