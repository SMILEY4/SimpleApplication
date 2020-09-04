## 1. Properties



### 1.1 Common Properties

These are properties that can be applied to ALL elements.



#### MutationBehaviourProperty

Does not modify javafx-node. Is only a marker property defining the behaviour of a node during mutation.

- **Default**

  Node and its children will be mutated

- **Static Node**

  This node (i.e. the properties of this node) will not be affected by mutations. Its children will still be mutated.

- **Static Subtree**

  This node and its children nodes will not be affected by mutations.



#### DisabledProperty

Whether the javafx-node is enabled or disabled. Nodes are always "enabled" by default.



#### StyleProperty

Specify the javafx css-style as either a css-string or as a css-file.

Default style is no stylesheets or an empty css-string.





### 1.2 Common Event Properties

Events / Event Properties that can be applied to ALL elements.



#### OnKeyPressedEventProperty

Fired when any key is pressed and the node (or a child node) is focused.

-  KeyEventData



#### OnKeyReleasedEventProperty

Fired when any key is released and the node (or a child node) is focused.

-  KeyEventData



#### OnKeyTypedEventProperty

Fired when any key is typed and the node (or a child node) is focused.

-  KeyEventData

  

#### OnMouseClickedEventProperty

Fired when any mouse button was clicked and the node (or a child node) is focused.

-  MouseButtonEventData



#### OnMousePressedEventProperty

Fired when any mouse button was pressed and the node (or a child node) is focused.

-  MouseButtonEventData



#### OnMouseReleasedEventProperty

Fired when any mouse button was released and the node (or a child node) is focused.

-  MouseButtonEventData



####  Event Data



##### 1. KeyEventData

**Data**:

- the key code
- the character
- whether the "alt"-key was down
- whether the "ctrl"-key was down
- whether the "meta"-key was down
- whether the "shift"-key was down
- whether the system shortcut-key was down
- the original javafx-event

**Used by:**

- OnKeyPressedEventProperty
- OnKeyReleasedEventProperty
- OnKeyTypedEventProperty



##### 2. MouseButtonEventData

**Data**:

- x position of the event
- y position of the event
- the mouse button
- whether the "alt"-key was down
- whether the "ctrl"-key was down
- whether the "meta"-key was down
- whether the "shift"-key was down
- whether the system shortcut-key was down
- the original javafx-event

**Used by:**

- OnMouseClickedEventProperty
- OnMousePressedEventProperty
- OnMouseReleasedEventProperty

