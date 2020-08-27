package de.ruegnerlukas.simpleapplication.simpleui.elements;

import de.ruegnerlukas.simpleapplication.simpleui.builders.NoOpUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.properties.DisabledProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.MutationBehaviourProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeMaxProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeMinProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizePreferredProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.StyleProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnKeyPressedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnKeyReleasedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnKeyTypedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnMouseClickedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnMouseDragEnteredEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnMouseDragExitedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnMouseDragOverEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnMouseDragReleasedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnMouseDraggedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnMouseEnteredEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnMouseExitedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnMouseMovedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnMousePressedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnMouseReleasedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnScrollEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnScrollFinishedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnScrollStartedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry.PropertyEntry;

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
				PropertyEntry.of(DisabledProperty.class, new DisabledProperty.DisabledUpdatingBuilder()),
				PropertyEntry.of(StyleProperty.class, new StyleProperty.StyleUpdatingBuilder())
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

				PropertyEntry.of(OnScrollEventProperty.class, new OnScrollEventProperty.UpdatingBuilder()),
				PropertyEntry.of(OnScrollStartedEventProperty.class, new OnScrollStartedEventProperty.UpdatingBuilder()),
				PropertyEntry.of(OnScrollFinishedEventProperty.class, new OnScrollFinishedEventProperty.UpdatingBuilder())
		);
	}




	/**
	 * Common properties for all region elements (that produce a {@link javafx.scene.layout.Region}).
	 */
	public static List<PropertyEntry> commonRegionProperties() {
		return List.of(
				PropertyEntry.of(SizeMinProperty.class, new SizeMinProperty.SizeMinUpdatingBuilder()),
				PropertyEntry.of(SizePreferredProperty.class, new SizePreferredProperty.SizePreferredUpdatingBuilder()),
				PropertyEntry.of(SizeMaxProperty.class, new SizeMaxProperty.SizeMaxUpdatingBuilder()),
				PropertyEntry.of(SizeProperty.class, new SizeProperty.SizeUpdatingBuilder())
		);
	}


}
