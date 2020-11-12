## SimpleUI

SimpleUI is an UI framework/library build on top of JavaFx. Its split into two general parts: The UI state and the scene tree. SimpleUI constructs the scene tree from the state and node factory which can then be shown to the user. The same state produces always the same scene. To create interactive interfaces, the state can be updated which then triggers SimpleUI to modify (or rebuild) the existing scene tree to match the new state.





### 1. Getting Started

Short walkthrough on how to create a small gui just with SimpleUI and JavaFX.



##### 1. Creating the empty JavaFx Application

```java
public class SimpleUIDemo extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) {
        stage.show();
    }
    
}
```



##### 2. Initializing SimpleUI

SimpleUI has to setup a few things before the application start and uses simple ui. This can be done with a single call to "SuiRegistry.initialize"

```java
public class SimpleUIDemo extends Application {

    //...
    
    @Override
    public void start(Stage stage) {
        SuiRegistry.initialize();
        stage.show();
    }
    
}
```



##### 3. Creating our UI-State

```java
public class SimpleUIDemo extends Application {

    //...
    
    @Override
    public void start(Stage stage) {
        SuiRegistry.initialize();
        
        MyState uiState = new MyState();
        
        stage.show();
    }

}

class MyState extends SuiState {
    public String text = "text";
}
```



##### 4. Creating the scene controller

The scene controller is responsible for managing the scene, i.e. the scene tree, ui state and so on. We can also customize the window here.

```java
public class SimpleUIDemo extends Application {

    //...
    
    @Override
    public void start(Stage stage) {
        SuiRegistry.initialize();
        
        MyState myState = new MyState();
        
		SuiSceneController controller = new SuiSceneController(
        	myState,
        	WindowRootElement.windowRoot(stage)        
            	.title("SimpleUI Demo")
            	.size(500, 400)
            	.content(MyState.class, state -> /*TODO*/)
        );
        
        controller.show();
    }

}

//...
```



##### 5. Creating the user interface

We have to tell SimpleUI how to create a scene tree / interface from the current state. To do that we can nest elements and assign properties to then. More on that later.

```java
public class SimpleUIDemo extends Application {

    //...

    @Override
    public void start(Stage stage) {
        SuiRegistry.initialize();
        
        MyState myState = new MyState();
        
		SuiSceneController controller = new SuiSceneController(
        	myState,
        	WindowRootElement.windowRoot(stage)        
            	.title("SimpleUI Demo")
            	.size(500, 400)
            	.content(MyState.class, state -> buildUI(state))
        );
        
        controller.show();
    }
    
    private NodeFactory buildUI(MyState currentState) {
        return SuiElements.button()
                .textContent("Button: " + currentState.text)
                .eventAction(".", event -> System.out.println("Click!"));
    }
    
}

//...
```

We now have a scene with a single button with the text "Button: text" which prints "Click!" into the console when pressed.



##### 6. Making the Interface more dynamic

To change the interface, we have to update the state and the ui will then update accordingly. In this example pressing the button will count up the value in the state and display it in the button.

```java
public class SimpleUIDemo extends Application {

    //...

    private NodeFactory buildUI(MyState currentState) {
        return SuiElements.button()
                .textContent("Button: " + currentState.text)
                .eventAction(event -> {
                    // we can not set the value of the state directly. This will not update the interface
                    currentState.update(state -> state.value++);
                });
    }
    
}

class MyState extends SuiState {
    public int value = 0;
}
```



### 2. Structuring more complex User Interfaces

There are two options of breaking up the code into smaller parts. By using SimpleUI Components or normal methods. Both options are equally valid and produce the same result.

#### 2.1 Before: No Components or Methods

```java
private NodeFactory buildUI(MyState currentState) {
	return SuiElements.vBox()
			.spacing(5)
			.alignment(Pos.CENTER)
			.items(
					SuiElements.button()
							.textContent("Button 1 (" + state.value + ")")
							.eventAction(".", e -> currentState.update(MyState.class, s -> s.value++)),
					SuiElements.button()
							.textContent("Button 2 (" + state.value + ")")
							.eventAction(".", e -> currentState.update(MyState.class, s -> s.value++)),
					SuiElements.button()
							.textContent("Button 3 (" + state.value + ")")
							.eventAction(".", e -> currentState.update(MyState.class, s -> s.value++))
	);
}
```



#### 2.2. SimpleUI-Components

```java
private NodeFactory buildUI(MyState currentState) {
	return SuiElements.vBox()
			.spacing(5)
			.alignment(Pos.CENTER)
			.items(
					new MyComponent("Button 1"),
					new MyComponent("Button 2"),
					new MyComponent("Button 3")
			);
}

//...

class MyComponent extends SuiComponent<MyState> {
    
    public MyComponent(String title) {
        super(state -> SuiElements.button()
        		.textContent(title + " (" + state.value + ")")
        		.eventAction(e -> state.update(MyState.class, s -> s.value++))
        );
    }
    
}
```



#### 2.3. Normal Methods

```java
private NodeFactory buildUI(MyState currentState) {
	return SuiElements.vBox()
			.spacing(5)
			.alignment(Pos.CENTER)
			.items(
					myComponent(state, "Button 1"),
					myComponent(state, "Button 2"),
					myComponent(state, "Button 3")
			);
}

private NodeFactory myComponent(MyState state, String text) {
	return SuiElements.button()
			.textContent(text + " (" + state.value + ")")
			.eventAction(e -> state.update(MyState.class, s -> s.value++));
}
```





### 3. Updating the UI-State

For a more advanced look at how updating the state modifies the interface, see "4. The Mutation Process"

#### 3.1 Normal Update

The normal update modifies the state in a safe way on the main JavaFx-thread. This is the only (recommended) way to update the state when we also want to modify the interface.

```java
myState.update(MyState.class, state -> {
    // state.setXY
});
```

 

#### 3.2 Silent Update

A silent update will modify the state but does not trigger any listeners or updates the interface. Updating the state in this way basically decouples the current state from the current interface and is not not recommended, but MAY be useful in some rare cases.

```java
myState.update(MyState.class, true, state -> { // true -> do silent update
    // state.setXY
});
```



