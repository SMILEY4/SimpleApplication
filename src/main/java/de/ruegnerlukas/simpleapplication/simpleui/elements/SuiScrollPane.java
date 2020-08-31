package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
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
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import javafx.scene.control.ScrollPane;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.get;

public final class SuiScrollPane {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiScrollPane() {
		// do nothing
	}




	/**
	 * Creates a new scroll-pane node
	 *
	 * @param properties the properties
	 * @return the factory for an scroll-pane node
	 */
	public static NodeFactory scrollPane(final Property... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiScrollPane.class, get().getEntry(SuiScrollPane.class).getProperties(), properties);
		return state -> new SuiNode(SuiScrollPane.class, List.of(properties), state, SuiScrollPane::handleChildrenChange);
	}




	/**
	 * Handle a change in the child nodes of the given scroll-pane node.
	 *
	 * @param node the scroll-pane node
	 */
	private static void handleChildrenChange(final SuiNode node) {
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
	public static void register(final SuiRegistry registry) {
		registry.registerBaseFxNodeBuilder(SuiScrollPane.class, new FxNodeBuilder());
		registry.registerProperties(SuiScrollPane.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiScrollPane.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiScrollPane.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiScrollPane.class, List.of(
				PropertyEntry.of(FitToWidthProperty.class, new FitToWidthProperty.ScrollPaneUpdatingBuilder()),
				PropertyEntry.of(FitToHeightProperty.class, new FitToHeightProperty.ScrollPaneUpdatingBuilder()),
				PropertyEntry.of(ShowScrollbarsProperty.class, new ShowScrollbarsProperty.ScrollPaneUpdatingBuilder()),
				PropertyEntry.of(ItemProperty.class, new ItemProperty.ScrollPaneBuilder(), null),
				PropertyEntry.of(OnScrollHorizontalEventProperty.class, new OnScrollHorizontalEventProperty.ScrollPaneUpdatingBuilder()),
				PropertyEntry.of(OnScrollVerticalEventProperty.class, new OnScrollVerticalEventProperty.ScrollPaneUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements BaseFxNodeBuilder<ScrollPane> {


		@Override
		public ScrollPane build(final MasterNodeHandlers nodeHandlers, final SuiNode node) {
			return new ScrollPane();
		}

	}


}
