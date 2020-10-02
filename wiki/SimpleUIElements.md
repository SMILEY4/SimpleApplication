

### SuiAccordion

An element containing any number of expandable sections. Only one of these sections can be expanded at any time.

**Properties:**

- all common properties

- all common region properties

- all common event properties

- ItemListProperty, ItemProperty

  Each item will be added as a new section. The title of the section is specified by the "TitleProperty" of the child item.

- OnAccordionExpandedEventProperty

  A listener that is called when the user expands one of the sections.





### SuiAnchorPane

An container that allows child nodes to anchor to its edges.

The anchors are defined by the"AnchorProperty" of the child nodes. E.g. an anchor for the top-side of "10.0" keeps the distance of the top side of the child node at a distance of 10 pixels to the top side of the anchor pane. If the anchor for a side is set to null, the side is not restricted and can be any distance.  

**Properties:**

- all common properties
- all common region properties
- all common event properties

- ItemListProperty, ItemProperty

  Specifies the items of the anchor pane. The items are laid out using the "AnchorProperty" of the child nodes.



### SuiButton

An element that can trigger an action when pressed

**Properties:**

- all common properties

- all common region properties

- all common event properties

- TextContentProperty

  Specifies the text shown inside the button

- WrapTextProperty

  Whether the text inside the button should wrap into the next line if it is too long.

- OnActionEventProperty

  A listener that is called when the button is pressed



### SuiCheckbox

An element that can be in one of the two states "selected" or "not selected".

**Properties:**

- all common properties
- all common region properties
- all common event properties

- TextContentProperty

  Specifies the text of the label shown next to the box

- WrapTextProperty

  Whether the label should wrap into the next line if it is too long.

- OnActionEventProperty

  A listener that is called when the box is pressed (either selected or deselected)

- OnCheckedEventProperty

  A listener that is called when the box was selected.

- OnUncheckedEventProperty

  A listener that is called when the box was deselected.



### SuiChoiceBox

An element where the user can select an item from a predefined list of items.

**Properties:**

- all common properties

- all common region properties

- all common event properties

- ContentItemsProperty

  Defines the list of items available to select from. 

- SelectedItemProperty

  Defines the selected item of the choicebox

- ChoicesConverterProperty

  Specifies how to convert the item from a java object to the string to display (and vice versa).

- OnValueChangedEventProperty

  A listener that is called when the user selects a new item or a new item is selected automatically.



### SuiComboBox

An element where the user can select an item from a predefined list of items. In addition, the user can type in the value to select by hand or search for an item.

 **Properties:**

- all common properties

- all common region properties

- all common event properties

- ContentItemsProperty

  Defines the list of items available to select from. 

- ChoicesConverterProperty

  Specifies how to convert the item from a java object to the string to display (and vice versa).

- OnValueChangedEventProperty

  A listener that is called when the user selects a new item or a new item is selected automatically.

- EditableProperty

  Whether the user can type in values manually. This property can not be used in combination with "SearchableProperty"

- SearchableProperty

  Whether the user can filter the list of available items. This property can not be used in combination with "EditableProperty"

- PromptTextProperty

  The text to show when no item is selected



### SuiContainer

A generic container that can hold any number of child elements. A custom layout function for the children can be provided.

**Properties:**

- all common properties

- all common region properties

- all common event properties

- ItemListProperty, ItemProperty

  Defines the child elements of the container.

- LayoutProperty

  Specifies the layout function and can be used to set the position and size of the child elements.

  To lay out the nodes, it is recommended to use the following methods of the child nodes:

  - relocate(x, y) to set the position relative to the container position
  - resize(w, h) to set the size
  - resizeRelocate(x, y, w, h) to set the position and size at the same time

  



### SuiTabPane

An element that allows the user to switch between different views/tabs.

**Properties:**

- all common properties

- all common region properties

- all common event properties

- ItemListProperty, ItemProperty

  Each item will be added to the tab pane as the content of a new tab. The title of the tab is specified by the "TitleProperty" of the child item.

- TabPaneMenuSideProperty

  Specifies the side on which the tabs are shown. By default, all tabs are displayed on the top side.

- TabClosingPolicyProperty

  Defines when tabs can be closed. Closing can be disabled completly, enabled only for the selected tab or enabled for all tabs.

- OnSelectedTabEventProperty

  A listener that is called when a new tab was selected by the user or when the currently selected tab was removed and a new tab had to be selected. 

- OnTabClosedEventProperty

  A listener that is called after a tab was manually closed by the user.



### SuiSplitPane

An element that can display multiple other views inside of it. The user can control the size of the view via dividers.

**Properties:**

- all common properties

- all common region properties

- all common event properties

- ItemListProperty, ItemProperty

  Each item will be added as a new visible view to the split pane. Items will be added from left to right or top to bottom.

- OrientationProperty

  Whether the views are divided horizontally (left to right) or vertically  (top to bottom).

- SplitDividerPositionProperty

  Defines the initial positions of all dividers as percentages. If the "fixed"-flag is set, the dividers can not be dragged by the user

- OnDividerDraggedEventProperty

  A listener that is called when the user moved a divider. The divider is identified by an index into the list of all dividers.



### SuiMenuBar

An element usually placed at the very top containing buttons-like expandable menus.

**Properties:**

- all common properties

- all common region properties

- all common event properties

- MenuContentProperty

  Defines the content of the menu-bar via a list of "SuiAbstractMenuItem". Each element in this list of the sup-type "SuiMenu" creates a top level entry in the menu-bar.

  Types of menu-items:

  - SuiMenu: an entry containing multiple sub-menu-entries
  - SuiMenuItem: a simple entry displaying a text. Triggers an event when clicked.
  - SuiCheckMenuItem: a simple entry displaying a text and a toggle between selected and not selected. Triggers an event when toggled with the new state.
  - SuiSeparatorMenuItem: an entry displaying a simple small line for better organizing the menu  

- SystemMenuBarProperty

  Whether to use the menu bar of the operating system if it supports it.



### SuiImage

An element displaying an image.

**Properties:**

- all common properties

- all common event properties

- ImageProperty

  Defines what image to show

- ImageSizeProperty

  Defines the size of the element. Since the element does not accept the common region properties, this property is the only way to control the width and height.

  The size is defined as "ImageDimensions" for width and height. There are 4 types of these dimensions:

  - UNDEFINED: no value has to be set. The size of the original image will be used for this axis (or 0). 
  - ABSOLUTE: the given values is the fixed size of an axis in pixels.
  - RELATIVE: the resulting size is the size of the original image multiplied by the given value. 
  - PARENT-RELATIVE: the element size is always the size of its parent multiplied by the given value.

  No min or max values are directly possible here. One workaround is to put the image element into an container and give that one a min/max size. The size of the image should then be defined with "parent-relative".

- PreserveRatioProperty

  Whether the element should aways keep the same aspect ratio as the original image. The width is always kept as defined in the ImageSizeProperty and the height is then calculated. The height of the ImageSizeProperty is then always ignored and can be set to "undefined".



### SuiList

An element displaying a scrollable list of items.

**Properties:**

- all common properties

- all common region properties

- all common event properties

- ContentItemsProperty

  Defines the items displayed in the list

- OnItemSelectedEventProperty

  A listener that is called when the user selects an item / multiple items.

- MultiselectProperty

  Whether to allow the user to select more than one item at the same time.

- PromptTextProperty

  Specifies the text displayed in the list when it does not show any items (i.e. is empty).

