package de.ruegnerlukas.simpleapplication.simpleui.assets.properties;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ActionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.CheckedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.DatePickerActionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.FocusEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.HoverEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.KeyEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.MouseButtonEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.MouseDragEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.MouseMoveEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.MouseScrollEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ScrollEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.TextContentEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ValueChangedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnActionEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnCheckedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnFocusChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnFocusLostEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnFocusReceivedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnHoverChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnHoverStartedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnHoverStoppedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnKeyPressedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnKeyReleasedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnKeyTypedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnMouseClickedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnMouseDragEnteredEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnMouseDragExitedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnMouseDragOverEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnMouseDragReleasedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnMouseDraggedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnMouseEnteredEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnMouseExitedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnMouseMovedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnMousePressedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnMouseReleasedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnMouseScrollEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnMouseScrollFinishedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnMouseScrollStartedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnScrollHorizontalEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnSelectedDateEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnTextChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnTextEnteredEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnUncheckedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnValueChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;

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
	public static SuiProperty eventKeyPressed(final SUIEventListener<KeyEventData> listener) {
		return eventKeyPressed(null, listener);
	}




	/**
	 * When a node or its child node has input focus and a key has been pressed.
	 *
	 * @param listener the listener for events with {@link KeyEventData}.
	 * @return a {@link OnKeyPressedEventProperty}
	 */
	public static SuiProperty eventKeyPressed(final String propertyId, final SUIEventListener<KeyEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnKeyPressedEventProperty(propertyId, listener);
	}




	/**
	 * When a node or its child node has input focus and a key has been released.
	 *
	 * @param listener the listener for events with {@link KeyEventData}.
	 * @return a {@link OnKeyReleasedEventProperty}
	 */
	public static SuiProperty eventKeyReleased(final SUIEventListener<KeyEventData> listener) {
		return eventKeyReleased(null, listener);
	}




	/**
	 * When a node or its child node has input focus and a key has been released.
	 *
	 * @param listener the listener for events with {@link KeyEventData}.
	 * @return a {@link OnKeyReleasedEventProperty}
	 */
	public static SuiProperty eventKeyReleased(final String propertyId, final SUIEventListener<KeyEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnKeyReleasedEventProperty(propertyId, listener);
	}




	/**
	 * When a node or its child node has input focus and a key has been typed.
	 *
	 * @param listener the listener for events with {@link KeyEventData}.
	 * @return a {@link OnKeyReleasedEventProperty}
	 */
	public static SuiProperty eventKeyTyped(final SUIEventListener<KeyEventData> listener) {
		return eventKeyTyped(null, listener);
	}




	/**
	 * When a node or its child node has input focus and a key has been typed.
	 *
	 * @param listener the listener for events with {@link KeyEventData}.
	 * @return a {@link OnKeyReleasedEventProperty}
	 */
	public static SuiProperty eventKeyTyped(final String propertyId, final SUIEventListener<KeyEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnKeyTypedEventProperty(propertyId, listener);
	}




	/**
	 * When a mouse button has been clicked (pressed and released) on a node.
	 *
	 * @param listener the listener for events with {@link MouseButtonEventData}.
	 * @return a {@link OnMouseClickedEventProperty}
	 */
	public static SuiProperty eventMouseClicked(final SUIEventListener<MouseButtonEventData> listener) {
		return eventMouseClicked(null, listener);
	}




	/**
	 * When a mouse button has been clicked (pressed and released) on a node.
	 *
	 * @param listener the listener for events with {@link MouseButtonEventData}.
	 * @return a {@link OnMouseClickedEventProperty}
	 */
	public static SuiProperty eventMouseClicked(final String propertyId, final SUIEventListener<MouseButtonEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseClickedEventProperty(propertyId, listener);
	}




	/**
	 * When a mouse button has been pressed on a node.
	 *
	 * @param listener the listener for events with {@link MouseButtonEventData}.
	 * @return a {@link OnMousePressedEventProperty}
	 */
	public static SuiProperty eventMousePressed(final SUIEventListener<MouseButtonEventData> listener) {
		return eventMousePressed(null, listener);
	}




	/**
	 * When a mouse button has been pressed on a node.
	 *
	 * @param listener the listener for events with {@link MouseButtonEventData}.
	 * @return a {@link OnMousePressedEventProperty}
	 */
	public static SuiProperty eventMousePressed(final String propertyId, final SUIEventListener<MouseButtonEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMousePressedEventProperty(propertyId, listener);
	}




	/**
	 * When a mouse button has been pressed on a node.
	 *
	 * @param listener the listener for events with {@link MouseButtonEventData}.
	 * @return a {@link OnMouseReleasedEventProperty}
	 */
	public static SuiProperty eventMouseReleased(final SUIEventListener<MouseButtonEventData> listener) {
		return eventMouseReleased(null, listener);
	}




	/**
	 * When a mouse button has been pressed on a node.
	 *
	 * @param listener the listener for events with {@link MouseButtonEventData}.
	 * @return a {@link OnMouseReleasedEventProperty}
	 */
	public static SuiProperty eventMouseReleased(final String propertyId, final SUIEventListener<MouseButtonEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseReleasedEventProperty(propertyId, listener);
	}




	/**
	 * When a full press-drag-release gesture enters a node.
	 *
	 * @param listener the listener for events with {@link MouseDragEventData}.
	 * @return a {@link OnMouseDragEnteredEventProperty}
	 */
	public static SuiProperty eventMouseDragEntered(final SUIEventListener<MouseDragEventData> listener) {
		return eventMouseDragEntered(null, listener);
	}




	/**
	 * When a full press-drag-release gesture enters a node.
	 *
	 * @param listener the listener for events with {@link MouseDragEventData}.
	 * @return a {@link OnMouseDragEnteredEventProperty}
	 */
	public static SuiProperty eventMouseDragEntered(final String propertyId, final SUIEventListener<MouseDragEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseDragEnteredEventProperty(propertyId, listener);
	}




	/**
	 * When a full press-drag-release gesture leaves a node.
	 *
	 * @param listener the listener for events with {@link MouseDragEventData}.
	 * @return a {@link OnMouseDragExitedEventProperty}
	 */
	public static SuiProperty eventMouseDragExited(final SUIEventListener<MouseDragEventData> listener) {
		return eventMouseDragExited(null, listener);
	}




	/**
	 * When a full press-drag-release gesture leaves a node.
	 *
	 * @param listener the listener for events with {@link MouseDragEventData}.
	 * @return a {@link OnMouseDragExitedEventProperty}
	 */
	public static SuiProperty eventMouseDragExited(final String propertyId, final SUIEventListener<MouseDragEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseDragExitedEventProperty(propertyId, listener);
	}




	/**
	 * When a mouse button is pressed on a node and then dragged.
	 *
	 * @param listener the listener for events with {@link MouseDragEventData}.
	 * @return a {@link OnMouseDraggedEventProperty}
	 */
	public static SuiProperty eventMouseDragged(final SUIEventListener<MouseDragEventData> listener) {
		return eventMouseDragged(null, listener);
	}




	/**
	 * When a mouse button is pressed on a node and then dragged.
	 *
	 * @param listener the listener for events with {@link MouseDragEventData}.
	 * @return a {@link OnMouseDraggedEventProperty}
	 */
	public static SuiProperty eventMouseDragged(final String propertyId, final SUIEventListener<MouseDragEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseDraggedEventProperty(propertyId, listener);
	}




	/**
	 * When a full press-drag-release gesture ends (by releasing mouse button) within a node.
	 *
	 * @param listener the listener for events with {@link MouseDragEventData}.
	 * @return a {@link OnMouseDragReleasedEventProperty}
	 */
	public static SuiProperty eventMouseDragReleased(final SUIEventListener<MouseDragEventData> listener) {
		return eventMouseDragReleased(null, listener);
	}




	/**
	 * When a full press-drag-release gesture ends (by releasing mouse button) within a node.
	 *
	 * @param listener the listener for events with {@link MouseDragEventData}.
	 * @return a {@link OnMouseDragReleasedEventProperty}
	 */
	public static SuiProperty eventMouseDragReleased(final String propertyId, final SUIEventListener<MouseDragEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseDragReleasedEventProperty(propertyId, listener);
	}




	/**
	 * When a full press-drag-release gesture progresses within a node.
	 *
	 * @param listener the listener for events with {@link MouseDragEventData}.
	 * @return a {@link OnMouseDragOverEventProperty}
	 */
	public static SuiProperty eventMouseDragOver(final SUIEventListener<MouseDragEventData> listener) {
		return eventMouseDragOver(null, listener);
	}




	/**
	 * When a full press-drag-release gesture progresses within a node.
	 *
	 * @param listener the listener for events with {@link MouseDragEventData}.
	 * @return a {@link OnMouseDragOverEventProperty}
	 */
	public static SuiProperty eventMouseDragOver(final String propertyId, final SUIEventListener<MouseDragEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseDragOverEventProperty(propertyId, listener);
	}




	/**
	 * When the mouse enters a node.
	 *
	 * @param listener the listener for events with {@link MouseMoveEventData}.
	 * @return a {@link OnMouseEnteredEventProperty}
	 */
	public static SuiProperty eventMouseEntered(final SUIEventListener<MouseMoveEventData> listener) {
		return eventMouseEntered(null, listener);
	}




	/**
	 * When the mouse enters a node.
	 *
	 * @param listener the listener for events with {@link MouseMoveEventData}.
	 * @return a {@link OnMouseEnteredEventProperty}
	 */
	public static SuiProperty eventMouseEntered(final String propertyId, final SUIEventListener<MouseMoveEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseEnteredEventProperty(propertyId, listener);
	}




	/**
	 * When the mouse exits a node.
	 *
	 * @param listener the listener for events with {@link MouseMoveEventData}.
	 * @return a {@link OnMouseExitedEventProperty}
	 */
	public static SuiProperty eventMouseExited(final SUIEventListener<MouseMoveEventData> listener) {
		return eventMouseExited(null, listener);
	}




	/**
	 * When the mouse exits a node.
	 *
	 * @param listener the listener for events with {@link MouseMoveEventData}.
	 * @return a {@link OnMouseExitedEventProperty}
	 */
	public static SuiProperty eventMouseExited(final String propertyId, final SUIEventListener<MouseMoveEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseExitedEventProperty(propertyId, listener);
	}




	/**
	 * When mouse cursor moves within a node but no buttons have been pushed.
	 *
	 * @param listener the listener for events with {@link MouseMoveEventData}.
	 * @return a {@link OnMouseMovedEventProperty}
	 */
	public static SuiProperty eventMouseMoved(final SUIEventListener<MouseMoveEventData> listener) {
		return eventMouseMoved(null, listener);
	}




	/**
	 * When mouse cursor moves within a node but no buttons have been pushed.
	 *
	 * @param listener the listener for events with {@link MouseMoveEventData}.
	 * @return a {@link OnMouseMovedEventProperty}
	 */
	public static SuiProperty eventMouseMoved(final String propertyId, final SUIEventListener<MouseMoveEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseMovedEventProperty(propertyId, listener);
	}




	/**
	 * When user performs a scrolling action
	 *
	 * @param listener the listener for events with {@link MouseScrollEventData}.
	 * @return a {@link OnMouseScrollEventProperty}
	 */
	public static SuiProperty eventMouseScroll(final SUIEventListener<MouseScrollEventData> listener) {
		return eventMouseScroll(null, listener);
	}




	/**
	 * When user performs a scrolling action
	 *
	 * @param listener the listener for events with {@link MouseScrollEventData}.
	 * @return a {@link OnMouseScrollEventProperty}
	 */
	public static SuiProperty eventMouseScroll(final String propertyId, final SUIEventListener<MouseScrollEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseScrollEventProperty(propertyId, listener);
	}




	/**
	 * When a scrolling gesture is detected.
	 *
	 * @param listener the listener for events with {@link MouseScrollEventData}.
	 * @return a {@link OnMouseScrollEventProperty}
	 */
	public static SuiProperty eventMouseScrollStarted(final SUIEventListener<MouseScrollEventData> listener) {
		return eventMouseScrollStarted(null, listener);
	}




	/**
	 * When a scrolling gesture is detected.
	 *
	 * @param listener the listener for events with {@link MouseScrollEventData}.
	 * @return a {@link OnMouseScrollEventProperty}
	 */
	public static SuiProperty eventMouseScrollStarted(final String propertyId, final SUIEventListener<MouseScrollEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseScrollStartedEventProperty(propertyId, listener);
	}




	/**
	 * When a scrolling gesture ends.
	 *
	 * @param listener the listener for events with {@link MouseScrollEventData}.
	 * @return a {@link OnMouseScrollEventProperty}
	 */
	public static SuiProperty eventMouseScrollFinished(final SUIEventListener<MouseScrollEventData> listener) {
		return eventMouseScrollFinished(null, listener);
	}




	/**
	 * When a scrolling gesture ends.
	 *
	 * @param listener the listener for events with {@link MouseScrollEventData}.
	 * @return a {@link OnMouseScrollEventProperty}
	 */
	public static SuiProperty eventMouseScrollFinished(final String propertyId, final SUIEventListener<MouseScrollEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnMouseScrollFinishedEventProperty(propertyId, listener);
	}




	/**
	 * When the input focus of a node changed.
	 *
	 * @param listener the listener for events with {@link FocusEventData}.
	 * @return a {@link OnFocusChangedEventProperty}
	 */
	public static SuiProperty eventFocusChanged(final SUIEventListener<FocusEventData> listener) {
		return eventFocusChanged(null, listener);
	}




	/**
	 * When the input focus of a node changed.
	 *
	 * @param listener the listener for events with {@link FocusEventData}.
	 * @return a {@link OnFocusChangedEventProperty}
	 */
	public static SuiProperty eventFocusChanged(final String propertyId, final SUIEventListener<FocusEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnFocusChangedEventProperty(propertyId, listener);
	}




	/**
	 * When a node now has the input focus.
	 *
	 * @param listener the listener for events with {@link FocusEventData}.
	 * @return a {@link OnFocusReceivedEventProperty}
	 */
	public static SuiProperty eventFocusReceived(final SUIEventListener<FocusEventData> listener) {
		return eventFocusReceived(null, listener);
	}




	/**
	 * When a node now has the input focus.
	 *
	 * @param listener the listener for events with {@link FocusEventData}.
	 * @return a {@link OnFocusReceivedEventProperty}
	 */
	public static SuiProperty eventFocusReceived(final String propertyId, final SUIEventListener<FocusEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnFocusReceivedEventProperty(propertyId, listener);
	}




	/**
	 * When a node lost the input focus.
	 *
	 * @param listener the listener for events with {@link FocusEventData}.
	 * @return a {@link OnFocusLostEventProperty}
	 */
	public static SuiProperty eventFocusLost(final SUIEventListener<FocusEventData> listener) {
		return eventFocusLost(null, listener);
	}




	/**
	 * When a node lost the input focus.
	 *
	 * @param listener the listener for events with {@link FocusEventData}.
	 * @return a {@link OnFocusLostEventProperty}
	 */
	public static SuiProperty eventFocusLost(final String propertyId, final SUIEventListener<FocusEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnFocusLostEventProperty(propertyId, listener);
	}




	/**
	 * When the hover over a node changes.
	 *
	 * @param listener the listener for events with {@link HoverEventData}.
	 * @return a {@link OnHoverChangedEventProperty}
	 */
	public static SuiProperty eventHoverChanged(final SUIEventListener<HoverEventData> listener) {
		return eventHoverChanged(null, listener);
	}




	/**
	 * When the hover over a node changes.
	 *
	 * @param listener the listener for events with {@link HoverEventData}.
	 * @return a {@link OnHoverChangedEventProperty}
	 */
	public static SuiProperty eventHoverChanged(final String propertyId, final SUIEventListener<HoverEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnHoverChangedEventProperty(propertyId, listener);
	}




	/**
	 * When a node is now being hovered over.
	 *
	 * @param listener the listener for events with {@link HoverEventData}.
	 * @return a {@link OnHoverStartedEventProperty}
	 */
	public static SuiProperty eventHoverStarted(final SUIEventListener<HoverEventData> listener) {
		return eventHoverStarted(null, listener);
	}




	/**
	 * When a node is now being hovered over.
	 *
	 * @param listener the listener for events with {@link HoverEventData}.
	 * @return a {@link OnHoverStartedEventProperty}
	 */
	public static SuiProperty eventHoverStarted(final String propertyId, final SUIEventListener<HoverEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnHoverStartedEventProperty(propertyId, listener);
	}




	/**
	 * When a node is no longer being hovered over.
	 *
	 * @param listener the listener for events with {@link HoverEventData}.
	 * @return a {@link OnHoverStoppedEventProperty}
	 */
	public static SuiProperty eventHoverStopped(final SUIEventListener<HoverEventData> listener) {
		return eventHoverStopped(null, listener);
	}




	/**
	 * When a node is no longer being hovered over.
	 *
	 * @param listener the listener for events with {@link HoverEventData}.
	 * @return a {@link OnHoverStoppedEventProperty}
	 */
	public static SuiProperty eventHoverStopped(final String propertyId, final SUIEventListener<HoverEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnHoverStoppedEventProperty(propertyId, listener);
	}




	/**
	 * When an action of a node is performed.
	 *
	 * @param listener the listener for events with {@link ActionEventData}.
	 * @return a {@link OnActionEventProperty}
	 */
	public static SuiProperty eventAction(final SUIEventListener<ActionEventData> listener) {
		return eventAction(null, listener);
	}




	/**
	 * When an action of a node is performed.
	 *
	 * @param listener the listener for events with {@link ActionEventData}.
	 * @return a {@link OnActionEventProperty}
	 */
	public static SuiProperty eventAction(final String propertyId, final SUIEventListener<ActionEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnActionEventProperty(propertyId, listener);
	}




	/**
	 * When an action of a date picker is performed.
	 *
	 * @param listener the listener for events with {@link DatePickerActionEventData}.
	 * @return a {@link OnActionEventProperty}
	 */
	public static SuiProperty eventDatePickerAction(final SUIEventListener<DatePickerActionEventData> listener) {
		return eventDatePickerAction(null, listener);
	}




	/**
	 * When an action of a date picker is performed.
	 *
	 * @param listener the listener for events with {@link DatePickerActionEventData}.
	 * @return a {@link OnActionEventProperty}
	 */
	public static SuiProperty eventDatePickerAction(final String propertyId, final SUIEventListener<DatePickerActionEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnSelectedDateEventProperty<>(propertyId, listener);
	}




	/**
	 * When a selection changed.
	 *
	 * @param listener the listener for events with {@link ValueChangedEventData}.
	 * @return a {@link OnValueChangedEventProperty}
	 */
	public static <T> SuiProperty eventValueChanged(final SUIEventListener<ValueChangedEventData<T>> listener) {
		return eventValueChanged(null, listener);
	}




	/**
	 * When a selection changed.
	 *
	 * @param listener the listener for events with {@link ValueChangedEventData}.
	 * @return a {@link OnValueChangedEventProperty}
	 */
	public static <T> SuiProperty eventValueChanged(final String propertyId,
													final SUIEventListener<ValueChangedEventData<T>> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnValueChangedEventProperty<>(propertyId, listener);
	}




	/**
	 * When a selection changed.
	 *
	 * @param expectedType the expected type of the selected items
	 * @param listener     the listener for events with {@link ValueChangedEventData}.
	 * @param <T>          generic type
	 * @return a {@link OnValueChangedEventProperty}
	 */
	public static <T> SuiProperty eventValueChangedType(final Class<T> expectedType,
														final SUIEventListener<ValueChangedEventData<T>> listener) {
		return eventValueChangedType(null, expectedType, listener);
	}




	/**
	 * When a selection changed.
	 *
	 * @param expectedType the expected type of the selected items
	 * @param listener     the listener for events with {@link ValueChangedEventData}.
	 * @param <T>          generic type
	 * @return a {@link OnValueChangedEventProperty}
	 */
	public static <T> SuiProperty eventValueChangedType(final String propertyId,
														final Class<T> expectedType,
														final SUIEventListener<ValueChangedEventData<T>> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnValueChangedEventProperty<>(propertyId, listener);
	}




	/**
	 * When a node (for example a scroll pane) was scroll horizontally.
	 *
	 * @param listener the listener for events with {@link ScrollEventData}.
	 * @return a {@link OnScrollHorizontalEventProperty}
	 */
	public static SuiProperty eventScrollHorizontal(final SUIEventListener<ScrollEventData> listener) {
		return eventScrollHorizontal(null, listener);
	}




	/**
	 * When a node (for example a scroll pane) was scroll horizontally.
	 *
	 * @param listener the listener for events with {@link ScrollEventData}.
	 * @return a {@link OnScrollHorizontalEventProperty}
	 */
	public static SuiProperty eventScrollHorizontal(final String propertyId, final SUIEventListener<ScrollEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnScrollHorizontalEventProperty(propertyId, listener);
	}




	/**
	 * When a box (for example a check box) was checked / selected.
	 *
	 * @param listener the listener for events with {@link CheckedEventData}.
	 * @return a {@link OnCheckedEventProperty}
	 */
	public static SuiProperty eventChecked(final SUIEventListener<CheckedEventData> listener) {
		return eventChecked(null, listener);
	}




	/**
	 * When a box (for example a check box) was checked / selected.
	 *
	 * @param listener the listener for events with {@link CheckedEventData}.
	 * @return a {@link OnCheckedEventProperty}
	 */
	public static SuiProperty eventChecked(final String propertyId, final SUIEventListener<CheckedEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnCheckedEventProperty(propertyId, listener);
	}




	/**
	 * When a box (for example a check box) was unchecked / deselected.
	 *
	 * @param listener the listener for events with {@link CheckedEventData}.
	 * @return a {@link OnUncheckedEventProperty}
	 */
	public static SuiProperty eventUnchecked(final SUIEventListener<CheckedEventData> listener) {
		return eventUnchecked(null, listener);
	}




	/**
	 * When a box (for example a check box) was unchecked / deselected.
	 *
	 * @param listener the listener for events with {@link CheckedEventData}.
	 * @return a {@link OnUncheckedEventProperty}
	 */
	public static SuiProperty eventUnchecked(final String propertyId, final SUIEventListener<CheckedEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnUncheckedEventProperty(propertyId, listener);
	}




	/**
	 * When the text of an input (e.g. text field) was entered i.e. accepted.
	 *
	 * @param listener the listener for events with {@link CheckedEventData}.
	 * @return a {@link OnUncheckedEventProperty}
	 */
	public static SuiProperty eventTextEntered(final SUIEventListener<TextContentEventData> listener) {
		return eventTextEntered(null, listener);
	}




	/**
	 * When the text of an input (e.g. text field) was entered i.e. accepted.
	 *
	 * @param listener the listener for events with {@link CheckedEventData}.
	 * @return a {@link OnUncheckedEventProperty}
	 */
	public static SuiProperty eventTextEntered(final String propertyId, final SUIEventListener<TextContentEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnTextEnteredEventProperty(propertyId, listener);
	}




	/**
	 * When the text of an input changed.
	 *
	 * @param listener the listener for events with {@link CheckedEventData}.
	 * @return a {@link OnTextChangedEventProperty}
	 */
	public static SuiProperty eventTextChanged(final SUIEventListener<TextContentEventData> listener) {
		return eventTextChanged(null, listener);
	}




	/**
	 * When the text of an input changed.
	 *
	 * @param listener the listener for events with {@link CheckedEventData}.
	 * @return a {@link OnTextChangedEventProperty}
	 */
	public static SuiProperty eventTextChanged(final String propertyId, final SUIEventListener<TextContentEventData> listener) {
		Validations.INPUT.notNull(listener).exception("The listener can not be null");
		return new OnTextChangedEventProperty(propertyId, listener);
	}


}
