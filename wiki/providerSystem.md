## Provider System

The provider system is an implementation of the service locator pattern. It uses a central registry singleton-class called "ProviderService". Values or instances can be created/provided with factories. We can then request instances and values by identifying classes or strings from this registry through "Providers".

 **Example:**

```java
// register a new singleton factory for the class "MyService"
ProviderService.registerFactory(new InstanceFactory<>(MyService.class) {
    @Override
    public MyService buildObject() {
        return new MyService(...);
    }
});

// Create a provider that requests an instance of "MyService" by the type.
Provider<MyService> myServiceProvider = new Provider<>(MyService.class);

// Get the MyService from the provider.
MyService myService = myServiceProvider.get();
```


#### Factory Parameters

- **Singleton vs Non-Singleton**

  - SINGLETON

    Only create the instance once and then reuse it for all requests. This is the default mode when nothing is specifically provided.

    ```java
    new InstanceFactory<>(User.class) {...}; // singleton by default
    new InstanceFactory<>(User.class, ObjectType.SINGLETON) {...};
    ```

  - NON-SINGLETON

    A new instance will be created by the factory for every Provider, but the same Provider will return the same instance.

    ```java
    new InstanceFactory<>(User.class, ObjectType.NON_SINGLETON) {...};
    
    Provider<User> providerA = new Provider<>(User.class);
    Provider<User> providerB = new Provider<>(User.class);
    User userA = providerA.get();
    User userB1 = providerB.get();
    User userB2 = providerB.get();
    // => userA =/= userB1, userA =/= userB2, but userB1 == userB2
    ```

- **Request Types**

  - CLASS TYPE

    When a class type is passed to the factory. The object can then be requested with that unique type 

    ```java
    new InstanceFactory<>(User.class) {...};
    new Provider<>(User.class);
    ```

  - STRING ID

    A id can be specified when creating the factory. Objects can then be requested with this string.

    ```java
    new InstanceFactory<User>("users") {...};
    new Provider<User>("users");
    ```




#### Configuring Provider Factories at Application Startup

When starting a new application an "ApplicationConfiguration" has to be created and configured. As an alternative to calling "ProviderService.registerFactory" directly, the factories can be added to the configuration instead.

```java
public static void main(String[] args) {
    ApplicationConfiguration configuration = new ApplicationConfiguration();
    configuration.getProviderFactories().add(new StringFactory("app.name", "My Test App"));
    configuration.getProviderFactories().add(new StringFactory("app.version", "0.1"));
    configuration.getProviderFactories().add(new InstanceFactory<>(User.class) {...});
    ...
    new Application(configuration).run();   
}
```

  

#### Overwriting existing Factories

Registering a new factory with the same identifier replaces previously existing factories.
This allows developers to replace exiting default instances with their own implementations.

```Java
ProviderService.registerFactory(new StringFactory("my.string", "old text"));
ProviderService.registerFactory(new StringFactory("my.string", "new text"));
String value = new StringProvider("my.string").get(); // = "new text"
```



#### Factory Types

- **Primitives**

  - Boolean

    ```java
    ProviderService.registerFactory(new BooleanFactory("my.boolean", true));
    boolean value = new BooleanProvider("my.boolean").get();
    ```

  - Double

    ```java
    ProviderService.registerFactory(new DoubleFactory("my.double", 42.0));
    double value = new DoubleProvider("my.double").get();
    ```

  - Float

    ```java
    ProviderService.registerFactory(new FloatFactory("my.float", 42.0f));
    float value = new FloatProvider("my.float").get();
    ```

  - Integer

    ```java
    ProviderService.registerFactory(new IntegerFactory("my.integer", 42));
    int value = new IntegerProvider("my.integer").get();
    ```

  - Long

    ```java
    ProviderService.registerFactory(new LongFactory("my.long", 42L));
    long value = new LongProvider("my.long").get();
    ```

  - String

    ```java
    ProviderService.registerFactory(new StringFactory("my.string", "text"));
    String value = new StringProvider("my.string").get();
    ```

- **Instances**

  ```java
  ProviderService.registerFactory(new InstanceFactory<>(User.class) {
      @Override
      public User buildObject() {
          return new UserImpl("name", 32);
      }
  });
  User value = new Provider<>(User.class).get();
  ```

- **Arrays**

  ```java
  ProviderService.registerFactory(new ArrayFactory<>(User.class, ) {
      @Override
      public User[] buildObject() {
          return new User[]{ new UserImpl("name", 32), ... };
      }
  });
  
  User[] value = new ArrayProvider<User>(User.class).get();
  ```

- **Lists**

  ```java
  ProviderService.registerFactory(new ListFactory<>(User.class) {
  	@Override
  	public List<User> buildObject() {
  		return List.of( new UserImpl("name", 32), ... );
  	}
  });
  List<User> value = new ListProvider<>(User.class).get();
  ```

- **Sets**

  ```java
  ProviderService.registerFactory(new SetFactory<>(User.class) {
  	@Override
  	public Set<User> buildObject() {
  		return Set.of(new UserImpl( "name", 32), ... );
  	}
  });
  Set<User> value = new SetProvider<>(User.class).get();
  ```

- **Maps**

  ```java
  ProviderService.registerFactory(new MapFactory<String, User>("my.user.map") {
  	@Override
  	public Map<String, User> buildObject() {
  		return Map.of("user1", new UserImpl("name", 32), ... );
  	}
  });
  Map<String, User> value = new MapProvider<String, User>("my.user.map").get();
  ```

  

