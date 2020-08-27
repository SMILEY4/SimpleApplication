package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.events.KeyEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.MouseButtonEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.MouseDragEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.MouseMoveEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.events.ScrollEventData;
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
		return new OnKeyPressedEventProperty(listener);
	}




	/**
	 * When a node or its child node has input focus and a key has been released.
	 *
	 * @param listener the listener for events with {@link KeyEventData}.
	 * @return a {@link OnKeyReleasedEventProperty}
	 */
	public static Property eventKeyReleased(final SUIEventListener<KeyEventData> listener) {
		return new OnKeyReleasedEventProperty(listener);
	}




	/**
	 * When a node or its child node has input focus and a key has been typed.
	 *
	 * @param listener the listener for events with {@link KeyEventData}.
	 * @return a {@link OnKeyReleasedEventProperty}
	 */
	public static Property eventKeyTyped(final SUIEventListener<KeyEventData> listener) {
		return new OnKeyTypedEventProperty(listener);
	}




	/**
	 * When a mouse button has been clicked (pressed and released) on a node.
	 *
	 * @param listener the listener for events with {@link MouseButtonEventData}.
	 * @return a {@link OnMouseClickedEventProperty}
	 */
	public static Property eventMouseClicked(final SUIEventListener<MouseButtonEventData> listener) {
		return new OnMouseClickedEventProperty(listener);
	}




	/**
	 * When a mouse button has been pressed on a node.
	 *
	 * @param listener the listener for events with {@link MouseButtonEventData}.
	 * @return a {@link OnMousePressedEventProperty}
	 */
	public static Property eventMousePressed(final SUIEventListener<MouseButtonEventData> listener) {
		return new OnMousePressedEventProperty(listener);
	}




	/**
	 * When a mouse button has been pressed on a node.
	 *
	 * @param listener the listener for events with {@link MouseButtonEventData}.
	 * @return a {@link OnMouseReleasedEventProperty}
	 */
	public static Property eventMouseReleased(final SUIEventListener<MouseButtonEventData> listener) {
		return new OnMouseReleasedEventProperty(listener);
	}




	/**
	 * When a full press-drag-release gesture enters a node.
	 *
	 * @param listener the listener for events with {@link MouseDragEventData}.
	 * @return a {@link OnMouseDragEnteredEventProperty}
	 */
	public static Property eventMouseDragEntered(final SUIEventListener<MouseDragEventData> listener) {
		return new OnMouseDragEnteredEventProperty(listener);
	}




	/**
	 * When a full press-drag-release gesture leaves a node.
	 *
	 * @param listener the listener for events with {@link MouseDragEventData}.
	 * @return a {@link OnMouseDragExitedEventProperty}
	 */
	public static Property eventMouseDragExited(final SUIEventListener<MouseDragEventData> listener) {
		return new OnMouseDragExitedEventProperty(listener);
	}




	/**
	 * When a mouse button is pressed on a node and then dragged.
	 *
	 * @param listener the listener for events with {@link MouseDragEventData}.
	 * @return a {@link OnMouseDraggedEventProperty}
	 */
	public static Property eventMouseDragged(final SUIEventListener<MouseDragEventData> listener) {
		return new OnMouseDraggedEventProperty(listener);
	}




	/**
	 * When a full press-drag-release gesture ends (by releasing mouse button) within a node.
	 *
	 * @param listener the listener for events with {@link MouseDragEventData}.
	 * @return a {@link OnMouseDragReleasedEventProperty}
	 */
	public static Property eventMouseDragReleased(final SUIEventListener<MouseDragEventData> listener) {
		return new OnMouseDragReleasedEventProperty(listener);
	}




	/**
	 * When a full press-drag-release gesture progresses within a node.
	 *
	 * @param listener the listener for events with {@link MouseDragEventData}.
	 * @return a {@link OnMouseDragOverEventProperty}
	 */
	public static Property eventMouseDragOver(final SUIEventListener<MouseDragEventData> listener) {
		return new OnMouseDragOverEventProperty(listener);
	}




	/**
	 * When the mouse enters a node.
	 *
	 * @param listener the listener for events with {@link MouseMoveEventData}.
	 * @return a {@link OnMouseEnteredEventProperty}
	 */
	public static Property eventMouseEntered(final SUIEventListener<MouseMoveEventData> listener) {
		return new OnMouseEnteredEventProperty(listener);
	}




	/**
	 * When the mouse exits a node.
	 *
	 * @param listener the listener for events with {@link MouseMoveEventData}.
	 * @return a {@link OnMouseExitedEventProperty}
	 */
	public static Property eventMouseExited(final SUIEventListener<MouseMoveEventData> listener) {
		return new OnMouseExitedEventProperty(listener);
	}




	/**
	 * When mouse cursor moves within a node but no buttons have been pushed.
	 *
	 * @param listener the listener for events with {@link MouseMoveEventData}.
	 * @return a {@link OnMouseMovedEventProperty}
	 */
	public static Property eventMouseMoved(final SUIEventListener<MouseMoveEventData> listener) {
		return new OnMouseMovedEventProperty(listener);
	}




	/**
	 * When user performs a scrolling action
	 *
	 * @param listener the listener for events with {@link ScrollEventData}.
	 * @return a {@link OnScrollEventProperty}
	 */
	public static Property eventScroll(final SUIEventListener<ScrollEventData> listener) {
		return new OnScrollEventProperty(listener);
	}




	/**
	 * When a scrolling gesture is detected.
	 *
	 * @param listener the listener for events with {@link ScrollEventData}.
	 * @return a {@link OnScrollEventProperty}
	 */
	public static Property eventScrollStarted(final SUIEventListener<ScrollEventData> listener) {
		return new OnScrollStartedEventProperty(listener);
	}




	/**
	 * When a scrolling gesture ends.
	 *
	 * @param listener the listener for events with {@link ScrollEventData}.
	 * @return a {@link OnScrollEventProperty}
	 */
	public static Property eventScrollFinished(final SUIEventListener<ScrollEventData> listener) {
		return new OnScrollFinishedEventProperty(listener);
	}


}
