## SimpleApplication - Plugins



### Loading Plugins



#### At startup

Plugins can be registered at startup through the ApplicationConfiguration. The application will then load all plugins in the correct order (specified by dependencies).



### At runtime

Plugins can be registered and loaded while the application is running





### Unloading Plugins



#### When closing the application

When the application is closed, all plugins will be unloaded automatically (dependend on their specified dependencies).



#### At runtime

Plugins can be unloaded at any time. Unloading a plugin on which other plugins depend can lead to them getting unloaded too. However, some (core) plugins can not be unloaded at runtime