#### 3.3 Unsafe Update

An unsafe update will modify the state and call the listeners on the current thread. This causes problems when the interface gets modified and the current thread is not the main JavaFx-thread.

```java
myState.updateUnsafe(MyState.class, true, state -> {
    // state.setXY
})
```



#### 3.4 Listening to Updates

Listeners can be added to the ui state that get notified of any updates.

```java
myState.addStateListener((state, update, tags) -> {
    // called after the state was updated.
});

state.addStateListener(new SuiStateListener() {
    @Override
    public void beforeUpdate(SuiState state, SuiStateUpdate<?> update) {
        // called before the state is updated
    }
    @Override
    public void stateUpdated(SuiState state, SuiStateUpdate<?> update, Tags tags) {
         // called after the state was updated
    }
});
```



### 4. The Scene Tree

SimpleUI holds an internal tree-representation of the current scene. Each node in this tree has a type, a list of properties and a list of child nodes. In addition to that, the nodes of the current tree know the javafx-node. This way, SimpleUI can work and manipulate the simpler representation of the interface and then update the javafx-nodes linked to the tree-nodes. 



### 5. The Mutation Process

When updating the ui state, the controller triggers the mutation process. The controller takes the current state, applies the update and then builds a completely new tree from that updated state. This new tree gets than compared to the current scene tree. The mutation process then tries to modify the properties and child-nodes in such a way that the tree matches the new target tree. If a node can not be modified to match the target node, it gets rebuild completely, i.e. gets replaced by the node from the new tree.



#### 5.1 The Mutation Algorithm

The mutation algorithm modifies an original tree to match a given target tree. It start by comparing the roots and then continues with the child nodes, recursively iterating over the whole trees (breadth-first), while making modifications to the original tree. For two nodes to be equal, they must have the same type (e.g. "button", "label", ...) and have the same properties with the same values.

If the type is different, the whole node has to be replaced by a completely new node (and therefore also the subtree with that node as root). 

If only the properties are different, SimpleUI can replace, remove or add properties without rebuilding parts of the tree.

When mutating child nodes of a parent node, SimpleUI simply iterates over both lists of children by default and mutates/rebuilds the original child or removes and adds child nodes where necessary. However, inserting a new child at the beginning for example can result in every child-pair to be different, resulting in unnecessary modifications. To avoid that, the "id-property" can be added to any node. If all child nodes of the original and target list have an id property, a more efficient strategy can be used. Instead of iterating over both lists, the algorithm can just compare child nodes with the same id.

The behavior when a node should be mutated can be customized with the mutation behavior property.



#### 5.2 Example Walkthrough:

![mutationExample](D:\LukasRuegner\Programmieren\Java\Workspace\SimpleApplication\wiki\mutationExample.png)

- Current Tree: the displayed scene tree
- Target Tree: the tree created from the new state after an update
- Mutated Tree: the original / current tree modified to match the target tree

**After updating the state, a few things have changed** (see "Current Tree" vs "Target Tree")

1. The event-handler of "Button #3" was removed.

2. the text of "Button #4" was changed.

3. the "Label #5" was changed to a button.

4. the new node "Checkbox #7*" was added to the vbox.

**The mutation process does the following things** (see "Mutated Tree" for the result)

1. start at root node and compare it -> neither the child nodes, nor the properties changed, it continues the process with the child nodes
2. It the checks "VBox #2" - no properties have been changed here too and the process continues to check the child nodes of that vbox
3. It checks the difference between "Label #5" and "Button #5" and tries to modify the node in the current tree. Since the basic type of the node changed (from a label to a button), it can not be mutated and has to be rebuild. To to that, the original node ("Label #5") gets replaced by the target node ("Button #5*")
4. It continues to check the child nodes and finds the added node "Checkbox #7*". This this node did not exist in the original tree, so it can be moved/copied over from the target tree and added to the underlying javafx-node.
5. After checking all children (and added children) of "VBox #2", it continues with "Button #3". It notices the removed property "event" and removes it from the node (in the current tree) and unterlying javafx-node.
6. The last difference - the changed "text"-property of "Button #4" can also be mutated in the current tree by replacing it with the new property and then updating the unterlying javafx-node.







### 6. UI-Elements

#### 6.1 Overview

An UI-Element in Simple-UI is less of an element like in JavaFX and more a collection of factories, systems and configurations for SimpleUI-Nodes. SimpleUI comes with a collection of pre-build elements. Existing elements can either be modified or new elements can be created.

An element consists of tree basic parts:

- **The Node Factory**

  The node factory create a new SimpleUI-Node from a given ui-state. Usually, this factory is wrapped inside an additional  builder to only expose valid properties of that element.

- **The JavaFx-Node Builder**

  The node builder creates a new basic JavaFx-node from a given SimpleUI-node. Usually, no properties are considered here. The data from the properties are added to the JavaFx-node in a separate step.

- **Registering an Element**

  Every UI Element has to be registered at the SUIRegistry before it can be used. Pre-Build elements are automatically registered when initializing the registry with "SuiRegistry.initialize();" This step tells SimpleUI what properties the element can use, how to use them and how to construct the JavaFx-node.

  ```java
  // get the instance of the registry. The registry must be initialized first.
  SuiRegistry registry = SuiRegistry.get();
  
  // register the javafx-node builder for the element of the type "MyElement".
  registry.registerBaseFxNodeBuilder(MyElement.class, new MyElementNodeBilder());
  
  // allow the "SizeProperty" for "MyElement" with the "RegionUpdatingBuilder" responsible for modifying the javafx-node.
  registry.registerProperty(MyElement.class, SizeProperty.class, new SizeProperty.RegionUpdatingBuilder());
  ```

  For a more detailed explanation, see "6.3 Custom Elements".

##### The Element "Lifecycle"

![suiElementLifecycle](D:\LukasRuegner\Programmieren\Java\Workspace\SimpleApplication\wiki\suiElementLifecycle.png)



#### 6.2 Pre-Build Elements

##### Example: Button

```java
SuiElements.button()
	.id("myButton") // (1)
    .textContent("My Button (" + state.counter + ")") // (2)
    .eventAction(".", e -> state.update(MyState.class, s -> s.counter++)); // (3)
```

