package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.SUIState;
import de.ruegnerlukas.simpleapplication.simpleui.SUIUtils;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NoOpUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.AnchorProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.DisabledProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeMaxProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeMinProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizePreferredProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.StyleProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry.PropertyEntry;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;


public final class SUIAnchorPane {


	/**
	 * Hidden constructor for utility classes.
	 */
	private SUIAnchorPane() {
		// do nothing
	}




	/**
	 * Creates a new anchor-pane node.
	 *
	 * @param properties the properties
	 * @return the factory for an anchor-pane node
	 */
	public static NodeFactory anchorPane(final Property... properties) {
		Properties.checkIllegal(SUIAnchorPane.class, SUIRegistry.get().getEntry(SUIAnchorPane.class).getProperties(), properties);
		return state -> new SUINode(
				SUIAnchorPane.class,
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

		registry.registerBaseFxNodeBuilder(SUIAnchorPane.class, new AnchorPaneNodeBuilder());
		registry.registerProperties(SUIAnchorPane.class, List.of(
				// node
				PropertyEntry.of(DisabledProperty.class, new DisabledProperty.DisabledUpdatingBuilder()),
				PropertyEntry.of(StyleProperty.class, new StyleProperty.StyleUpdatingBuilder()),
				// region
				PropertyEntry.of(SizeMinProperty.class, new SizeMinProperty.SizeMinUpdatingBuilder()),
				PropertyEntry.of(SizePreferredProperty.class, new SizePreferredProperty.SizePreferredUpdatingBuilder()),
				PropertyEntry.of(SizeMaxProperty.class, new SizeMaxProperty.SizeMaxUpdatingBuilder()),
				PropertyEntry.of(SizeProperty.class, new SizeProperty.SizeUpdatingBuilder()),
				// special
				PropertyEntry.of(ItemListProperty.class, new ItemListProperty.ItemListBuilder(), null)
		));

		registry.registerBaseFxNodeBuilder(AnchorPaneChildItem.class, new ChildItem.ChildItemNodeBuilder());
		registry.registerProperties(AnchorPaneChildItem.class, List.of(
				PropertyEntry.of(ItemListProperty.class, new NoOpUpdatingBuilder()),
				PropertyEntry.of(AnchorProperty.class, new AnchorProperty.AnchorUpdatingBuilder())
		));
	}




	private static class AnchorPaneNodeBuilder implements BaseFxNodeBuilder<AnchorPane> {


		@Override
		public AnchorPane build(final MasterNodeHandlers nodeHandlers, final SUINode node) {
			return new AnchorPane();
		}

	}




	/**
	 * Creates a new anchor-pane child node
	 *
	 * @param properties the properties
	 * @return the factory for an anchor-pane child node
	 */
	public static NodeFactory anchorPaneItem(final NodeFactory factory, final Property... properties) {
		final List<Property> list = new ArrayList<>();
		list.add(new ItemListProperty(factory));
		list.addAll(List.of(properties));
		return state -> new AnchorPaneChildItem(list, state);
	}




	public static class AnchorPaneChildItem extends ChildItem {


		/**
		 * @param properties the properties
		 * @param state      the state
		 */
		public AnchorPaneChildItem(final List<Property> properties, final SUIState state) {
			super(AnchorPaneChildItem.class, properties, state, null);
		}


	}


}
