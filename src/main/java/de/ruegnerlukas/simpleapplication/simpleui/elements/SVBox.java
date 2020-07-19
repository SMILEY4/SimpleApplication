package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.SimpleUIRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.DisabledProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.FitToWidthProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeMaxProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeMinProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizePreferredProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SpacingProperty;
import javafx.scene.layout.VBox;

import java.util.List;

public final class SVBox {


	/**
	 * Hidden constructor for utility classes.
	 */
	private SVBox() {
		// do nothing
	}




	/**
	 * Creates a new vbox node.
	 *
	 * @param properties the properties
	 * @return the factory for a vbox node
	 */
	public static NodeFactory vbox(final Property... properties) {
		Properties.checkIllegal(SVBox.class, SimpleUIRegistry.get().getEntry(SVBox.class).getProperties(), properties);
		return state -> new SNode(SVBox.class, List.of(properties), state, SVBox::handleChildrenChange);
	}




	/**
	 * Handle a change in the child nodes of the given vbox node.
	 *
	 * @param node the vbox node
	 */
	private static void handleChildrenChange(final SNode node) {
		final VBox vbox = (VBox) node.getFxNode();
		vbox.getChildren().clear();
		node.getChildren().forEach(child -> {
			vbox.getChildren().add(child.getFxNode());
		});
	}



	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SimpleUIRegistry registry) {
		registry.registerBaseFxNodeBuilder(SVBox.class, new SVBox.VBoxNodeBuilder());
		registry.registerProperty(SVBox.class, SizeMinProperty.class, new SizeMinProperty.SizeMinUpdatingBuilder());
		registry.registerProperty(SVBox.class, SizePreferredProperty.class,
				new SizePreferredProperty.SizePreferredUpdatingBuilder());
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
