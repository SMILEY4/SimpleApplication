package de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.FocusEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.HoverEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.KeyEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.MouseButtonEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.MouseDragEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.MouseMoveEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.MouseScrollEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.EventProperties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.SuiEventListener;

@SuppressWarnings ("unchecked")
public interface CommonEventBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


	default T eventKeyPressed(final SuiEventListener<KeyEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventKeyPressed(listener));
		return (T) this;
	}


	default T eventKeyPressed(final String propertyId, final SuiEventListener<KeyEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventKeyPressed(propertyId, listener));
		return (T) this;
	}


	default T eventKeyReleased(final SuiEventListener<KeyEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventKeyReleased(listener));
		return (T) this;
	}


	default T eventKeyReleased(final String propertyId, final SuiEventListener<KeyEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventKeyReleased(propertyId, listener));
		return (T) this;
	}


	default T eventKeyTyped(final SuiEventListener<KeyEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventKeyTyped(listener));
		return (T) this;
	}


	default T eventKeyTyped(final String propertyId, final SuiEventListener<KeyEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventKeyTyped(propertyId, listener));
		return (T) this;
	}


	default T eventMouseClicked(final SuiEventListener<MouseButtonEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseClicked(listener));
		return (T) this;
	}


	default T eventMouseClicked(final String propertyId, final SuiEventListener<MouseButtonEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseClicked(propertyId, listener));
		return (T) this;
	}


	default T eventMousePressed(final SuiEventListener<MouseButtonEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMousePressed(listener));
		return (T) this;
	}


	default T eventMousePressed(final String propertyId, final SuiEventListener<MouseButtonEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMousePressed(propertyId, listener));
		return (T) this;
	}


	default T eventMouseReleased(final SuiEventListener<MouseButtonEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseReleased(listener));
		return (T) this;
	}


	default T eventMouseReleased(final String propertyId, final SuiEventListener<MouseButtonEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseReleased(propertyId, listener));
		return (T) this;
	}


	default T eventMouseDragEntered(final SuiEventListener<MouseDragEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseDragEntered(listener));
		return (T) this;
	}


	default T eventMouseDragEntered(final String propertyId, final SuiEventListener<MouseDragEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseDragEntered(propertyId, listener));
		return (T) this;
	}

	default T eventMouseDragExited(final SuiEventListener<MouseDragEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseDragExited(listener));
		return (T) this;
	}


	default T eventMouseDragExited(final String propertyId, final SuiEventListener<MouseDragEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseDragExited(propertyId, listener));
		return (T) this;
	}


	default T eventMouseDragged(final SuiEventListener<MouseDragEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseDragged(listener));
		return (T) this;
	}


	default T eventMouseDragged(final String propertyId, final SuiEventListener<MouseDragEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseDragged(propertyId, listener));
		return (T) this;
	}


	default T eventMouseDragOver(final SuiEventListener<MouseDragEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseDragOver(listener));
		return (T) this;
	}


	default T eventMouseDragOver(final String propertyId, final SuiEventListener<MouseDragEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseDragOver(propertyId, listener));
		return (T) this;
	}


	default T eventMouseDragReleased(final SuiEventListener<MouseDragEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseDragReleased(listener));
		return (T) this;
	}


	default T eventMouseDragReleased(final String propertyId, final SuiEventListener<MouseDragEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseDragReleased(propertyId, listener));
		return (T) this;
	}


	default T eventMouseEntered(final SuiEventListener<MouseMoveEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseEntered(listener));
		return (T) this;
	}


	default T eventMouseEntered(final String propertyId, final SuiEventListener<MouseMoveEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseEntered(propertyId, listener));
		return (T) this;
	}

	default T eventMouseExited(final SuiEventListener<MouseMoveEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseExited(listener));
		return (T) this;
	}


	default T eventMouseExited(final String propertyId, final SuiEventListener<MouseMoveEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseExited(propertyId, listener));
		return (T) this;
	}


	default T eventMouseMoved(final SuiEventListener<MouseMoveEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseMoved(listener));
		return (T) this;
	}


	default T eventMouseMoved(final String propertyId, final SuiEventListener<MouseMoveEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseMoved(propertyId, listener));
		return (T) this;
	}


	default T eventMouseScroll(final SuiEventListener<MouseScrollEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseScroll(listener));
		return (T) this;
	}


	default T eventMouseScroll(final String propertyId, final SuiEventListener<MouseScrollEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseScroll(propertyId, listener));
		return (T) this;
	}


	default T eventMouseScrollStarted(final SuiEventListener<MouseScrollEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseScrollStarted(listener));
		return (T) this;
	}


	default T eventMouseScrollStarted(final String propertyId, final SuiEventListener<MouseScrollEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseScrollStarted(propertyId, listener));
		return (T) this;
	}


	default T eventMouseScrollFinished(final SuiEventListener<MouseScrollEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseScrollFinished(listener));
		return (T) this;
	}


	default T eventMouseScrollFinished(final String propertyId, final SuiEventListener<MouseScrollEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventMouseScrollFinished(propertyId, listener));
		return (T) this;
	}

	default T eventFocusChanged(final SuiEventListener<FocusEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventFocusChanged(listener));
		return (T) this;
	}


	default T eventFocusChanged(final String propertyId, final SuiEventListener<FocusEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventFocusChanged(propertyId, listener));
		return (T) this;
	}

	default T eventFocusReceived(final SuiEventListener<FocusEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventFocusReceived(listener));
		return (T) this;
	}


	default T eventFocusReceived(final String propertyId, final SuiEventListener<FocusEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventFocusReceived(propertyId, listener));
		return (T) this;
	}


	default T eventFocusLost(final SuiEventListener<FocusEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventFocusLost(listener));
		return (T) this;
	}


	default T eventFocusLost(final String propertyId, final SuiEventListener<FocusEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventFocusLost(propertyId, listener));
		return (T) this;
	}


	default T eventHoverChanged(final SuiEventListener<HoverEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventHoverChanged(listener));
		return (T) this;
	}


	default T eventHoverChanged(final String propertyId, final SuiEventListener<HoverEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventHoverChanged(propertyId, listener));
		return (T) this;
	}


	default T eventHoverStarted(final SuiEventListener<HoverEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventHoverStarted(listener));
		return (T) this;
	}


	default T eventHoverStarted(final String propertyId, final SuiEventListener<HoverEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventHoverStarted(propertyId, listener));
		return (T) this;
	}


	default T eventHoverStopped(final SuiEventListener<HoverEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventHoverStopped(listener));
		return (T) this;
	}


	default T eventHoverStopped(final String propertyId, final SuiEventListener<HoverEventData> listener) {
		getFactoryInternalProperties().add(EventProperties.eventHoverStopped(propertyId, listener));
		return (T) this;
	}


}
