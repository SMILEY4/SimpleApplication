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
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.AnchorProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.DisabledProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MutationBehaviourProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SizeMaxProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SizeMinProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SizePreferredProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SizeProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.StyleProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NoOpUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;

import java.util.List;

public final class PropertyGroups {


	/**
	 * Hidden constructor
	 */
	private PropertyGroups() {
		// Hidden constructor
	}




	/**
	 * Common properties for all elements.
	 */
	public static List<PropertyEntry> commonProperties() {
		return List.of(
				PropertyEntry.of(MutationBehaviourProperty.class, new NoOpUpdatingBuilder()),
				PropertyEntry.of(DisabledProperty.class, new DisabledProperty.UpdatingBuilder()),
				PropertyEntry.of(StyleProperty.class, new StyleProperty.UpdatingBuilder()),
				PropertyEntry.of(AnchorProperty.class, new AnchorProperty.UpdatingBuilder())
		);
	}




	/**
	 * Common event properties for all elements.
	 */
	public static List<PropertyEntry> commonEventProperties() {
		return List.of(
				PropertyEntry.of(OnKeyPressedEventProperty.class, new OnKeyPressedEventProperty.UpdatingBuilder()),
				PropertyEntry.of(OnKeyReleasedEventProperty.class, new OnKeyReleasedEventProperty.UpdatingBuilder()),
				PropertyEntry.of(OnKeyTypedEventProperty.class, new OnKeyTypedEventProperty.UpdatingBuilder()),

				PropertyEntry.of(OnMouseClickedEventProperty.class, new OnMouseClickedEventProperty.UpdatingBuilder()),
				PropertyEntry.of(OnMousePressedEventProperty.class, new OnMousePressedEventProperty.UpdatingBuilder()),
				PropertyEntry.of(OnMouseReleasedEventProperty.class, new OnMouseReleasedEventProperty.UpdatingBuilder()),

				PropertyEntry.of(OnMouseDragEnteredEventProperty.class, new OnMouseDragEnteredEventProperty.UpdatingBuilder()),
				PropertyEntry.of(OnMouseDragExitedEventProperty.class, new OnMouseDragExitedEventProperty.UpdatingBuilder()),
				PropertyEntry.of(OnMouseDraggedEventProperty.class, new OnMouseDraggedEventProperty.UpdatingBuilder()),
				PropertyEntry.of(OnMouseDragOverEventProperty.class, new OnMouseDragOverEventProperty.UpdatingBuilder()),
				PropertyEntry.of(OnMouseDragReleasedEventProperty.class, new OnMouseDragReleasedEventProperty.UpdatingBuilder()),

				PropertyEntry.of(OnMouseEnteredEventProperty.class, new OnMouseEnteredEventProperty.UpdatingBuilder()),
				PropertyEntry.of(OnMouseExitedEventProperty.class, new OnMouseExitedEventProperty.UpdatingBuilder()),
				PropertyEntry.of(OnMouseMovedEventProperty.class, new OnMouseMovedEventProperty.UpdatingBuilder()),

				PropertyEntry.of(OnMouseScrollEventProperty.class, new OnMouseScrollEventProperty.UpdatingBuilder()),
				PropertyEntry.of(OnMouseScrollStartedEventProperty.class, new OnMouseScrollStartedEventProperty.UpdatingBuilder()),
				PropertyEntry.of(OnMouseScrollFinishedEventProperty.class, new OnMouseScrollFinishedEventProperty.UpdatingBuilder()),

				PropertyEntry.of(OnFocusChangedEventProperty.class, new OnFocusChangedEventProperty.UpdatingBuilder()),
				PropertyEntry.of(OnFocusReceivedEventProperty.class, new OnFocusReceivedEventProperty.UpdatingBuilder()),
				PropertyEntry.of(OnFocusLostEventProperty.class, new OnFocusLostEventProperty.UpdatingBuilder()),

				PropertyEntry.of(OnHoverChangedEventProperty.class, new OnHoverChangedEventProperty.UpdatingBuilder()),
				PropertyEntry.of(OnHoverStartedEventProperty.class, new OnHoverStartedEventProperty.UpdatingBuilder()),
				PropertyEntry.of(OnHoverStoppedEventProperty.class, new OnHoverStoppedEventProperty.UpdatingBuilder())
		);
	}




	/**
	 * Common properties for all region elements (that produce a {@link javafx.scene.layout.Region}).
	 */
	public static List<PropertyEntry> commonRegionProperties() {
		return List.of(
				PropertyEntry.of(SizeMinProperty.class, new SizeMinProperty.RegionUpdatingBuilder()),
				PropertyEntry.of(SizePreferredProperty.class, new SizePreferredProperty.RegionUpdatingBuilder()),
				PropertyEntry.of(SizeMaxProperty.class, new SizeMaxProperty.RegionUpdatingBuilder()),
				PropertyEntry.of(SizeProperty.class, new SizeProperty.RegionUpdatingBuilder())
		);
	}


}
