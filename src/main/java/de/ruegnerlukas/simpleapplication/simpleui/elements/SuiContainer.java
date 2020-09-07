package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiBaseNode;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNodeChildListener;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNodeChildTransformListener;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.LayoutProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.PropertyEntry;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.Optional;


public final class SuiContainer {


	/**
	 * Hidden constructor for utility classes.
	 */
	private SuiContainer() {
		// do nothing
	}




	/**
	 * Creates a new anchor-pane node.
	 *
	 * @param properties the properties
	 * @return the factory for an anchor-pane node
	 */
	public static NodeFactory container(final Property... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiContainer.class, SuiRegistry.get().getEntry(SuiContainer.class).getProperties(), properties);
		return state -> SuiBaseNode.create(
				SuiContainer.class,
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

		registry.registerBaseFxNodeBuilder(SuiContainer.class, new FxNodeBuilder());
		registry.registerProperties(SuiContainer.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiContainer.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiContainer.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiContainer.class, List.of(
				PropertyEntry.of(ItemListProperty.class, new ItemListProperty.Builder(), null),
				PropertyEntry.of(LayoutProperty.class, new LayoutProperty.UpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements BaseFxNodeBuilder<Pane> {


		@Override
		public Pane build(final MasterNodeHandlers nodeHandlers, final SuiNode node) {
			return new Pane() {
				@Override
				protected void layoutChildren() {
					final Optional<LayoutProperty> property = node.getPropertySafe(LayoutProperty.class);
					if (property.isPresent()) {
						property.get().getLayoutFunction().layout(this, this.getChildrenUnmodifiable());
					} else {
						super.layoutChildren();
					}
				}
			};
		}

	}


}
