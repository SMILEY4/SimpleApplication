package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.FitToHeightProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.FitToWidthProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ShowScrollbarsProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnScrollHorizontalEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.OnScrollVerticalEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import javafx.scene.control.ScrollPane;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry.get;

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
		Properties.validate(SUIScrollPane.class, get().getEntry(SUIScrollPane.class).getProperties(), properties);
		return state -> new SUINode(SUIScrollPane.class, List.of(properties), state, SUIScrollPane::handleChildrenChange);
	}




	/**
	 * Handle a change in the child nodes of the given scroll-pane node.
	 *
	 * @param node the scroll-pane node
	 */
	private static void handleChildrenChange(final SUINode node) {
		final ScrollPane scrollPane = (ScrollPane) node.getFxNode();
		if (node.hasChildren()) {
			scrollPane.setContent(node.getChild(0).getFxNode());
		} else {
			scrollPane.setContent(null);
		}
	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SUIRegistry registry) {
		registry.registerBaseFxNodeBuilder(SUIScrollPane.class, new SUIScrollPane.ScrollPaneNodeBuilder());
		registry.registerProperties(SUIScrollPane.class, PropertyGroups.commonProperties());
		registry.registerProperties(SUIScrollPane.class, PropertyGroups.commonNodeProperties());
		registry.registerProperties(SUIScrollPane.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SUIScrollPane.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SUIScrollPane.class, List.of(
				PropertyEntry.of(FitToWidthProperty.class, new FitToWidthProperty.ScrollPaneFitToWidthUpdatingBuilder()),
				PropertyEntry.of(FitToHeightProperty.class, new FitToHeightProperty.ScrollPaneFitToHeightUpdatingBuilder()),
				PropertyEntry.of(ShowScrollbarsProperty.class, new ShowScrollbarsProperty.ShowScrollbarUpdatingBuilder()),
				PropertyEntry.of(ItemProperty.class, new ItemProperty.ScrollPaneContentBuilder(), null),
				PropertyEntry.of(OnScrollHorizontalEventProperty.class, new OnScrollHorizontalEventProperty.ScrollPaneUpdatingBuilder()),
				PropertyEntry.of(OnScrollVerticalEventProperty.class, new OnScrollVerticalEventProperty.ScrollPaneUpdatingBuilder())
		));
	}




	private static class ScrollPaneNodeBuilder implements BaseFxNodeBuilder<ScrollPane> {


		@Override
		public ScrollPane build(final MasterNodeHandlers nodeHandlers, final SUINode node) {
			return new ScrollPane();
		}

	}


}