- Creates a node factory for a simpleui-node of the button-type with the  given three properties.
  1. an id property. Used to identify the button during the mutation process
  2. the text content property. Defines the text to display on the button
  3. an event property. The listener specified in the property get called when the button is pressed. The listener here then updates the state and increments the counter. This will then start the mutation process, updating the text content property and JavaFx-node to show the new text.

##### Example: VBox

```java
SuiElements.vBox()
		.spacing(5) // (1)
		.items( // (2)
				IntStream.range(0, state.counter).mapToObj( // (3)
						index -> SuiElements.label().textContent("Item " + index)
				)
		);
```

- Creates a node factory for a simpleui-node of the vbox-type with the  given properties and the resulting child nodes.
  1. the property defining the spacing between the items of the vbox
  2. the property defining the items /child-nodes. Child nodes can be constructed in multiple ways (See "7.2.1 ItemList and Item Property" for more information). Here, a stream of node factories is given to the items-property.
  3. Creates n child nodes, where n is the current value of the counter of the ui-state. When the value changes, child nodes will be automatically added/removed during mutation.





#### 6.3 Custom Elements

To create a custom element, you need the parts listed in "6.1 Overview". Usually, everything making up an element is defined in a single class.



##### 6.3.1 Creating the Node Factory

(Skip this step if you want to use a builder from 6.3.4)

The node factory looks mostly the same for all elements.

```java
public static NodeFactory myElement(List<SuiProperty> properties) {
	return (state, tags) -> SuiNode.create( // create a NodeFactory which build the simpleui-node
			MyElement.class, 		// the type of the node. Usually just the class it is defined in. "MyElement" in this case
			properties,				// the properties of the node
			state,					// the state
			tags					// the update tags
	);
}
```

It is recommended to validate the given properties:

```java
// check that the given property array is not null
Validations.INPUT.notNull(properties).exception("The properties may not be null.");
```

```java
// check that the given properties do not conatin any null values
Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
```

```java
// Checks that all properties are valid and allowed for "MyElement"-nodes.
PropertyValidation.validate(MyElement.class, SuiRegistry.get().getEntry(MyElement.class).getProperties(), properties);
```



##### 6.3.2 Creating the JavaFx-Node-Builder

```java
// a JavaFx-node-builder that creates a JavaFx-Button
class MyElementNodeBuilder implements AbstractFxNodeBuilder<Button> {

    @Override
    public Button build(SuiNode node) {
        return new Button(); // return the base JavaFx-node
    }

}
```



##### 6.3.3 Registering the new Element and its properties

```java
MyElement.register(SuiRegistry.get());

public static void register(SuiRegistry registry) {

    // register the JavaFx-Node-Builder
    registry.registerBaseFxNodeBuilder(MyElement.class, new MyElementNodeBuilder());

	// allow all of the common, common-region and common- event  properties
    registry.registerProperties(MyElement.class, PropertyGroups.commonProperties());
    registry.registerProperties(MyElement.class, PropertyGroups.commonRegionProperties());
    registry.registerProperties(MyElement.class, PropertyGroups.commonEventProperties());
    
    // allow some extra properties
    registry.registerProperties(MyElement.class, List.of(
        PropertyEntry.of(TextContentProperty.class, new TextContentProperty.LabeledUpdatingBuilder()),
	    PropertyEntry.of(WrapTextProperty.class, new WrapTextProperty.LabeledUpdatingBuilder()),
    ));
    
    // => only properties registered here can be used with this element
    
}
```



##### 6.3.4 Wrapping the Node Factory in a Builder

The wrapper allows the "user" to only* add properties to the element that are exposed by the builder.

```java
public static MyElementBuilder myElement() {
    return new MyElementBuilder();
}

public static class MyElementBuilder extends BuilderExtensionContainer implements
        BaseBuilderExtension<MyElementBuilder>, // expose all "common"-properties
        RegionBuilderExtension<MyElementBuilder>, // expose all "region"-properties
        CommonEventBuilderExtension<MyElementBuilder>, // expose all "common-event"-properties
        TextContentProperty.PropertyBuilderExtension<MyElementBuilder>,
        WrapTextProperty.PropertyBuilderExtension<MyElementBuilder> {


    @Override
    public SuiNode create(final SuiState state, final Tags tags) {
        return create(
                MyElement.class,
                state,
                tags
        );
    }

}
```

```java
SuiElements.vBox()
    .items(
        myElement()
            .textContent("My Element 1")
            .wrapText(true)
        myElement()
            .textContent("My Element 1")
            .wrapText(true)
    );
```



#### 6.4 Customizing Elements

Already existing elements can be extended with new properties or existing builders/updaters of properties can be overwritten.

Example adding/overwriting the property "MyProperty" to the existing "SuiButton"-Element:

```java
// new properties can be added to element by just registering them at the registry with the type of the element 
SuiRegistry.get().registerProperty(
    SuiButton.class,
    MyProperty.class,
    new MyProperty.UpdatingBuilder()
);
```





### 7. Properties

A property, that is allowed for a given element-type, can be added to nodes of that type. Properties hold some data. If that data is changed (though a state update and the following mutation process) the change is reflected to the JavaFx-Node.

Every Property also defines a "NodeBuilder" and an (optional) "NodeUpdater". The node builder applies data to a newly create JavaFx-Node.

When the property gets removed, the "remove"-method of the updater is called with the removed property and when a property is added or modified, the "update"-method of the updater is called with the new property.



#### 7.1 The Property-Id

During the mutation process, the algorithm has to check if two properties are the same or if the original property was changed. Most of the time, a comparison function comparing the data of the two properties is enough. But for some properties, this method does not work. For example for properties adding an event listener. When the listener is defined as a lambda, the instance of the listener is always a different one between two properties,  thereby always modifying the original simpleui-node and javafx-node, event if the logic inside the lambda stays exactly the same.

This can be avoided using property-ids. Some properties require an property-id. Two properties with the same id are automatically considered equal, solving the problem and preventing unnecessary modifications. 

Example:

