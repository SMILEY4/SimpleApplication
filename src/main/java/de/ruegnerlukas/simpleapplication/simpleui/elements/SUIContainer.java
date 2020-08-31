package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.SUIUtils;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.LayoutProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry.PropertyEntry;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.Optional;


public final class SUIContainer {


	/**
	 * Hidden constructor for utility classes.
	 */
	private SUIContainer() {
		// do nothing
	}




	/**
	 * Creates a new anchor-pane node.
	 *
	 * @param properties the properties
	 * @return the factory for an anchor-pane node
	 */
	public static NodeFactory container(final Property... properties) {
		Properties.validate(SUIContainer.class, SUIRegistry.get().getEntry(SUIContainer.class).getProperties(), properties);
		return state -> new SUINode(
				SUIContainer.class,
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

		registry.registerBaseFxNodeBuilder(SUIContainer.class, new FxNodeBuilder());
		registry.registerProperties(SUIContainer.class, PropertyGroups.commonProperties());
		registry.registerProperties(SUIContainer.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SUIContainer.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SUIContainer.class, List.of(
				PropertyEntry.of(ItemListProperty.class, new ItemListProperty.ItemListBuilder(), null),
				PropertyEntry.of(LayoutProperty.class, new LayoutProperty.LayoutUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements BaseFxNodeBuilder<Pane> {


		@Override
		public Pane build(final MasterNodeHandlers nodeHandlers, final SUINode node) {
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
