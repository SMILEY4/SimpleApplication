package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.events.ActionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.CheckedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.DatePickerActionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.FocusEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.HoverEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.KeyEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.MouseButtonEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.MouseDragEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.MouseMoveEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.MouseScrollEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.events.ScrollEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.TextContentEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.ValueChangedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;

public final class EventProperties {


	/**
	 * hidden constructor
	 */
	private EventProperties() {
		// hidden constructor
	}




	/**
	 * When a node or its child node has input focus and a key has been pressed.
	 *
	 * @param listener the listener for events with {@link KeyEventData}.
	 * @return a {@link OnKeyPressedEventProperty}
	 */
	public static Property eventKeyPressed(final SUIEventListener<KeyEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnKeyPressedEventProperty(listener);
	}




	/**
	 * When a node or its child node has input focus and a key has been released.
	 *
	 * @param listener the listener for events with {@link KeyEventData}.
	 * @return a {@link OnKeyReleasedEventProperty}
	 */
	public static Property eventKeyReleased(final SUIEventListener<KeyEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnKeyReleasedEventProperty(listener);
	}




	/**
	 * When a node or its child node has input focus and a key has been typed.
	 *
	 * @param listener the listener for events with {@link KeyEventData}.
	 * @return a {@link OnKeyReleasedEventProperty}
	 */
	public static Property eventKeyTyped(final SUIEventListener<KeyEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnKeyTypedEventProperty(listener);
	}




	/**
	 * When a mouse button has been clicked (pressed and released) on a node.
	 *
	 * @param listener the listener for events with {@link MouseButtonEventData}.
	 * @return a {@link OnMouseClickedEventProperty}
	 */
	public static Property eventMouseClicked(final SUIEventListener<MouseButtonEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseClickedEventProperty(listener);
	}




	/**
	 * When a mouse button has been pressed on a node.
	 *
	 * @param listener the listener for events with {@link MouseButtonEventData}.
	 * @return a {@link OnMousePressedEventProperty}
	 */
	public static Property eventMousePressed(final SUIEventListener<MouseButtonEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMousePressedEventProperty(listener);
	}




	/**
	 * When a mouse button has been pressed on a node.
	 *
	 * @param listener the listener for events with {@link MouseButtonEventData}.
	 * @return a {@link OnMouseReleasedEventProperty}
	 */
	public static Property eventMouseReleased(final SUIEventListener<MouseButtonEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseReleasedEventProperty(listener);
	}




	/**
	 * When a full press-drag-release gesture enters a node.
	 *
	 * @param listener the listener for events with {@link MouseDragEventData}.
	 * @return a {@link OnMouseDragEnteredEventProperty}
	 */
	public static Property eventMouseDragEntered(final SUIEventListener<MouseDragEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseDragEnteredEventProperty(listener);
	}




	/**
	 * When a full press-drag-release gesture leaves a node.
	 *
	 * @param listener the listener for events with {@link MouseDragEventData}.
	 * @return a {@link OnMouseDragExitedEventProperty}
	 */
	public static Property eventMouseDragExited(final SUIEventListener<MouseDragEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseDragExitedEventProperty(listener);
	}




	/**
	 * When a mouse button is pressed on a node and then dragged.
	 *
	 * @param listener the listener for events with {@link MouseDragEventData}.
	 * @return a {@link OnMouseDraggedEventProperty}
	 */
	public static Property eventMouseDragged(final SUIEventListener<MouseDragEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseDraggedEventProperty(listener);
	}




	/**
	 * When a full press-drag-release gesture ends (by releasing mouse button) within a node.
	 *
	 * @param listener the listener for events with {@link MouseDragEventData}.
	 * @return a {@link OnMouseDragReleasedEventProperty}
	 */
	public static Property eventMouseDragReleased(final SUIEventListener<MouseDragEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseDragReleasedEventProperty(listener);
	}




	/**
	 * When a full press-drag-release gesture progresses within a node.
	 *
	 * @param listener the listener for events with {@link MouseDragEventData}.
	 * @return a {@link OnMouseDragOverEventProperty}
	 */
	public static Property eventMouseDragOver(final SUIEventListener<MouseDragEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseDragOverEventProperty(listener);
	}




	/**
	 * When the mouse enters a node.
	 *
	 * @param listener the listener for events with {@link MouseMoveEventData}.
	 * @return a {@link OnMouseEnteredEventProperty}
	 */
	public static Property eventMouseEntered(final SUIEventListener<MouseMoveEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseEnteredEventProperty(listener);
	}




	/**
	 * When the mouse exits a node.
	 *
	 * @param listener the listener for events with {@link MouseMoveEventData}.
	 * @return a {@link OnMouseExitedEventProperty}
	 */
	public static Property eventMouseExited(final SUIEventListener<MouseMoveEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseExitedEventProperty(listener);
	}




	/**
	 * When mouse cursor moves within a node but no buttons have been pushed.
	 *
	 * @param listener the listener for events with {@link MouseMoveEventData}.
	 * @return a {@link OnMouseMovedEventProperty}
	 */
	public static Property eventMouseMoved(final SUIEventListener<MouseMoveEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseMovedEventProperty(listener);
	}




	/**
	 * When user performs a scrolling action
	 *
	 * @param listener the listener for events with {@link MouseScrollEventData}.
	 * @return a {@link OnMouseScrollEventProperty}
	 */
	public static Property eventMouseScroll(final SUIEventListener<MouseScrollEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseScrollEventProperty(listener);
	}




