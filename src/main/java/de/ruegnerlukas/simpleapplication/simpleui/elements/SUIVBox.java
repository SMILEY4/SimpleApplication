package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
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
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import javafx.scene.layout.VBox;

import java.util.List;

public final class SUIVBox {


	/**
	 * Hidden constructor for utility classes.
	 */
	private SUIVBox() {
		// do nothing
	}




	/**
	 * Creates a new vbox node.
	 *
	 * @param properties the properties
	 * @return the factory for a vbox node
	 */
	public static NodeFactory vbox(final Property... properties) {
		Properties.checkIllegal(SUIVBox.class, SUIRegistry.get().getEntry(SUIVBox.class).getProperties(), properties);
		return state -> new SUINode(SUIVBox.class, List.of(properties), state, SUIVBox::handleChildrenChange);
	}




	/**
	 * Handle a change in the child nodes of the given vbox node.
	 *
	 * @param node the vbox node
	 */
	private static void handleChildrenChange(final SUINode node) {
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
	public static void register(final SUIRegistry registry) {
		registry.registerBaseFxNodeBuilder(SUIVBox.class, new SUIVBox.VBoxNodeBuilder());
		registry.registerProperty(SUIVBox.class, SizeMinProperty.class, new SizeMinProperty.SizeMinUpdatingBuilder());
		registry.registerProperty(SUIVBox.class, SizePreferredProperty.class,
				new SizePreferredProperty.SizePreferredUpdatingBuilder());
		registry.registerProperty(SUIVBox.class, SizeMaxProperty.class, new SizeMaxProperty.SizeMaxUpdatingBuilder());
		registry.registerProperty(SUIVBox.class, SizeProperty.class, new SizeProperty.SizeUpdatingBuilder());
		registry.registerProperty(SUIVBox.class, DisabledProperty.class, new DisabledProperty.DisabledUpdatingBuilder());
		registry.registerProperty(SUIVBox.class, ItemListProperty.class, new ItemListProperty.ItemListBuilder());
		registry.registerProperty(SUIVBox.class, SpacingProperty.class, new SpacingProperty.VBoxSpacingUpdatingBuilder());
		registry.registerProperty(SUIVBox.class, AlignmentProperty.class, new AlignmentProperty.VBoxAlignmentUpdatingBuilder());
		registry.registerProperty(SUIVBox.class, FitToWidthProperty.class, new FitToWidthProperty.VBoxFitToWidthUpdatingBuilder());
	}




	private static class VBoxNodeBuilder implements BaseFxNodeBuilder<VBox> {


		@Override
		public VBox build(final MasterNodeHandlers nodeHandlers, final SUINode node) {
			return new VBox();
		}

	}


}
