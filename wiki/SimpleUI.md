## SimpleUI

SimpleUI is an UI framework/library build on top of JavaFx. Its split into two general parts: The UI state and the scene tree. SimpleUI constructs the scene tree from the state which can then be shown to the user. The same state produces always the same scene. To create interactive interfaces, the state can be updated which then triggers SimpleUI to modify or rebuild the existing scene tree to match the new state.



TODO

- creating a scene tree from the ui-state ✔️

- structuring the code: components vs functions✔️

- updating the state and scene ✔️

  - how to update✔️
  - Mutation process in depth❌

- performance optimizations ❌

- properties and elements ❌

  - elements

    - e.g.:

      ```
      MyElement
      - accepts all properties from the node-group
      - accepts all properties from the region-group
      - accepts all properties from the event-group
      - accepts some special properties
      	* XyzProp:
      		this properties defines ...
      ```

  - properties

    - dont explain every property, only the general categories/groups and important props (e.g. itemListprop, mutationProp, idProp, ...) + where to find the other properties

- custom properties and elements❌ 

- modifying existing elements❌ 

- streams❌

- sui profiler❌

- using simpleui with simpleapplication view system❌





### 1. Quickstart

Short walkthrough on how to create a small gui just with SimpleUI and JavaFX. How to use SimpleUI together with the SimpleApplication framework, see section "TODO!!TODO!!TODO!!TODO!!TODO!!TODO!!❌"



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

The scene controller is responsible for managing the scene, i.e. the scene tree, ui state and so on. We can then get the root javafx-node from that controller.

```java
public class SimpleUIDemo extends Application {

    //...
    
    @Override
    public void start(Stage stage) {
        SuiRegistry.initialize();
        
        MyState myState = new MyState();
        
        SuiSceneController controller = new SuiSceneController(myState, MyState.class, state -> { /*todo*/ });
        Parent rootNode = (Parent) controller.getRootFxNode();
        
        stage.setScene(new Scene(rootNode, 200, 100));
        stage.show();
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
        
        SuiSceneController controller = new SuiSceneController(myState, MyState.class, state -> buildUI(state));
        Parent rootNode = (Parent) controller.getRootFxNode();
        
        stage.setScene(new Scene(rootNode, 200, 100));
        stage.show();
    }
    
    private NodeFactory buildUI(MyState currentState) {
        return SuiButton.button(
        	Properties.textContent("Button: " + currentState.text),
            EventProperties.eventAction(event -> System.out.println("Click!"));
        );
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
        return SuiButton.button(
        	Properties.textContent("Button: " + currentState.text),
            EventProperties.eventAction(event -> {
                // we can not set the value of the state directly. This will not update the interface
                currentState.update(state -> state.value++);
            });
        );
    }
    
}

class MyState extends SuiState {
    public int value = 0;
}
```

In case the update process can rebuild the root node, we have to tell the javafx scene when the root changes.

```java
public class SimpleUIDemo extends Application {

    //...

    @Override
    public void start(Stage stage) {
        SuiRegistry.initialize();
        
        MyState myState = new MyState();
        
        SuiSceneController controller = new SuiSceneController(myState, MyState.class, state -> buildUI(state));
        Parent rootNode = (Parent) controller.getRootFxNode();
        
        final Scene scene = new Scene(rootNode, 200, 100);
        controller.addListener(newRootNode -> {
            Node fxRoot = newRootNode.getFxNodeStore().get();
            scene.setRoot((Parent) fxRoot);
        });
        
        stage.setScene(scene);
        stage.show();
    }
    
	//...
    
}

//...
```



### 2. Structuring more complex User Interfaces

There are two options of breaking up the code into smaller parts. By using SimpleUI Components or normal methods. Both options are equally valid and produce the same result.

#### 2.1 Before: No Components or Methods

```java
private NodeFactory buildUI(MyState currentState) {
    return SuiVBox.vbox(
        Properties.spacing(5),
        Properties.alignment(Pos.CENTER),
        Properties.items(
            SuiButton.button(
                Properties.textContent("Button 1 (" + state.value + ")"),
                EventProperties.eventAction(e -> currentState.update(MyState.class, s -> s.value++))
            ),
            SuiButton.button(
                Properties.textContent("Button 2 (" + state.value + ")"),
                EventProperties.eventAction(e -> currentState.update(MyState.class, s -> s.value++))
            ),
            SuiButton.button(
                Properties.textContent("Button 3 (" + state.value + ")"),
                EventProperties.eventAction(e -> currentState.update(MyState.class, s -> s.value++))
            )
        )
    );
}
```



#### 2.2. SimpleUI-Components

```java
private NodeFactory buildUI(MyState currentState) {
    return SuiVBox.vbox(
        Properties.spacing(5),
        Properties.alignment(Pos.CENTER),
        Properties.items(
            new MyComponent("Button 1"),
            new MyComponent("Button 2"),
            new MyComponent("Button 3")
        )
    );
}

...

class MyComponent extends SuiComponent<MyState> {
    
    public MyComponent(String title) {
        super(state -> SuiButton.button(
            Properties.textContent(text + " (" + state.value + ")"),
            EventProperties.eventAction(e -> state.update(MyState.class, s -> s.value++))
        ));
    }
    
}
```



#### 2.3. Normal Methods

```java
private NodeFactory buildUI(MyState currentState) {
    return SuiVBox.vbox(
        Properties.spacing(5),
        Properties.alignment(Pos.CENTER),
        Properties.items(
            myComponent(state, "Button 1"),
            myComponent(state, "Button 2"),
            myComponent(state, "Button 3")
        )
    );
}

...

private NodeFactory myComponent(MyState state, String text) {
    return SuiButton.button(
            Properties.textContent(text + " (" + state.value + ")"),
            EventProperties.eventAction(e -> state.update(MyState.class, s -> s.value++))
    );
}
```



### 3. Updating the UI-State

For a more advanced look at how updating the state modifies the interface, see "4. The Mutation Process"

#### 3.1 Normal Update

The normal update modifies the state in a safe way on the main javafx thread. This is the only (recommended) way to update the state when we also want to modify the interface.

```java
myState.update(MyState.class, state -> {
    // state.setXY
});
```

 

#### 3.2 Silent Update

A silent update with modify the state but not trigger any listeners or update the interface. Updating the state in this way basically decouples the current state from the current interface and is not thus not recommended, but MAY be useful in some rare cases.

```java
myState.update(MyState.class, true, state -> { // true -> do silent update
    // state.setXY
});
```



#### 3.3 Unsafe Update

An unsafe update will modify the state and call the listeners on the current thread. This causes problems when the interface gets modified and the current thread is not the main javafx-thread.

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



### 4. The Mutation Process

### 4.1 Overview

when updating the ui state, the controller triggers the mutation process. 

- state update triggers mutation
- a new scene tree is build from the new state
- the current tree is recursively compared with the new tree
- if there is a difference between the old and new tree, the old tree will be modified so that it matches the new tree
- if it is not possible to make the necessary modifications, the node or subtree rebuild / copied over from the new tree to replace the old node/subtree



### 4.2 Mutation of single Nodes

- how properties are compared
- how properties are modified
  - compare all props, find added, removed, updated
  - some nodes are excluded: itemProps, ..

- the use of the property-id of some props



### 4.3 Mutation of Nodes with Children

- how child nodes are compared
- how child nodes are mutated
- the use of id-property



### 4.4 Customize Mutation Behaviour

- Mutation behaviour property

- in depth perf. optimisation in other section