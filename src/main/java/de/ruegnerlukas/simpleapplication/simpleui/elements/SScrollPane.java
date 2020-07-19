package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.SimpleUIRegistry;
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
import javafx.scene.control.ScrollPane;

import java.util.List;

public final class SScrollPane {


	/**
	 * Hidden constructor for utility classes
	 */
	private SScrollPane() {
		// do nothing
	}




	/**
	 * Creates a new scroll-pane node
	 *
	 * @param properties the properties
	 * @return the factory for an scroll-pane node
	 */
	public static NodeFactory scrollPane(final Property... properties) {
		Properties.checkIllegal(SScrollPane.class, SimpleUIRegistry.get().getEntry(SScrollPane.class).getProperties(), properties);
		return state -> new SNode(SScrollPane.class, List.of(properties), state, SScrollPane::handleChildrenChange);
	}




	/**
	 * Handle a change in the child nodes of the given scroll-pane node.
	 *
	 * @param node the scroll-pane node
	 */
	private static void handleChildrenChange(final SNode node) {
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
	public static void register(final SimpleUIRegistry registry) {
		registry.registerBaseFxNodeBuilder(SScrollPane.class, new SScrollPane.ScrollPaneNodeBuilder());
		registry.registerProperty(SScrollPane.class, SizeMinProperty.class, new SizeMinProperty.SizeMinUpdatingBuilder());
		registry.registerProperty(SScrollPane.class, SizePreferredProperty.class,
				new SizePreferredProperty.SizePreferredUpdatingBuilder());
		registry.registerProperty(SScrollPane.class, SizeMaxProperty.class, new SizeMaxProperty.SizeMaxUpdatingBuilder());
		registry.registerProperty(SScrollPane.class, SizeProperty.class, new SizeProperty.SizeUpdatingBuilder());
		registry.registerProperty(SScrollPane.class, DisabledProperty.class, new DisabledProperty.DisabledUpdatingBuilder());
		registry.registerProperty(SScrollPane.class, FitToWidthProperty.class,
				new FitToWidthProperty.ScrollPaneFitToWidthUpdatingBuilder());
		registry.registerProperty(SScrollPane.class, FitToHeightProperty.class,
				new FitToHeightProperty.FitToHeightUpdatingBuilder());
		registry.registerProperty(SScrollPane.class, ShowScrollbarsProperty.class,
				new ShowScrollbarsProperty.ShowScrollbarUpdatingBuilder());
		registry.registerProperty(SScrollPane.class, ItemProperty.class, new ItemProperty.ScrollPaneContentBuilder());
	}




	private static class ScrollPaneNodeBuilder implements BaseFxNodeBuilder<ScrollPane> {


		@Override
		public ScrollPane build(final SceneContext context, final SNode node) {
			return new ScrollPane();
		}

	}


}
