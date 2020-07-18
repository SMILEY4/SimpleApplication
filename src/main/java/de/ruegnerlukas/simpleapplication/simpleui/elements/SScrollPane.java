package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.SimpleUIRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.*;
import javafx.scene.control.ScrollPane;

import java.util.List;

public class SScrollPane {


	public static NodeFactory scrollPane(final Property... properties) {
		Properties.checkIllegal(SScrollPane.class, SimpleUIRegistry.get().getEntry(SScrollPane.class).getProperties(), properties);
		return state -> new SNode(SScrollPane.class, List.of(properties), state, SScrollPane::handleChildrenChange);
	}




	private static void handleChildrenChange(SNode node) {
		final ScrollPane scrollPane = (ScrollPane) node.getFxNode();
		scrollPane.setContent(node.getChildren().get(0).getFxNode());
	}




	public static void register(final SimpleUIRegistry registry) {
		registry.registerBaseFxNodeBuilder(SScrollPane.class, new SScrollPane.ScrollPaneNodeBuilder());
		registry.registerProperty(SScrollPane.class, SizeMinProperty.class, new SizeMinProperty.SizeMinUpdatingBuilder());
		registry.registerProperty(SScrollPane.class, SizePreferredProperty.class, new SizePreferredProperty.SizePreferredUpdatingBuilder());
		registry.registerProperty(SScrollPane.class, SizeMaxProperty.class, new SizeMaxProperty.SizeMaxUpdatingBuilder());
		registry.registerProperty(SScrollPane.class, SizeProperty.class, new SizeProperty.SizeUpdatingBuilder());
		registry.registerProperty(SScrollPane.class, ItemListProperty.class, new ItemListProperty.ItemListBuilder());
		registry.registerProperty(SScrollPane.class, DisabledProperty.class, new DisabledProperty.DisabledUpdatingBuilder());
		registry.registerProperty(SScrollPane.class, FitToWidthProperty.class, new FitToWidthProperty.ScrollPaneFitToWidthUpdatingBuilder());
		registry.registerProperty(SScrollPane.class, FitToHeightProperty.class, new FitToHeightProperty.FitToHeightUpdatingBuilder());
		registry.registerProperty(SScrollPane.class, ShowScrollbarsProperty.class, new ShowScrollbarsProperty.ShowScrollbarUpdatingBuilder());
		registry.registerProperty(SScrollPane.class, ItemProperty.class, new ItemProperty.ScrollPaneContentBuilder());
	}




	private static class ScrollPaneNodeBuilder implements BaseFxNodeBuilder<ScrollPane> {


		@Override
		public ScrollPane build(final SceneContext context, final SNode node) {
			return new ScrollPane();
		}

	}



}
