## The Extension System

The extension system can be used to add data at predefined points ("extension points") in other systems or plugins.

## 1. Defining an Extension Point

```java
// create a new extension point
ExtensionPoint extensionPoint = new ExtensionPoint("my.extension.point");

// allow "strings" to be passed to the extension point and provide a listener that gets called when data of that type or null gets passed to the point.
extensionPoint.addSupportedTypeAllowNull(String.class, data -> { ... });

// allow "integers" but prevent "null"-values from reaching the listener.
extensionPoint.addSupportedTypel(Integer.class, data -> { ... });

// make the point available through the service.
ExtensionPointService service = new Provider<>(ExtensionPointService.class).get();
service.register(extensionPoint);
```



## 2. Using an Extension Point

```java
ExtensionPointService service = new Provider<>(ExtensionPointService.class).get();
service.provide("my.extension.point", String.class, "My String");
service.provide("my.extension.point", Integer.class, 42);
```