```java
// the lambda function results in different instances. Without an id, this will always trigger an unnecessary modification.
SuiButton.button(
    EventProperties.eventAction(event -> System.out.println("Clicked!"))		
);

// Here, the instance of the listener stays the same, avoiding the modification, but adding additional code.
SuiEventListener<ActionEventData> listener = event -> System.out.println("Clicked!");
SuiButton.button(
    EventProperties.eventAction(listener)
);

// Adding an id to the property avoids the modification. The instance of the listener changes, but the id stays the same.
SuiButton.button(
    EventProperties.eventAction("my.listener", event -> System.out.println("Clicked!"))
);
```



#### 7.2 Common / Important Properties



##### 7.2.1 ItemList and Item Property

The item-list-property and item-property specify the child nodes of a node. Child nodes can be defined/created in different ways.

Creating children with the item-list-property:

- with an array

  ```java
  Properties.items(
          SuiButton.button(...),
          SuiButton.button(...),
          ...
  )
  ```

- with a list

  ```java
  Properties.items(
          List.of(
              SuiButton.button(...),
              SuiButton.button(...),
              ...
          )
  )
  ```

- with a stream

  ```java
  Properties.items(
      IntStream.range(0, 5).mapToObj(i -> SuiButton.button(...))
  );
  ```

- with a factory

  ```java
  Properties.items(
      () -> {
          List<NodeFactory> buttons = new ArrayList<>();
          for (int i = 0; i < 5; i++) {
              buttons.add(SuiButton.button(...));
          }
          return buttons;
      }
  );
  ```

To inject children into existing nodes from the outside, The injectable item properties can be used. See "9. Injecting Node" for more details.



##### 7.2.2 Size Properties

The Size Properties (size, min-size, preferred-size, max-size) can be added to almost all nodes. They specify how big a node should be.

The size-property combines the three other properties in one, making it possible to specify e.g. the min-size twice with different values. In these cases, the more specific property, i.e. the min-size-property always has priority over the more generic size-property. When one of the two is removed, the values of the other property is used instead.



##### 7.2.3 Event Properties

Event properties can be used to add listeners to nodes for certain events. There are a lot of properties for different events available. Some can be added to (almost) all nodes (e.g.: "EventProperties.eventFocusChanged" when the node get focused/unfocused) some can only be added to specific nodes.

Property-Ids are added to every event-property, preventing unnecessary mutations when using lambdas for example (see "7.2 The Property-Id").

Event Properties can also be used in combination with SimpleUI-Streams (See "10. SimpleUI Streams").

For more information about events, see 8. Events



##### 7.2.4 Id Property

See "8.2 The Id Property"



##### 7.2.5 Mutation Behavior Property

See "8.2 The Mutation Behavior Property"



##### 7.2.6 Properties "Property"

This is not a real property, but an exposed function of element builders (see 6.3.4). It allows the developer to add properties to an element via an builder that were not exposed by said builder.

```java
SuiElements.button()
	.properties(
        new MyProperty("Some Text") // "MyProperty" is not exposed by the "SuiButton-Builder". 
    );
```

Properties added this way must still be registered with the element (See 6.3.3).



#### 7.3 Custom Properties

The property consists of four parts.

- The property itself holding the data
- a comparison function checking two properties for equality
- One or more "NodeBuilder" for one or more JavaFx-node-types
- One or more "NodeUpdater" for one or more JavaFx-node-types



**The Property holding the data**

```java
class MyProperty extends SuiProperty {

    // all data of a property should be declared as final
    public final String text;

    public MyProperty(String text) {
        super(MyProperty.class, /*TODO*/); // MyProperty.class is the idenfying type of this property
        this.text = text;
    }

}
```

**The Comparison function**

```java
class MyProperty extends SuiProperty {

    private static final BiFunction<MyProperty, MyProperty, Boolean> COMPARATOR = (a, b) -> a.text.equals(b.text);

    ...

    public MyProperty(String text) {
        super(MyProperty.class, COMPARATOR);
        this.text = text;
    }

}
```

**The Builder**

```java
class MyProperty extends SuiProperty {

    ...

    public static class Builder implements PropFxNodeBuilder<MyProperty, Labeled> {

        @Override
        public void build(SuiNode node, MyProperty property, Labeled fxNode) {
            fxNode.setText(property.text);
        }
        
    }

}
```

- build: called once when the JavaFx-node is created.

**The Updater**

```java
class MyProperty extends SuiProperty {

    ...

    public static class Updater implements PropFxNodeUpdater<MyProperty, Labeled> {

        @Override
        public MutationResult update(MyProperty property, SuiNode node, Labeled fxNode) {
            fxNode.setText(property.text);
            return MutationResult.MUTATED; 
        }

        @Override
        public MutationResult remove(MyProperty property, SuiNode node, Labeled fxNode) {
            fxNode.setText(null);
            return MutationResult.MUTATED;
        }
        
    }

}
```

- update: called when the property of the given node was changed or added. The given property is the modified or added one. The given SimpleUI-node still has the old property or none. The real property of the node will be updated after this update-method.
- remove: called when the property is removed. The SimpleUI-node still has the node at that point. It will be removed after this remove-method.

If the JavaFx-node can not be modified, "MutationResult.REQUIRES_REBUILD" can be returned instead. This will trigger a complete rebuild of the node.

**Combining the Builder and Updater**

The builder and updater can be combined as a single class "PropFxNodeUpdatingBuilder":

```java
class MyProperty extends SuiProperty {

    ...

    public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<MyProperty, Labeled> {

        @Override
        public void build(SuiNode node, MyProperty property, Labeled fxNode) {
            fxNode.setText(property.text);
        }

        @Override
        public MutationResult update(MyProperty property, SuiNode node, Labeled fxNode) {
            fxNode.setText(property.text);
            return MutationResult.MUTATED; 
        }

        @Override
        public MutationResult remove(MyProperty property, SuiNode node, Labeled fxNode) {
            fxNode.setText(null);
            return MutationResult.MUTATED;
        }
        
    }

}
```

**Property-Ids**

To use a property-id with our new property, we simple make it required in the constructor and pass it to the superclass.

```java
class MyProperty extends SuiProperty {

    ...

    public MyProperty(String propertyId, String text) {
        super(MyProperty.class, propertyId); // if a property-id is used, the comparison function is not required anymore
        this.text = text;
    }

    ...
    
}
```

