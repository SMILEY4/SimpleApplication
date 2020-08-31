package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.properties.TextContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.WrapTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnActionEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import javafx.scene.control.Button;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.get;

public final class SuiButton {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiButton() {
		// do nothing
	}




	/**
	 * Creates a new button node
	 *
	 * @param properties the properties
	 * @return the factory for a button node
	 */
	public static NodeFactory button(final Property... properties) {
		Properties.validate(SuiButton.class, get().getEntry(SuiButton.class).getProperties(), properties);
		return state -> new SuiNode(SuiButton.class, List.of(properties), state, null);
	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SuiRegistry registry) {
		registry.registerBaseFxNodeBuilder(SuiButton.class, new ButtonNodeBuilder());
		registry.registerProperties(SuiButton.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiButton.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiButton.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiButton.class, List.of(
				// labeled
				PropertyEntry.of(TextContentProperty.class, new TextContentProperty.TextContentUpdatingBuilder()),
				PropertyEntry.of(WrapTextProperty.class, new WrapTextProperty.WrapTextUpdatingBuilder()),
				PropertyEntry.of(AlignmentProperty.class, new AlignmentProperty.LabeledAlignmentUpdatingBuilder()),
				// button base
				PropertyEntry.of(OnActionEventProperty.class, new OnActionEventProperty.ButtonBaseUpdatingBuilder())
		));
	}




	private static class ButtonNodeBuilder implements BaseFxNodeBuilder<Button> {


		@Override
		public Button build(final MasterNodeHandlers nodeHandlers, final SuiNode node) {
			return new Button();
		}

	}


}
