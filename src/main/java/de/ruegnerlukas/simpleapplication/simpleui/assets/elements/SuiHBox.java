package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.FitToHeightProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.FitToWidthProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SpacingProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNodeChildListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNodeChildTransformListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.scene.layout.HBox;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.get;

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
	public static NodeFactory hbox(final SuiProperty... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiHBox.class, get().getEntry(SuiHBox.class).getProperties(), properties);
		return state -> SuiNode.create(
				SuiHBox.class,
				List.of(properties),
				state,
				SuiNodeChildListener.DEFAULT,
				SuiNodeChildTransformListener.DEFAULT
				);
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




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<HBox> {


		@Override
		public HBox build(final SuiNode node) {
			return new HBox();
		}

	}


}
