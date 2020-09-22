### SuiTabPane

An element that allows the user to switch between different views/tabs.

**Properties:**

- all common properties

- all common region properties

- all common event properties

- ItemListProperty, ItemProperty

  Each item will be added to the tab pane as the content of a new tab. The title of the tab is specified by the "TabTitleProperty" of the child item.

- TabPaneMenuSideProperty

  Specifies the side on which the tabs are shown. By default, all tabs are displayed on the top side.

- TabClosingPolicyProperty

  Defines when tabs can be closed. Closing can be disabled completly, enabled only for the selected tab or enabled for all tabs.

- OnSelectedTabEventProperty

  A listener that is called when a new tab was selected by the user or when the currently selected tab was removed and a new tab had to be selected. 

- OnTabClosedEventProperty

  A listener that is called after a tab was manually closed by the user.



### SuiSplitPane

A element that can display multiple other views inside of it. The user can control the size of the view via dividers.

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



