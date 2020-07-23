package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.DisabledProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.FitToHeightProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.FitToWidthProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ShowScrollbarsProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeMaxProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeMinProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizePreferredProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import javafx.scene.control.ScrollPane;

import java.util.List;

public final class SUIScrollPane {


	/**
	 * Hidden constructor for utility classes
	 */
	private SUIScrollPane() {
		// do nothing
	}




	/**
	 * Creates a new scroll-pane node
	 *
	 * @param properties the properties
	 * @return the factory for an scroll-pane node
	 */
	public static NodeFactory scrollPane(final Property... properties) {
		Properties.checkIllegal(SUIScrollPane.class, SUIRegistry.get().getEntry(SUIScrollPane.class).getProperties(), properties);
		return state -> new SUINode(SUIScrollPane.class, List.of(properties), state, SUIScrollPane::handleChildrenChange);
	}




	/**
	 * Handle a change in the child nodes of the given scroll-pane node.
	 *
	 * @param node the scroll-pane node
	 */
	private static void handleChildrenChange(final SUINode node) {
		final ScrollPane scrollPane = (ScrollPane) node.getFxNode();
		if (node.getChildren().isEmpty()) {
			scrollPane.setContent(null);
		} else {
			scrollPane.setContent(node.getChildren().get(0).getFxNode());
		}
	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SUIRegistry registry) {
		registry.registerBaseFxNodeBuilder(SUIScrollPane.class, new SUIScrollPane.ScrollPaneNodeBuilder());
		registry.registerProperty(SUIScrollPane.class, SizeMinProperty.class, new SizeMinProperty.SizeMinUpdatingBuilder());
		registry.registerProperty(SUIScrollPane.class, SizePreferredProperty.class,
				new SizePreferredProperty.SizePreferredUpdatingBuilder());
		registry.registerProperty(SUIScrollPane.class, SizeMaxProperty.class, new SizeMaxProperty.SizeMaxUpdatingBuilder());
		registry.registerProperty(SUIScrollPane.class, SizeProperty.class, new SizeProperty.SizeUpdatingBuilder());
		registry.registerProperty(SUIScrollPane.class, DisabledProperty.class, new DisabledProperty.DisabledUpdatingBuilder());
		registry.registerProperty(SUIScrollPane.class, FitToWidthProperty.class,
				new FitToWidthProperty.ScrollPaneFitToWidthUpdatingBuilder());
		registry.registerProperty(SUIScrollPane.class, FitToHeightProperty.class,
				new FitToHeightProperty.FitToHeightUpdatingBuilder());
		registry.registerProperty(SUIScrollPane.class, ShowScrollbarsProperty.class,
				new ShowScrollbarsProperty.ShowScrollbarUpdatingBuilder());
		registry.registerProperty(SUIScrollPane.class, ItemProperty.class, new ItemProperty.ScrollPaneContentBuilder());
	}




	private static class ScrollPaneNodeBuilder implements BaseFxNodeBuilder<ScrollPane> {


		@Override
		public ScrollPane build(final MasterNodeHandlers nodeHandlers, final SUINode node) {
			return new ScrollPane();
		}

	}


}
