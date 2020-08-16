package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NoOpUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.IdMutationStrategy.AddOperation;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.IdMutationStrategy.RemoveOperation;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.IdMutationStrategy.ReplaceOperation;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.IdMutationStrategy.SwapOperation;
import de.ruegnerlukas.simpleapplication.simpleui.properties.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.DisabledProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.FitToWidthProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.MutationBehaviourProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeMaxProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeMinProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizePreferredProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SpacingProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.StyleProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.stream.Collectors;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry.get;

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
		Properties.checkIllegal(SUIVBox.class, get().getEntry(SUIVBox.class).getProperties(), properties);
		return state -> new SUINode(SUIVBox.class, List.of(properties), state, SUIVBox::handleChildrenChange, SUIVBox::handleChildrenTransform);
	}




	/**
	 * Handle a change in the child nodes of the given vbox node.
	 *
	 * @param node the vbox node
	 */
	private static void handleChildrenChange(final SUINode node) {
		final VBox vbox = (VBox) node.getFxNode();
		if (node.hasChildren()) {
			vbox.getChildren().setAll(node.streamChildren()
					.map(SUINode::getFxNode)
					.collect(Collectors.toList()));
		} else {
			vbox.getChildren().clear();
		}
	}




	private static void handleChildrenTransform(final SUINode node,
												final List<ReplaceOperation> replaceOperations,
												final List<RemoveOperation> removeOperations,
												final List<AddOperation> addOperations,
												final List<SwapOperation> swapOperations) {
		final VBox vbox = (VBox) node.getFxNode();
//		operations.forEach(op -> op.apply(vbox));

		/*
		Order of operations:
		- remove
		- add
		- swap
		- replace
		 */

		List<Node> nodesToRemove = removeOperations.stream()
				.map(o -> o.getNode().getFxNode())
				.collect(Collectors.toList());
		vbox.getChildren().removeAll(nodesToRemove);

		addOperations.forEach(op -> op.apply(vbox));
		swapOperations.forEach(op -> op.apply(vbox));
		replaceOperations.forEach(op -> op.apply(vbox));

	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SUIRegistry registry) {
		registry.registerBaseFxNodeBuilder(SUIVBox.class, new SUIVBox.VBoxNodeBuilder());

		registry.registerProperties(SUIVBox.class, List.of(
				// node
				PropertyEntry.of(MutationBehaviourProperty.class, new NoOpUpdatingBuilder()),
				PropertyEntry.of(DisabledProperty.class, new DisabledProperty.DisabledUpdatingBuilder()),
				PropertyEntry.of(StyleProperty.class, new StyleProperty.StyleUpdatingBuilder()),
				// region
				PropertyEntry.of(SizeMinProperty.class, new SizeMinProperty.SizeMinUpdatingBuilder()),
				PropertyEntry.of(SizePreferredProperty.class, new SizePreferredProperty.SizePreferredUpdatingBuilder()),
				PropertyEntry.of(SizeMaxProperty.class, new SizeMaxProperty.SizeMaxUpdatingBuilder()),
				PropertyEntry.of(SizeProperty.class, new SizeProperty.SizeUpdatingBuilder()),
				// special
				PropertyEntry.of(FitToWidthProperty.class, new FitToWidthProperty.VBoxFitToWidthUpdatingBuilder()),
				PropertyEntry.of(SpacingProperty.class, new SpacingProperty.VBoxSpacingUpdatingBuilder()),
				PropertyEntry.of(AlignmentProperty.class, new AlignmentProperty.VBoxAlignmentUpdatingBuilder()),
				PropertyEntry.of(ItemListProperty.class, new ItemListProperty.ItemListBuilder(), null)
		));
	}




	private static class VBoxNodeBuilder implements BaseFxNodeBuilder<VBox> {


		@Override
		public VBox build(final MasterNodeHandlers nodeHandlers, final SUINode node) {
			return new VBox();
		}

	}


}