	/**
	 * When a scrolling gesture is detected.
	 *
	 * @param listener the listener for events with {@link MouseScrollEventData}.
	 * @return a {@link OnMouseScrollEventProperty}
	 */
	public static Property eventMouseScrollStarted(final SUIEventListener<MouseScrollEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseScrollStartedEventProperty(listener);
	}




	/**
	 * When a scrolling gesture ends.
	 *
	 * @param listener the listener for events with {@link MouseScrollEventData}.
	 * @return a {@link OnMouseScrollEventProperty}
	 */
	public static Property eventMouseScrollFinished(final SUIEventListener<MouseScrollEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseScrollFinishedEventProperty(listener);
	}




	/**
	 * When the input focus of a node changed.
	 *
	 * @param listener the listener for events with {@link FocusEventData}.
	 * @return a {@link OnFocusChangedEventProperty}
	 */
	public static Property eventFocusChanged(final SUIEventListener<FocusEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnFocusChangedEventProperty(listener);
	}




	/**
	 * When a node now has the input focus.
	 *
	 * @param listener the listener for events with {@link FocusEventData}.
	 * @return a {@link OnFocusReceivedEventProperty}
	 */
	public static Property eventFocusReceived(final SUIEventListener<FocusEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnFocusReceivedEventProperty(listener);
	}




	/**
	 * When a node lost the input focus.
	 *
	 * @param listener the listener for events with {@link FocusEventData}.
	 * @return a {@link OnFocusLostEventProperty}
	 */
	public static Property eventFocusLost(final SUIEventListener<FocusEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnFocusLostEventProperty(listener);
	}




	/**
	 * When the hover over a node changes.
	 *
	 * @param listener the listener for events with {@link HoverEventData}.
	 * @return a {@link OnHoverChangedEventProperty}
	 */
	public static Property eventHoverChanged(final SUIEventListener<HoverEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnHoverChangedEventProperty(listener);
	}




	/**
	 * When a node is now being hovered over.
	 *
	 * @param listener the listener for events with {@link HoverEventData}.
	 * @return a {@link OnHoverStartedEventProperty}
	 */
	public static Property eventHoverStarted(final SUIEventListener<HoverEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnHoverStartedEventProperty(listener);
	}




	/**
	 * When a node is no longer being hovered over.
	 *
	 * @param listener the listener for events with {@link HoverEventData}.
	 * @return a {@link OnHoverStoppedEventProperty}
	 */
	public static Property eventHoverStopped(final SUIEventListener<HoverEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnHoverStoppedEventProperty(listener);
	}




	/**
	 * When an action of a node is performed.
	 *
	 * @param listener the listener for events with {@link ActionEventData}.
	 * @return a {@link OnActionEventProperty}
	 */
	public static Property eventAction(final SUIEventListener<ActionEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnActionEventProperty(listener);
	}




	/**
	 * When an action of a date picker is performed.
	 *
	 * @param listener the listener for events with {@link DatePickerActionEventData}.
	 * @return a {@link OnActionEventProperty}
	 */
	public static Property eventDatePickerAction(final SUIEventListener<DatePickerActionEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnSelectedDateEventProperty<>(listener);
	}




	/**
	 * When a selection changed.
	 *
	 * @param listener the listener for events with {@link ValueChangedEventData}.
	 * @return a {@link OnValueChangedEventProperty}
	 */
	public static <T> Property eventValueChanged(final SUIEventListener<ValueChangedEventData<T>> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnValueChangedEventProperty<>(listener);
	}




	/**
	 * When a selection changed.
	 *
	 * @param expectedType the expected type of the selected items
	 * @param listener     the listener for events with {@link ValueChangedEventData}.
	 * @param <T>          generic type
	 * @return a {@link OnValueChangedEventProperty}
	 */
	public static <T> Property eventValueChanged(final Class<T> expectedType, final SUIEventListener<ValueChangedEventData<T>> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnValueChangedEventProperty<>(listener);
	}




	/**
	 * When a node (for example a scroll pane) was scroll horizontally.
	 *
	 * @param listener the listener for events with {@link ScrollEventData}.
	 * @return a {@link OnScrollHorizontalEventProperty}
	 */
	public static Property eventScrollHorizontal(final SUIEventListener<ScrollEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnScrollHorizontalEventProperty(listener);
	}




	/**
	 * When a box (for example a check box) was checked / selected.
	 *
	 * @param listener the listener for events with {@link CheckedEventData}.
	 * @return a {@link OnCheckedEventProperty}
	 */
	public static Property eventChecked(final SUIEventListener<CheckedEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnCheckedEventProperty(listener);
	}




	/**
	 * When a box (for example a check box) was unchecked / deselected.
	 *
	 * @param listener the listener for events with {@link CheckedEventData}.
	 * @return a {@link OnUncheckedEventProperty}
	 */
	public static Property eventUnchecked(final SUIEventListener<CheckedEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnUncheckedEventProperty(listener);
	}




	/**
	 * When the text of an input (e.g. text field) was entered i.e. accepted.
	 *
	 * @param listener the listener for events with {@link CheckedEventData}.
	 * @return a {@link OnUncheckedEventProperty}
	 */
	public static Property eventTextEntered(final SUIEventListener<TextContentEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnTextEnteredEventProperty(listener);
	}




	/**
	 * When the text of an input changed.
	 *
	 * @param listener the listener for events with {@link CheckedEventData}.
	 * @return a {@link OnTextChangedEventProperty}
	 */
	public static Property eventTextChanged(final SUIEventListener<TextContentEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnTextChangedEventProperty(listener);
	}


}
