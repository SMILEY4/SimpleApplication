### SimpleApplication - Events

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