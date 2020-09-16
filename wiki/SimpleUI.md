## SimpleUI

SimpleUI is an UI framework/library build on top of JavaFx. Its split into two general parts: The UI state and the scene tree. SimpleUI constructs the scene tree from the state and node factory which can then be shown to the user. The same state produces always the same scene. To create interactive interfaces, the state can be updated which then triggers SimpleUI to modify (or rebuild) the existing scene tree to match the new state.



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

When updating the ui state, the controller triggers the mutation process. The controller takes the current state, applies the update and then builds a completely new scene tree from that updated state. This new tree gets than compared to the current scene tree. The mutation process then tries to modify the properties and child-nodes in such a way that the tree matches the new target tree. If a node can not be modified to match the target node, it gets rebuild completely, i.e. gets replaced by the node from the new tree.

##### Example Walkthrough:

![mutationExample](D:\LukasRuegner\Programmieren\Java\Workspace\SimpleApplication\wiki\mutationExample.png)

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



### 4.2 Mutation of a Node

A few things can be changed about a node:

- add a new property
- remove a property
- modify an existing property
  - how props are compared
  - also note the prop-id
- completely change out the node (new node in same place does not match the prev node at all)



### 4.3 Mutation of Child-Nodes

- how child nodes are compared
- how child nodes are mutated
- the use of id-property



### 4.4 Customize Mutation Behaviour

- Mutation behaviour property

- in depth perf. optimisation in other section