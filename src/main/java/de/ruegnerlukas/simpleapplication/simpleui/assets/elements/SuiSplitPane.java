package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedSplitPane;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnDividerDraggedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.OrientationProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SplitDividerPositionProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.operations.OperationType;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.operations.RemoveOperation;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNodeChildListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNodeChildTransformListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;

import java.util.List;
import java.util.stream.Collectors;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiSplitPane {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiSplitPane() {
		// do nothing
	}




	/**
	 * Creates a new split pane
	 *
	 * @param properties the properties
	 * @return the factory for a split pane
	 */
	public static NodeFactory splitPane(final SuiProperty... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiSplitPane.class, properties);
		return (state, tags) -> SuiNode.create(
				SuiSplitPane.class,
				List.of(properties),
				state,
				tags,
				CHILD_LISTENER,
				CHILD_TRANSFORM_LISTENER
		);
	}




	/**
	 * A child listener for to split panes.
	 */
	private static final SuiNodeChildListener CHILD_LISTENER = node -> {
		final SplitPane pane = (SplitPane) node.getFxNodeStore().get();
		if (pane != null) {
			if (node.getChildNodeStore().hasChildren()) {
				pane.getItems().setAll(node.getChildNodeStore().stream()
						.map(child -> child.getFxNodeStore().get())
						.collect(Collectors.toList()));
			} else {
				pane.getItems().clear();
			}
		}
	};

	/**
	 * A child transform listener for to split panes.
	 */
	private static final SuiNodeChildTransformListener CHILD_TRANSFORM_LISTENER = (node, type, operations) -> {
		final SplitPane pane = (SplitPane) node.getFxNodeStore().get();
		if (pane != null) {
			if (type == OperationType.REMOVE) {
				List<Node> nodesToRemove = operations.stream()
						.map(op -> (RemoveOperation) op)
						.map(op -> op.getNode().getFxNodeStore().get())
						.collect(Collectors.toList());
				pane.getItems().removeAll(nodesToRemove);
			} else {
				operations.forEach(op -> op.applyToFx(pane.getItems()));
			}
		}
	};




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SuiRegistry registry) {
		registry.registerBaseFxNodeBuilder(SuiSplitPane.class, new FxNodeBuilder());
		registry.registerProperties(SuiSplitPane.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiSplitPane.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiSplitPane.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiSplitPane.class, List.of(
				PropertyEntry.of(ItemListProperty.class, new ItemListProperty.SplitPaneBuilder(), null),
				PropertyEntry.of(ItemProperty.class, new ItemProperty.SplitPaneBuilder(), null),
				PropertyEntry.of(OrientationProperty.class, new OrientationProperty.SplitPaneUpdatingBuilder()),
				PropertyEntry.of(SplitDividerPositionProperty.class, new SplitDividerPositionProperty.SplitPaneUpdatingBuilder()),
				PropertyEntry.of(OnDividerDraggedEventProperty.class, new OnDividerDraggedEventProperty.SplitPaneUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<SplitPane> {


		@Override
		public SplitPane build(final SuiNode node) {
			return new ExtendedSplitPane();
		}

	}


}