**Using the Property with Builders from 6.3.4**

To use our new property with the builder pattern from 6.3.4, we need to create a "FactoryExtension" for the property.

```java
public interface MyPropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {

	default T myProperty(String text) {
		getBuilderProperties().add(new MyProperty(wrapText));
		return (T) this;
	}
    
}
```

The property can then be exposed via the builder:

```java
public static class MyElementBuilder extends BuilderExtensionContainer implements
        MyPropertyBuilderExtension<MyElementBuilder>, // expose the new propert
		...
            
myElement()
        myProperty("id", "text")
        ...
```



### 8. Events

#### 8.1 Direct Listeners

Listeners can be added directly to elements. This way, only the one listener will immediately receive all events created by that one element.

```java
SuiElements.button()
	.eventAction("my.listener", e -> System.out.println("Click!"));
```



#### 8.2. Emitting Listener

The emitting-listener does not handle events directly, but forwards them to the global "SimpleUI-Event-Bus", where additional handlers can be registered to handle the forwarded events. To better manage the events at the bus, tags can be added to each event. Listeners at the event-but can then filter the incoming events with these tags. In addition to the manually specified tags, the event type will be added to the tags (e.g. "type.ActionEventData").

```java
// forwards all action-events to the event-bus together with the tags "my.button" and "type.ActionEventData"

SuiElements.button()
	.eventAction("my.listener", new SuiEmittingEventListener(Tags.from("my.button")));

SuiElements.button().
    .emitEventAction("my.listener", Tags.from("my.button"));
```

Listeners can then subscribe to the "SimpleUI-Event-Bus" via the registry.

```java
// get the event bus from the registry
EventBus eventBus = SuiRegistry.get().getEventBus();

// listen to events of the type "ActionEventData" that contain the tag "my.button".
eventBus.subscribe(
    SubscriptionData.ofType(ActionEventData.class, Tags.containsAll("my.button")),
    event -> System.out.println("Click!")
);
```

#### 8.3 Emitting Streams

The same functionality as emitting listeners can be achieved with SimpleUI-Streams. Here, the "emitAsSuiEvent"-step will forward the event to the bus.

```java
SuiElements.button()
	.eventAction("my.listener", SuiStream.eventStream(ActionEventData.class,
                                                      stream -> stream.emitAsSuiEvent(Tags.from("my.button"))));
```



### 9. Multiple Windows

SimpleUI can open and close multiple different windows. All windows usually use the same state. Windows have to be defined as child windows of any controller (Root window is an exception). The window of this parent controller will automatically be the parent/owner of the new child window. 

```java
MyState myState = new MyState();
        
SuiSceneController controller = new SuiSceneController(
	myState,
	WindowRootElement.windowRoot(stage)
		.title("Windows Demo")
    	.size(500, 400)
		.content(MyState.class, state -> ...) // 1
    	.modal(WindowRootElement.windowRoot()
               .title("Child Window 1")
               .size(200, 200)
               .condition(MyState.class, state -> state.isShowWindow1() == true) // 2
               .onClose(TestUIState.class, state -> state.setShowWindow1(false)) // 3
               .content(TestUIState -> state ...) // 4
               .modal(WindowRootElement.windowRoot() // 5
                     ...
                )
        .modal(WindowRootElement.windowRoot()
               .title("Child Window 2")
               ...
        )
);
        
controller.show();
    
...
    
myState.update(MyState.class, state -> state.setShowWindow1(true)) // -> opens window 1 
```

- **1)** Define the content of the parent/root window.
- **2)** The condition for when the window should be shown. If this condition returns true, the window will be open and if it returns false, it will be closed.
- **3)** an action that is triggered when the window is closed by a state-update or by the user. If the user closes the window  (via the close button at the top of the window), the state will not be updated automatically. It is recommended to manually "reset" the  state to keep the state in sync to what is displayed. This can usually be done without "state.update(...)" since the change (closing the window) already happened.
- **4)** the actual content of the child window
- **5)** child windows can also have their own child windows, and so on.



### 10. Performance Optimizations

#### 10.1 The Id-Property

Also see "5.1 The Mutation Algorithm".

When looking for differences between two lists of children, the mutation algorithm iterates over both lists and modifies the children accordingly. This can cause problems when, for example, a new node is added at the beginning of the list or the list gets shuffled. The algorithm can then cause unnecessary modifications or event rebuilds of complete subtrees. To avoid this, we can give all child nodes an unique id that SimpleUI can then use to efficiently match up the correct children.

```java
...
.items(
    SuiElements.button()
        .id("btn_1")
        .textContent("Button 1"),
    SuiElements.button()
        .id("btn_2"),
        .textContent("Button 2"),
    SuiElements.button()
        .id("btn_3"),
        .textContent("Button 3")
)
...
```

 

#### 10.2 The Mutation Behavior Property

If we already know that some parts of the tree can never change, we can give SimpleUI a small hint with the MutationBehaviourProperty.

```java
// Example: Never mutate properties of this button.
SuiElements.button()
    .behaviourStatic()
    .textContent("Button 1")
```

- **Default**

  The default behavior. Always consider the node and its child nodes during mutation.

  - add nothing
  - set to MutationBehaviour.DEFAULT
  
- **Static Node**

  The properties of this node will not be considered during mutation but its child nodes can be freely modified.

  Example:

  ```java
  // The properties of the vbox will not be changed and no properties can be added or removed.
  // However child nodes can still be removed, added or modified.
  SuiElements.vBox()
      .mutationBehaviour(MutationBehaviour.STATIC_NODE)
      .spacing(5)
      .item(SuiElements.button().textContent("My Button"))
  ```
  
  - add Properties.behaviourStaticNode()
  - add Properties.mutationBehaviour(MutationBehaviour.STATIC_NODE)
  
- **Static Subtree**

  The properties of this node can be changed but its child nodes are not considered during mutation

  Example:

  ```java
  // The properties of the vbox can be freely modified, added and removed.
  // The childs nodes are not considered during mutation. No child node can be added or removed and no properties of them can be changed.
  SuiElements.vBox()
      .mutationBehaviour(MutationBehaviour.STATIC_SUBTREE)
      .spacing(5)
      .item(SuiElements.button().textContent("My Button"))
  ```
  
  - add Properties.behaviourStaticSubtree()
  - add Properties.mutationBehaviour(MutationBehaviour.STATIC_SUBTREE)
  
