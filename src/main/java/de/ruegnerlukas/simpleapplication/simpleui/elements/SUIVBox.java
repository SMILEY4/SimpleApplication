package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.SUIUtils;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.FitToWidthProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SpacingProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import javafx.scene.layout.VBox;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry.get;

public final class SUIVBox {


	/**
	 * Hidden constructor for utility classes.
	 */
	private SUIVBox() {
		// do nothing
	}




	/**
	 * Creates a new vbox node.
	 *
	 * @param properties the properties
	 * @return the factory for a vbox node
	 */
	public static NodeFactory vbox(final Property... properties) {
		Properties.validate(SUIVBox.class, get().getEntry(SUIVBox.class).getProperties(), properties);
		return state -> new SUINode(
				SUIVBox.class,
				List.of(properties),
				state,
				SUIUtils.defaultPaneChildListener(),
				SUIUtils.defaultPaneChildTransformListener());
	}







	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SUIRegistry registry) {
		registry.registerBaseFxNodeBuilder(SUIVBox.class, new SUIVBox.VBoxNodeBuilder());
		registry.registerProperties(SUIVBox.class, PropertyGroups.commonProperties());
		registry.registerProperties(SUIVBox.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SUIVBox.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SUIVBox.class, List.of(
				PropertyEntry.of(FitToWidthProperty.class, new FitToWidthProperty.VBoxFitToWidthUpdatingBuilder()),
				PropertyEntry.of(SpacingProperty.class, new SpacingProperty.VBoxSpacingUpdatingBuilder()),
				PropertyEntry.of(AlignmentProperty.class, new AlignmentProperty.VBoxAlignmentUpdatingBuilder()),
				PropertyEntry.of(ItemListProperty.class, new ItemListProperty.ItemListBuilder(), null)
		));
	}








	private static class VBoxNodeBuilder implements BaseFxNodeBuilder<VBox> {


		@Override
		public VBox build(final MasterNodeHandlers nodeHandlers, final SUINode node) {
			return new VBox();
		}

	}


}
