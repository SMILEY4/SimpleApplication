## The Plugin System

The plugin system manages plugins. Plugins can be registered and de-registered before application startup or at runtime. Plugins can depend on other plugins or other systems. When all dependencies of a plugin are loaded, the plugin can be loaded automatically or manually. When a plugin or system gets unloaded, the plugins dependent on that will be handled accordingly. 

Plugins can be user to either modularize an application or to add additional functionality to an existing software.



### 1. The Plugin-Class

All plugins managed by the system must extend from "Plugin". This class provides hooks for when the plugin get loaded and unloaded by the application. It also hold information about the id of the plugin, a readable name, the other plugins it depends on and whether the system should automatically load the plugin when possible.

Example:

```java
class MyPlugin extends Plugin {
    
    public TestPlugin() {
        super(new PluginInformation(
            "my.plugin",            // the unique id of this plugin 
            "My Test Plugin",       // a readable name
            "1.0",                  // the version of the plugin
            false,                  // whether the plugin can be loaded automatically
            Set.of("other.id")      // (optional) the ids of plugins that must be loaded for this plugin to be able to load
        ));
    }
    
    @Override
    public void onLoad() { ... } // called when this plugin gets loaded.
    
    @Override
    public void onUnload() { ... } // called when this plugin gets unloaded.
    
}
```





### 2. Registering and Loading Plugins



#### 2.1 At Application Startup

Plugins can be registered at startup through the ApplicationConfiguration. The application will then load all plugins in the correct order (specified by dependencies). These Plugins are loaded before most other application systems. If a plugin requires one of these systems, see "4. Components".

```java
public static void main(String[] args) {
    ApplicationConfiguration configuration = new ApplicationConfiguration();
    configuration.getPlugins().add(new MyApplication());
    configuration.getPlugins().add(new MyPlugin());
    configuration.getPlugins().add(new OtherPlugin());
    ...
    new Application(configuration).run();   
}
```



#### 2.2 At Runtime

Plugins can also be registered and loaded at runtime via the plugin system

```java
PluginService service = new Provider<>(PluginService.class).get();

Plugin plugin = new MyPlugin();
service.registerPlugin(plugin);
```

We (usually) need to load plugins registered this way manually.

```java
// fails when a dependency is not yet loaded.
service.loadPlugin(plugin.getId());

// tries to load all the required plugins the given plugin depends on.
service.loadPluginWithDependencies(plugin.getId())
```



#### 2.3 Marked as "Autoload"

When a plugin is marked as "autoload", the checks everytime an other plugin was loaded if it can be loaded now too.

```java
// register the plugin marked as autoload.
service.registerPlugin(myAutoloadPlugin);

// loads the other plugin required for "myAutoloadPlugin".
service.loadPlugin("my.dependency");

// => "myAutoloadPlugin" get loaded automatically after the plugin "my.dependency".
```





### 3. De-Registering and Unloading Plugins

Loaded plugins can be unloaded at any time. Unloading a plugin causes all other plugins that depend on it to unload too.

```java
service.unloadPlugin("my.plugin");
```

After a plugin was unloaded it can be loaded again as long as it is registerd. A registered and unloaded plugin can be deregistered.

```java
service.deregisterPlugin("my.plugin");
```





### 4. Components

Components are systems that are integrated into the plugin system but are not plugins. They get be loaded and unloaded like plugins and plugins can depend on components. Components to not need to be registered before loading them.



#### 4.1 Loading and Unloading

Loading a component can also load plugins that depend on it and are marked as autoload. Unloading a plugin causes dependent plugins to also unload.

```java
// load a new component with the id "my.component".
service.loadComponent("my.component");

// unload the component with the id "my.component".
service.unloadComponent("my.component");
```

Components can be used have plugins depend on external systems, like for example JavaFx.



#### 4.2 Predefined Components

- ##### View System

  - id of the component is "ApplicationConstants.COMPONENT_VIEW_SYSTEM"
  - is loaded when the view system is initialized.