- **Static**

  Neither the properties of the node can be changed nor its child nodes

  ```java
  // The properties and children of the vbox can not be changed, added or removed.
  // Properties of child nodes can not be modified either.
  SuiElements.vBox()
      .mutationBehaviour(MutationBehaviour.STATIC)
      .spacing(5)
      .item(SuiElements.button().textContent("My Button"))
  ```
  
  - add Properties.behaviourStatic()
  - add Properties.mutationBehaviour(MutationBehaviour.STATIC)



##### Update Tag Filters

To further optimize the behavior of our nodes, we can add a filter to the mutation behavior property.

When updating the ui state, tags can be specified. These tags will be passed along during the mutation process and can be accessed before mutating each node. We can then tell nodes to only mutate when a certain condition is met depending on the tags.

**Adding Tags to the State Update**

```java
// update the state and add the three given tags to this update.
uiState.update(MyState.class, (TaggedSuiStateUpdate<MyState>) state -> {
    state.counter++;
    return Tags.from("tag0", "my.tag", "important");
});
```

**Reacting to the Tags**

```java
.mutationBehaviour(MutationBehaviour.STATIC,
        Tags.or(
                Tags.contains("important"),
                Tags.and(
                        Tags.contains("tag0"),
                        Tags.containsAny("tag.a", "tag.b")
                )
        )
        // -> important | (tag0 & (tag.a | tag.b))
);
```

The node with this behavior is considered "static" as long as the given condition does not match the tags created by the state update triggering the mutation process.

This system allows for complex expressions. The following operations are available:

- **Tags.contains(tag)**: the list of tags must contain the given tag to return true.
- **Tags.containsAll(tags)**: the list of tags must contain all of the given tags to return true.
- **Tags.containsAny(tags)**: the list of tags must contain at least one of the given tags to return true.
- **Tags.or(expr)**: At least one of the given expressions must evaluate to true for this expression to return true.
- **Tags.and(expr)**: All of the given expressions must evaluate to true for this expression to return true.
- **Tags.not(expr)**: Only when the given expression evaluate to false does this expression return true.
- **Tags.constant(value)**: the expression always returns the given value, independent of the list of tags.



##### Behavior Table:

"(mutate parent) / (mutate children)"

- ❌/✔️ = do not mutate the parent; do mutate its child nodes

- ✔️/❌ = do mutate the parent; do not mutate its child nodes

|                | NO FILTER | FILTER DOES NOT MATCH | FILTER MATCHES |
| -------------- | --------- | --------------------- | -------------- |
| DEFAULT        | ✔️/✔️       | ✔️/✔️                   | ✔️/✔️            |
| STATIC NODE    | ❌/✔️       | ❌/✔️                   | ✔️/✔️            |
| STATIC SUBTREE | ✔️/❌       | ✔️/❌                   | ✔️/✔️            |
| STATIC         | ❌/❌       | ❌/❌                   | ✔️/✔️            |



#### 10.3 Property-Ids

See "7.1 The Property-Id"



### 11. Injecting Nodes

When building the scene tree, so called injection points can be defined where nodes can be added from the outside.



#### 11.1 Creating the Injection Points

```java
SuiElements.vBox()
		.itemsInjectable("my.injectionpoint");
```

This creates a vbox with the ability to add any amount of child nodes from the outside. We can also inject additional items into a node that already has child-nodes.  To customize the position of the injected items in the list, we can use an "InjectionIndexMarker".

```java
SuiElements.vBox()
		.itemsInjectable(
				"my.injectionpoint",
				InjectionIndexMarker.injectAt(1), // inject all nodes at index 1, i.e. between "Button 1" and "Button 2"
				SuiElements.button()
						.id("btn.1")
						.textContent("Button 1"),
				SuiElements.button()
						.id("btn.2")
						.textContent("Button 2")
		);
```



####   11.2 Injecting Nodes

Items can then be injected via the "SuiRegistry" and the id of the injection point. Items to inject must be registered before the scene tree is created/mutated, or else the nodes will not show up. Nodes added after creating the scene tree will only show up after the mutation process. It is possible to add all nodes and then trigger a state update that does not modify the state, but runs the mutation process, adding the injected children. 

```java
SuiRegistry.get().inject(
		"my.injectionpoint",
		SuiElements.button()
				.id("btn.injected")
				.textContent("Button Injected"),
		SuiElements.label()
				.id("label.injected")
				.textContent("Label Injected")
);
```





### 12. Styling

#### 12.1 Application Style

Apply a given style to the whole application (i.e. all windows). This functions as a base for window and element styles.

```java
style = SuiApplicationStyle.modena(); // use the default modena style
style = SuiApplicationStyle.caspian(); // use the default caspian style
style = SuiApplicationStyle.caspian(Resource.externalRelative("style.css")); // use the caspian style together with custom css
style = SuiApplicationStyle.cssStylesheet(Resource.externalRelative("style.css")); // use the given css file(s)

SuiRegistry.get().getStyleManager().setApplicationBaseStyle(style); // set the given style as the application style
```



#### 12.2 Window Style

Apply a given style to a specific window. This functions as a base for element styles.

```java
style = SuiWindowBaseStyle.cssStylesheet(Resource.externalRelative("style.css")); // use the given css file(s)

...
windowRoot()
    ...
    .windowBaseStyle(style)
    ...
```



#### 12.3 Element Style

Add styles to individual elements (or subtrees).

```java
// style the button with css-strings
button()
    .textContent("Hello!")
    .style("-fx-background-color: red", "-fx-border-color: green");

// add a css stylesheet to the button
button()
    .textContent("Hello!")
    .style(Resource.externalRelative("style.css"));

// other properties can also be used for styling, e.g. margin, padding, background, ...
```



### 13. SimpleUI Streams

