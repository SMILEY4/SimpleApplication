package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.FactoryExtension;

public interface CommonEventBuilderExtension<T extends FactoryExtension> extends FactoryExtension,
		OnKeyPressedEventProperty.PropertyBuilderExtension<T>,
		OnKeyReleasedEventProperty.PropertyBuilderExtension<T>,
		OnKeyTypedEventProperty.PropertyBuilderExtension<T>,
		OnMousePressedEventProperty.PropertyBuilderExtension<T>,
		OnMouseReleasedEventProperty.PropertyBuilderExtension<T>,
		OnMouseDragEnteredEventProperty.PropertyBuilderExtension<T>,
		OnMouseDragExitedEventProperty.PropertyBuilderExtension<T>,
		OnMouseDraggedEventProperty.PropertyBuilderExtension<T>,
		OnMouseDragReleasedEventProperty.PropertyBuilderExtension<T>,
		OnMouseEnteredEventProperty.PropertyBuilderExtension<T>,
		OnMouseExitedEventProperty.PropertyBuilderExtension<T>,
		OnMouseMovedEventProperty.PropertyBuilderExtension<T>,
		OnMouseScrollEventProperty.PropertyBuilderExtension<T>,
		OnMouseScrollStartedEventProperty.PropertyBuilderExtension<T>,
		OnMouseScrollFinishedEventProperty.PropertyBuilderExtension<T>,
		OnFocusChangedEventProperty.PropertyBuilderExtension<T>,
		OnFocusReceivedEventProperty.PropertyBuilderExtension<T>,
		OnFocusLostEventProperty.PropertyBuilderExtension<T>,
		OnHoverChangedEventProperty.PropertyBuilderExtension<T>,
		OnHoverStartedEventProperty.PropertyBuilderExtension<T>,
		OnHoverStoppedEventProperty.PropertyBuilderExtension<T> {


}
