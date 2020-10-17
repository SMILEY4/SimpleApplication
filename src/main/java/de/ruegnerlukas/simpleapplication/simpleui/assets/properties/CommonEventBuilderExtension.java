package de.ruegnerlukas.simpleapplication.simpleui.assets.properties;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnFocusChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnFocusLostEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnFocusReceivedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnHoverChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnHoverStartedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnHoverStoppedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnKeyPressedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnKeyReleasedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnKeyTypedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnMouseDragEnteredEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnMouseDragExitedEventProperty;
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
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;

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
