## SimpleApplication - Plugin Service



### 1. Loading Plugins



#### 1.1 At startup

Plugins can be registered at startup through the ApplicationConfiguration. The application will then load all plugins in the correct order (specified by dependencies).



#### 1.2 At runtime

Plugins can be registered and loaded while the application is running





### 2. Unloading Plugins



#### 1.1 When closing the application

When the application is closed, all plugins will be unloaded automatically (dependend on their specified dependencies).



#### 1.2 At runtime

Plugins can be unloaded at any time. Unloading a plugin on which other plugins depend can lead to them getting unloaded too. However, some (core) plugins can not be unloaded at runtime





### 3. Components

Components can be loaded and unloaded at runtime without registering them first. Plugins can use components as dependencies to make sure a plugin is loaded at a certain point in the application lifecycle.





### 4. Predefined Application Components

- **ApplicationConstants.COMPONENT_VIEW_SYSTEM**
  - loaded after the view service was initialized and is ready to be used
  - unloaded when the application is stopping

