package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.SUIUtils;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.FitToHeightProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.FitToWidthProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SpacingProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import javafx.scene.layout.HBox;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry.get;

public final class SUIHBox {


	/**
	 * Hidden constructor for utility classes.
	 */
	private SUIHBox() {
		// do nothing
	}




	/**
	 * Creates a new hbox node.
	 *
	 * @param properties the properties
	 * @return the factory for a hbox node
	 */
	public static NodeFactory hbox(final Property... properties) {
		Properties.checkIllegal(SUIHBox.class, get().getEntry(SUIHBox.class).getProperties(), properties);
		return state -> new SUINode(
				SUIHBox.class,
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
		registry.registerBaseFxNodeBuilder(SUIHBox.class, new SUIHBox.HBoxNodeBuilder());
		registry.registerProperties(SUIHBox.class, PropertyGroups.commonProperties());
		registry.registerProperties(SUIHBox.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SUIHBox.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SUIHBox.class, List.of(
				PropertyEntry.of(FitToWidthProperty.class, new FitToHeightProperty.HBoxFitToHeightUpdatingBuilder()),
				PropertyEntry.of(SpacingProperty.class, new SpacingProperty.HBoxSpacingUpdatingBuilder()),
				PropertyEntry.of(AlignmentProperty.class, new AlignmentProperty.HBoxAlignmentUpdatingBuilder()),
				PropertyEntry.of(ItemListProperty.class, new ItemListProperty.ItemListBuilder(), null)
		));
	}




	private static class HBoxNodeBuilder implements BaseFxNodeBuilder<HBox> {


		@Override
		public HBox build(final MasterNodeHandlers nodeHandlers, final SUINode node) {
			return new HBox();
		}

	}


}
