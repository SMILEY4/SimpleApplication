package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.SUIState;
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
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
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
		return state -> new SUINode(SUIAnchorPane.class, List.of(properties), state, SUIAnchorPane::handleChildrenChange);
	}




	/**
	 * Handle a change in the child nodes of the given anchor-pane node.
	 *
	 * @param node the anchor-pane node
	 */
	private static void handleChildrenChange(final SUINode node) {
		final AnchorPane anchorPane = (AnchorPane) node.getFxNode();
		anchorPane.getChildren().clear();
		node.getChildren().forEach(child -> {
			anchorPane.getChildren().add(child.getFxNode());
		});
	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SUIRegistry registry) {
		registry.registerBaseFxNodeBuilder(SUIAnchorPane.class, new AnchorPaneNodeBuilder());
		registry.registerProperty(SUIAnchorPane.class, SizeMinProperty.class, new SizeMinProperty.SizeMinUpdatingBuilder());
		registry.registerProperty(SUIAnchorPane.class, SizePreferredProperty.class,
				new SizePreferredProperty.SizePreferredUpdatingBuilder());
		registry.registerProperty(SUIAnchorPane.class, SizeMaxProperty.class, new SizeMaxProperty.SizeMaxUpdatingBuilder());
		registry.registerProperty(SUIAnchorPane.class, SizeProperty.class, new SizeProperty.SizeUpdatingBuilder());
		registry.registerProperty(SUIAnchorPane.class, DisabledProperty.class, new DisabledProperty.DisabledUpdatingBuilder());
		registry.registerProperty(SUIAnchorPane.class, ItemListProperty.class, new ItemListProperty.ItemListBuilder());

		registry.registerBaseFxNodeBuilder(AnchorPaneChildItem.class, new ChildItem.ChildItemNodeBuilder());
		registry.registerProperty(AnchorPaneChildItem.class, ItemListProperty.class, new NoOpUpdatingBuilder());
		registry.registerProperty(AnchorPaneChildItem.class, AnchorProperty.class, new AnchorProperty.AnchorUpdatingBuilder());
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
