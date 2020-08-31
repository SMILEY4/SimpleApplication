package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.FitToHeightProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.FitToWidthProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SpacingProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.utils.SuiUtils;
import javafx.scene.layout.HBox;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.get;

public final class SuiHBox {


	/**
	 * Hidden constructor for utility classes.
	 */
	private SuiHBox() {
		// do nothing
	}




	/**
	 * Creates a new hbox node.
	 *
	 * @param properties the properties
	 * @return the factory for a hbox node
	 */
	public static NodeFactory hbox(final Property... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiHBox.class, get().getEntry(SuiHBox.class).getProperties(), properties);
		return state -> new SuiNode(
				SuiHBox.class,
				List.of(properties),
				state,
				SuiUtils.defaultPaneChildListener(),
				SuiUtils.defaultPaneChildTransformListener());
	}





	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SuiRegistry registry) {
		registry.registerBaseFxNodeBuilder(SuiHBox.class, new FxNodeBuilder());
		registry.registerProperties(SuiHBox.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiHBox.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiHBox.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiHBox.class, List.of(
				PropertyEntry.of(FitToWidthProperty.class, new FitToHeightProperty.HBoxUpdatingBuilder()),
				PropertyEntry.of(SpacingProperty.class, new SpacingProperty.HBoxUpdatingBuilder()),
				PropertyEntry.of(AlignmentProperty.class, new AlignmentProperty.HBoxUpdatingBuilder()),
				PropertyEntry.of(ItemListProperty.class, new ItemListProperty.Builder(), null)
		));
	}




	private static class FxNodeBuilder implements BaseFxNodeBuilder<HBox> {


		@Override
		public HBox build(final MasterNodeHandlers nodeHandlers, final SuiNode node) {
			return new HBox();
		}

	}


}
