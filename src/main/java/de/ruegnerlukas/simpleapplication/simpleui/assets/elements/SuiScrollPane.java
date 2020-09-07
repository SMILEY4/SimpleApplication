package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnScrollHorizontalEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnScrollVerticalEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.FitToHeightProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.FitToWidthProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ShowScrollbarsProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNodeChildListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNodeChildTransformListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.scene.control.ScrollPane;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.get;

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
		return state -> SuiBaseNode.create(
				SuiLabel.class,
				List.of(properties),
				state,
				SuiNodeChildListener.DEFAULT,
				SuiNodeChildTransformListener.NO_OP
		);
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




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<ScrollPane> {


		@Override
		public ScrollPane build(final SuiBaseNode node) {
			return new ScrollPane();
		}

	}


}