The stream-api provided by SimpleUI is very similar to the one build into java. The main difference lies in the source of the elements. Java Streams usually have a collection or array as a source. Operations can then iterate over this source and process the elements this way. The whole process is done once the end of the collection/array/... has been reached. SimpleUI-Streams have a source producing elements as "events" and pushing them to the stream (asynchronously). These source do not have a start, end  or fixed size and can not be iterated over the stream can not know when the current event/element is going to be the last event ever produced. Examples for event sources are JavaFx-Observables or event listeners.  

Example:

```java
SimpleStringProperty observable = new SimpleStringProperty();

SuiStream.from(observable)
    .map(e -> "value=" + e)
    .forEach(e -> System.out.println(e));

observable.set("abc");
// -> prints "value=abc" 

observable.set("123");
// -> prints "value=123"
```



#### 13.1 Stream sources

- **Observable JavaFx Values**

  The JavaFx-ObservableValue produces a new element each time the value is changed. The new value is then pushed to the stream(s).

  ```java
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable).forEach(e -> System.out.println(e));
  ```

- **Event Listeners and SimpleUI-Event-Properties**

  The streams can also be used as event listeners of properties. The events received by the listener are pushed to the stream.

  ```java
  SuiEventListener<TextContentEventData> eventListener = SuiStream.eventStream(TextContentEventData.class,
      stream -> stream
          .map(e -> e.getText())
          .forEach(e -> System.out.println(e)));
  
  SuiElements.textField()
          .eventTextEntered("my.listener", eventListener);
  
  // or as a single block:
  SuiElements.textField()
          .eventTextEntered(
                  "my.listener",
                  SuiStream.eventStream(TextContentEventData.class,
                          stream -> stream
                                  .map(e -> e.getText())
                                  .forEach(e -> System.out.println(e))));
  ```
  
- **Normal Java Collections**

  The behavior is a bit different with collections than described above. Here we process all elements in the source collection at once and in order synchronously. This can be very useful for testing streams however.

  ```java
  List<Integer> sourceCollection = List.of(1, 2, 4, 8, 16, 32);
  SuiStream.from(sourceCollection, stream -> stream.forEach(e -> System.out.println(e)));
  ```





#### 13.2 Stream operations

- **for each**

  applies the given function to every element as the last step in the stream.

  ```java
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable)
      .forEach(value -> System.out.println(value));
  
  observable.setValue("abc"); // prints: "text=abc"
  observable.setValue(null);  // prints: "text=null"
  ```

- **peek**

  applies the given function to every element without modifying it and pushed it to the next step in the stream.

  ```java
SimpleStringProperty observable = new SimpleStringProperty();
SuiStream.from(observable)
      .peek(value -> System.out.println("peek " + value));
      .forEach(value -> System.out.println(value));
  
  observable.setValue("abc"); // prints: "peek abc", "abc"
  observable.setValue(null);  // prints: "peek null", "null"
  ```

- **map**

  Applies the given mapping function to the element in the stream and pushes the result to the next step in the stream. 

  ```java
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable)
      .map(value -> "text="+value)
      .forEach(value -> System.out.println(value));
  
  observable.setValue("abc"); // prints: "text=abc"
  observable.setValue(null);  // prints: "text=null"
  ```

- **map and ignore nulls**

  same as "map" but only applies the function to non-null-elements and just pushes the null-elements to the next step.

  ```java
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable)
      .mapIgnoreNulls(value -> "text="+value)
      .forEach(value -> System.out.println(value));
  
  observable.setValue("abc"); // prints: "text=abc"
  observable.setValue(null);  // prints: "null"
  ```

- **map nulls**

  same as "map" but only applies the function to null-elements and just pushes the non-null-elements to the next step.

  ```java
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable)
      .mapNulls(value -> "no text")
      .forEach(value -> System.out.println(value));
  
  observable.setValue("abc"); // prints: "abc"
  observable.setValue(null);  // prints "no text"
  ```

- **map to string**

  maps every element to a string using the "Object.toString()" function and "null" for null-elements.

  ```java
  SimpleObjectProperty<Point> observable = new SimpleObjectProperty<>();
  SuiStream.from(observable)
      .mapToString()
      .forEach(value -> System.out.println(value));
  
  observable.setValue(new Point(1,2)); // prints: "java.awt.Point[x=1,y=2]"
  observable.setValue(null);           // prints "null"
  ```

- **flat-map**

  applies a mapping function that returns a list to every element and pushes every element in the resulting list to the next step in the stream.

  ```java
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable)
      .flatMap(value -> value == null ? "no value" : List.of(value.split(",")))
      .forEach(value -> System.out.println(value));
  
  observable.setValue("a,b,c"); // prints: "a", "b", "c"
  observable.setValue(null);    // prints: "no value"
  ```

- **flat-map ignore nulls**

  same as flat-map  but only applies the function to non-null-elements and just pushes the null-elements to the next step.

  ```java
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable)
      .flatMapIgnoreNulls(value -> List.of(value.split(",")))
      .forEach(value -> System.out.println(value));
  
  observable.setValue("a,b,c"); // prints: "a", "b", "c"
  observable.setValue(null);    // prints "null"
  ```

- **flat-map nulls**

  same as "flat-map" but only applies the function to null-elements and just pushes the non-null-elements to the next step.

  ```java
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable)
      .flatMapNulls(value -> List.of("null-1", "null-2"))
      .forEach(value -> System.out.println(value));
  
  observable.setValue("abc"); // prints: "abc"
  observable.setValue(null);  // prints "null-1", "null-2"
  ```

- **filter**

  Only pushes the elements matching the given condition to the next step of the stream.

  ```java
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable)
      .filter(value -> value.equals("xyz")
      .forEach(value -> System.out.println(value));
  
  observable.setValue("abc"); // prints nothing
  observable.setValue("xyz"); // prints: "xyz"
  observable.setValue(null);  // prints nothing
  ```

- **filter nulls**

  Filters out any null elements and pushes non-null-elements to the next step of the stream.

  ```java
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable)
      .filterNulls()
      .forEach(value -> System.out.println(value));
  
  observable.setValue("abc"); // prints "abc"
  observable.setValue(null);  // prints nothing
  ```

