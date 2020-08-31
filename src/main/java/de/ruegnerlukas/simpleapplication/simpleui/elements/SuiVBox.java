package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.utils.SuiUtils;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.FitToWidthProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SpacingProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import javafx.scene.layout.VBox;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.get;

public final class SuiVBox {


	/**
	 * Hidden constructor for utility classes.
	 */
	private SuiVBox() {
		// do nothing
	}




	/**
	 * Creates a new vbox node.
	 *
	 * @param properties the properties
	 * @return the factory for a vbox node
	 */
	public static NodeFactory vbox(final Property... properties) {
		Properties.validate(SuiVBox.class, get().getEntry(SuiVBox.class).getProperties(), properties);
		return state -> new SuiNode(
				SuiVBox.class,
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
		registry.registerBaseFxNodeBuilder(SuiVBox.class, new FxNodeBuilder());
		registry.registerProperties(SuiVBox.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiVBox.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiVBox.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiVBox.class, List.of(
				PropertyEntry.of(FitToWidthProperty.class, new FitToWidthProperty.VBoxUpdatingBuilder()),
				PropertyEntry.of(SpacingProperty.class, new SpacingProperty.VBoxUpdatingBuilder()),
				PropertyEntry.of(AlignmentProperty.class, new AlignmentProperty.VBoxUpdatingBuilder()),
				PropertyEntry.of(ItemListProperty.class, new ItemListProperty.Builder(), null)
		));
	}








	private static class FxNodeBuilder implements BaseFxNodeBuilder<VBox> {


		@Override
		public VBox build(final MasterNodeHandlers nodeHandlers, final SuiNode node) {
			return new VBox();
		}

	}


}
