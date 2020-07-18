package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.SimpleUIRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.State;
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
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;

public class SAnchorPane {


	public static NodeFactory anchorPane(final Property... properties) {
		Properties.checkIllegal(SAnchorPane.class, SimpleUIRegistry.get().getEntry(SAnchorPane.class).getProperties(), properties);
		return state -> new SNode(SAnchorPane.class, List.of(properties), state, SAnchorPane::handleChildrenChange);
	}




	private static void handleChildrenChange(SNode node) {
		final AnchorPane anchorPane = (AnchorPane) node.getFxNode();
		anchorPane.getChildren().clear();
		node.getChildren().forEach(child -> {
			anchorPane.getChildren().add(child.getFxNode());
		});
	}




	public static void register(final SimpleUIRegistry registry) {
		registry.registerBaseFxNodeBuilder(SAnchorPane.class, new AnchorPaneNodeBuilder());
		registry.registerProperty(SAnchorPane.class, SizeMinProperty.class, new SizeMinProperty.SizeMinUpdatingBuilder());
		registry.registerProperty(SAnchorPane.class, SizePreferredProperty.class, new SizePreferredProperty.SizePreferredUpdatingBuilder());
		registry.registerProperty(SAnchorPane.class, SizeMaxProperty.class, new SizeMaxProperty.SizeMaxUpdatingBuilder());
		registry.registerProperty(SAnchorPane.class, SizeProperty.class, new SizeProperty.SizeUpdatingBuilder());
		registry.registerProperty(SAnchorPane.class, DisabledProperty.class, new DisabledProperty.DisabledUpdatingBuilder());
		registry.registerProperty(SAnchorPane.class, ItemListProperty.class, new ItemListProperty.ItemListBuilder());

		registry.registerBaseFxNodeBuilder(AnchorPaneChildItem.class, new ChildItem.ChildItemNodeBuilder());
		registry.registerProperty(AnchorPaneChildItem.class, ItemListProperty.class, new NoOpUpdatingBuilder());
		registry.registerProperty(AnchorPaneChildItem.class, AnchorProperty.class, new AnchorProperty.AnchorUpdatingBuilder());
	}




	private static class AnchorPaneNodeBuilder implements BaseFxNodeBuilder<AnchorPane> {


		@Override
		public AnchorPane build(final SceneContext context, final SNode node) {
			return new AnchorPane();
		}

	}




	public static NodeFactory anchorPaneItem(final NodeFactory factory, final Property... properties) {
		final List<Property> list = new ArrayList<>();
		list.add(new ItemListProperty(factory));
		list.addAll(List.of(properties));
		return state -> new AnchorPaneChildItem(list, state);
	}




	public static class AnchorPaneChildItem extends ChildItem {


		public AnchorPaneChildItem(final List<Property> properties, final State state) {
			super(AnchorPaneChildItem.class, properties, state, null);
		}


	}


}
