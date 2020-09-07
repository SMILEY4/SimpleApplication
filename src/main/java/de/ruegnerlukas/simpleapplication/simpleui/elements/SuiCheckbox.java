package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.properties.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.properties.TextContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.WrapTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnActionEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnCheckedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import javafx.scene.control.CheckBox;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.get;

public final class SuiCheckbox {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiCheckbox() {
		// do nothing
	}




	/**
	 * Creates a new checkbox node
	 *
	 * @param properties the properties
	 * @return the factory for a checkbox node
	 */
	public static NodeFactory checkbox(final Property... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiCheckbox.class, get().getEntry(SuiCheckbox.class).getProperties(), properties);
		return state -> new SuiNode(SuiCheckbox.class, List.of(properties), state, null);
	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SuiRegistry registry) {
		registry.registerBaseFxNodeBuilder(SuiCheckbox.class, new FxNodeBuilder());
		registry.registerProperties(SuiCheckbox.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiCheckbox.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiCheckbox.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiCheckbox.class, List.of(
				PropertyEntry.of(TextContentProperty.class, new TextContentProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(WrapTextProperty.class, new WrapTextProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(AlignmentProperty.class, new AlignmentProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(OnActionEventProperty.class, new OnActionEventProperty.ButtonBaseUpdatingBuilder()),
				PropertyEntry.of(OnCheckedEventProperty.class, new OnCheckedEventProperty.CheckboxUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements BaseFxNodeBuilder<CheckBox> {


		@Override
		public CheckBox build(final MasterNodeHandlers nodeHandlers, final SuiNode node) {
			return new CheckBox();
		}

	}


}