- **skip**

  Does not push any elements to the next step as long as the provided boolean is "true".

  ```java
  AtomicBoolean skipFlag = new AtomicBoolean(false);
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable)
      .skip(() -> skipFlag.get()))
      .forEach(value -> System.out.println(value));
  
  observable.setValue("a"); // prints "a"
  skipFlag.set(true);
  observable.setValue("b"); // prints nothing
  skipFlag.set(false);
  observable.setValue("c"); // prints "b"
  ```

- **collect**

  Collects the elements into a list or observable value.

  ```java
  // collect elements to list
  Collection<String> list = new ArrayList<>();
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable)
      .collectInto(list);
  
  observable.setValue("abc");
  observable.setValue(null);
  // list now has values "abc" and null
  ```

  ```java
  SimpleStringProperty target = new SimpleStringProperty();
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable)
      .collectInto(target);
  
  observable.setValue("abc"); // target now has value "abc"
  observable.setValue(null);  // target now has value null
  ```

- **distinct**

  Only pushes an element to the next step of the stream if is it not the same as the previous element (based on Object.equals).

  ```java
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable)
      .distinct(target)
      .forEach(value -> System.out.println(value));
  
  observable.setValue("abc"); // prints "abc"
  observable.setValue("abc"); // prints nothing
  observable.setValue("abc"); // prints nothing
  observable.setValue("xyz"); // prints "xyz"
  observable.setValue(null);  // prints "null"
  ```

- **wait for**

  Waits for a specific element matching the given condition and then pushes all elements that have arrived since the last matching element to the next step. 

  ```java
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable)
      .waitFor(false, value -> value.equals(".")) // false -> do not include the matching element
      .forEach(value -> System.out.println(value));
  
  observable.setValue("abc"); // prints nothing
  observable.setValue("xyz"); // prints nothing
  observable.setValue(".");   // prints: "abc", "xyz" 
  ```

  ```java
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable)
      .waitFor(true, value -> value.equals(".")) // true -> include the matching element
      .forEach(value -> System.out.println(value));
  
  observable.setValue("abc"); // prints nothing
  observable.setValue("xyz"); // prints nothing
  observable.setValue(".");   // prints: "abc", "xyz", "."
  ```

- **wait for and pack**

  Waits for a specific element matching the given condition and then pushes all elements that have arrived since the last matching element to the next step as a single list. 

  ```java
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable)
      .waitForAndPack(true, value -> value.equals("."))
      .forEach(value -> System.out.println(value));
  
  observable.setValue("abc"); // prints nothing
  observable.setValue("xyz"); // prints nothing
  observable.setValue(".");   // prints: "[abc, xyz]"
  ```

- **group**

  Pushes the last n elements to the next step as a single list.

  ```java
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable)
      .lastN(2)
      .forEach(value -> System.out.println(value));
  
  observable.setValue("a"); // prints "[a]"
  observable.setValue("b"); // prints "[a, b]"
  observable.setValue("c"); // prints "[b, c]"
  ```

- **accumulate**

  When an element arrives at this state, hold back the element an all future elements until the given time runs out or the maximum number of elements have arrived. Then push all collected elements to the next step as a single list.

  ```java
  SimpleObjectPropert<List<String>> observable = new SimpleObjectPropert<>();
  SuiStream.from(observable)
      .accumulate(2, Duration.millis(100))
      .forEach(value -> System.out.println(value));
  
  observable.setValue("a"); // prints nothing
  observable.setValue("b"); // prints "[a, b]"
  
  observable.setValue("a"); // prints nothing
  // wait longer than 100ms -> prints "[a]" after 100ms
  observable.setValue("b"); // prints "[b]"
  ```

- **unpack**

  Pushes every element of the given type in an input list to the next step.

  ```java
  SimpleObjectPropert<List<String>> observable = new SimpleObjectPropert<>();
  SuiStream.from(observable)
      .unpack(String.class)
      .forEach(value -> System.out.println(value));
  
  observable.setValue(List.of("a", "b", "c")); // prints "a", "b", "c"
  observable.setValue(null);                   // prints null
  ```

- **switch to javafx thread**

  Some operations can only be executed on the main JavaFx-Application-Thread. This operation switches the stream over to this thread to process all future operations of this stream on this thread.

  ```java
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable)
      .peek(value -> System.out.println(value + ": " + Platform.isFxApplicationThread()))
      .onJavaFxThread()
      .forEach(value -> System.out.println(value + ": " + Platform.isFxApplicationThread()));
  
  observable.setValue("abc"); // prints: "abc: false", "abc: true"
  ```

- **async**

  Processes every future operation of each element on a "new" thread supplied by a thread pool.

  ```java
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable)
      .async()
      .forEach(value -> System.out.println(value));
  ```

- **suppress exceptions**

  Hides/Suppresses all exceptions that are thrown by future operations of this stream.

  ```java
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable)
      .suppressErrors()
      .forEach(value -> System.out.println(value.toString())); // to can cause an NPE
  
  observable.setValue("abc"); // prints "abc"
  observable.setValue(null); // prints nothing
  ```

- **handle exceptions**

   Intercepts and handles all exceptions that are thrown by future operations of this stream.

  ```java
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable)
      .handleErrors(e -> System.out.println("Error!"))
      .forEach(value -> System.out.println(value.toString())); // to can cause can NPE
  
  observable.setValue("abc"); // prints "abc"
  observable.setValue(null); // prints "Error!"
  ```

- **update state**

  Triggers a state update for each incoming element in the stream.

  ```java
  SimpleStringProperty observable = new SimpleStringProperty();
  SuiStream.from(observable)
      .updateState(MyState.class, myState, (state, value) -> state.text = value);
  
  observable.setValue("abc"); // triggers a state update. The "text"-field of the state will be set to the value of the element.
  ```
  
- **emit as a simpleui-event**

   Forwards each incoming element to the global SimpleUI-Event-Bus together with the given tags.

   ```java
   SimpleStringProperty observable = new SimpleStringProperty();
   SuiStream.from(observable)
       .map(value -> "Event: " + value)
       .emitAsSuiEvent(Tags.from("tag1", "tag2"));
   
   observable.setValue("abc"); // forwards the string "Event: abc" as an event with the tags "tag1", "tag2" to the event bus
   ```

