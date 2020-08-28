package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SelectedProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.TextContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.WrapTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnActionEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import javafx.scene.control.CheckBox;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry.get;

public final class SUICheckbox {


	/**
	 * Hidden constructor for utility classes
	 */
	private SUICheckbox() {
		// do nothing
	}




	/**
	 * Creates a new checkbox node
	 *
	 * @param properties the properties
	 * @return the factory for a checkbox node
	 */
	public static NodeFactory checkbox(final Property... properties) {
		Properties.checkIllegal(SUICheckbox.class, get().getEntry(SUICheckbox.class).getProperties(), properties);
		return state -> new SUINode(SUICheckbox.class, List.of(properties), state, null);
	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SUIRegistry registry) {
		registry.registerBaseFxNodeBuilder(SUICheckbox.class, new CheckboxNodeBuilder());
		registry.registerProperties(SUICheckbox.class, PropertyGroups.commonProperties());
		registry.registerProperties(SUICheckbox.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SUICheckbox.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SUICheckbox.class, List.of(
				// labeled
				PropertyEntry.of(TextContentProperty.class, new TextContentProperty.TextContentUpdatingBuilder()),
				PropertyEntry.of(WrapTextProperty.class, new WrapTextProperty.WrapTextUpdatingBuilder()),
				PropertyEntry.of(AlignmentProperty.class, new AlignmentProperty.LabeledAlignmentUpdatingBuilder()),
				// button base
				PropertyEntry.of(OnActionEventProperty.class, new OnActionEventProperty.ButtonBaseUpdatingBuilder()),
				// special
				PropertyEntry.of(SelectedProperty.class, new SelectedProperty.SelectedPropertyUpdatingBuilder())
		));
	}




	private static class CheckboxNodeBuilder implements BaseFxNodeBuilder<CheckBox> {


		@Override
		public CheckBox build(final MasterNodeHandlers nodeHandlers, final SUINode node) {
			return new CheckBox();
		}

	}


}
