## The View System

The View system is responsible for providing an easy interface for working with javafx scenes. A scene can be defined as a "View" which can then be displayed in an existing or new window.



### 1. The View

A view can be seen as a blueprint for a ui scene. It consists of the following data:

- **id**: a unique string of that view
- **size**: the size of the window this view should be shown in. Set to null if this view should not change the size of the existing window.
- **minSize** / **maxSize**: the minimum and maximum size of the window (optional).
- **title**: the title of the window
- **styles**: the ids of styles that should be applied to the nodes of this view
- **icon**: the icon of the window
- **dataFactory**: a factory creating a "WindowHandleData", which provides the javafx root-node of the scene 

Example View:

```java
View.builder()
    .id("my.view")
    .size(new Dimension2D(500, 400))
    .title("The Title of the Window")
    .dataFactory(() -> new Pane())
    .icon(Resource.internal("testResources/icon.png"))
    .build();
```

##### Empty Views

The "EmptyView" class is a predefined view that displays nothing and is usually used as a default value.





### 2. Showing a View at Application Startup

To show a view directly after running the application, we have to define it in the application configuration. Doing this instead of opening a view in the "onLoad"-method of a plugin reduces the time it takes to open a window and gives the user immediate feedback. It e.g. can be used as a loading or flash screen.

```java
public static void main(String[] args) {
    ApplicationConfiguration configuration = new ApplicationConfiguration();
    configuration.setShowViewAtStartup(true);    // false by default
    configuration.setView(myView);               // the view to show. An empty view by default
    new Application(configuration).run();
}
```





### 3. Showing a View in an existing Window



#### 3.1 Registering / De-Registering Views

Before we can show a view we have to register it at the "ViewService".

```java
ViewService service = new Provider<>(ViewService.class).get();
service.registerView(myView);
```

We can also de-register views in a similar way.

```java
service.deregisterView("my.view")
```



#### 3.2 Window Handles

A window handle is associated with a currently open window and functions as an identifier for the window and interface between the application logic and that javafx-window.

When opening a new window with the ViewService, the service will return a new WindowHandle connected to the opened window. This handle can then be used to identify the window to display a new view or close it.



#### 3.3 Showing Views in existing Windows

Displaying a new view in an open window:

```java
// display the view in the default/primary window
service.showView("my.view");

// display the view in the window associated with the given handle.
// This will replace the previous view.
service.showView("my.view", myWindowHandle);
```

Before changing to the new, the dispose-method of the previous "WindowHandleData" of the window handle will be called. By default it does nothing, but can be used for custom logic.

```java
WindowHandleDataFactory factory = () -> new WindowHandleData() {
    @Override
    public Parent getNode() {
        return .... ;
    }
    @Override
    public void dispose() {
        // do something here
    }
};
```





### 4. Handling new Windows / Popups

#### 4.1 Popup Configuration

The popup configuration holds information about how to open the new window and how it should behave.

It has the following options:

- **parent**: the parent window or null to use the primary window
- **wait**: whether to pause the logic that opened the window until the window is closed again.
- **modality**: (optional) the "Modality" defining what events get blocked for which windows when the popup is open
- **style**: (optional) the style of the window, e.g. transparent, undecorated, utility, ...
- **alwaysOnTop**: (optional) whether the window is always on top regardless of focus

Example Config:

```java
PopupConfiguration myPopupConfig = PopupConfiguration.builder()
    .parent(null)
    .wait(false)
    .alwaysOnTop(true)
    .build;
```



#### 4.2 Opening a new Window

```java
WindowHandle myWindowHandle = service.popupView("my.view", myPopupConfig);
```

Opens a new window showing the registered view with the id "my.view" and returns the associated window handle.



#### 4.3 Closing an open Window

```java
service.closePopup(myWindowHandle);
```

Closes the window associated with the given handle and marks the handle as inactive. The handle can then no longer be used. 

This will also call the "dispose"-method of the "WindowHandleData".
