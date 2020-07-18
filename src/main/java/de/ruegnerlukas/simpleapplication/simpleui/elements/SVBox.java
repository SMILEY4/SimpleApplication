package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.SimpleUIRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.*;
import javafx.scene.layout.VBox;

import java.util.List;

public class SVBox {


	public static NodeFactory vbox(final Property... properties) {
		Properties.checkIllegal(SVBox.class, SimpleUIRegistry.get().getEntry(SVBox.class).getProperties(), properties);
		return state -> new SNode(SVBox.class, List.of(properties), state, SVBox::handleChildrenChange);
	}




	private static void handleChildrenChange(SNode node) {
		final VBox vbox = (VBox) node.getFxNode();
		vbox.getChildren().clear();
		node.getChildren().forEach(child -> {
			vbox.getChildren().add(child.getFxNode());
		});
	}




	public static void register(final SimpleUIRegistry registry) {
		registry.registerBaseFxNodeBuilder(SVBox.class, new SVBox.VBoxNodeBuilder());
		registry.registerProperty(SVBox.class, SizeMinProperty.class, new SizeMinProperty.SizeMinUpdatingBuilder());
		registry.registerProperty(SVBox.class, SizePreferredProperty.class, new SizePreferredProperty.SizePreferredUpdatingBuilder());
		registry.registerProperty(SVBox.class, SizeMaxProperty.class, new SizeMaxProperty.SizeMaxUpdatingBuilder());
		registry.registerProperty(SVBox.class, SizeProperty.class, new SizeProperty.SizeUpdatingBuilder());
		registry.registerProperty(SVBox.class, DisabledProperty.class, new DisabledProperty.DisabledUpdatingBuilder());
		registry.registerProperty(SVBox.class, ItemListProperty.class, new ItemListProperty.ItemListBuilder());
		registry.registerProperty(SVBox.class, SpacingProperty.class, new SpacingProperty.VBoxSpacingUpdatingBuilder());
		registry.registerProperty(SVBox.class, AlignmentProperty.class, new AlignmentProperty.VBoxAlignmentUpdatingBuilder());
		registry.registerProperty(SVBox.class, FitToWidthProperty.class, new FitToWidthProperty.VBoxFitToWidthUpdatingBuilder());
	}




	private static class VBoxNodeBuilder implements BaseFxNodeBuilder<VBox> {


		@Override
		public VBox build(final SceneContext context, final SNode node) {
			return new VBox();
		}

	}


}
