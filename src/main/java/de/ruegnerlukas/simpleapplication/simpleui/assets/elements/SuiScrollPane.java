package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnScrollHorizontalEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnScrollVerticalEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.FitToHeightProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.FitToWidthProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ShowScrollbarsProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNodeChildListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNodeChildTransformListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.scene.control.ScrollPane;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiScrollPane {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiScrollPane() {
		// do nothing
	}




	public static SuiScrollPaneBuilder create() {
		return new SuiScrollPaneBuilder();
	}




	/**
	 * Creates a new scroll-pane node
	 *
	 * @param properties the properties
	 * @return the factory for an scroll-pane node
	 */
	public static NodeFactory scrollPane(final SuiProperty... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiScrollPane.class, properties);
		return (state, tags) -> SuiNode.create(
				SuiScrollPane.class,
				List.of(properties),
				state,
				tags,
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
		final ScrollPane scrollPane = (ScrollPane) node.getFxNodeStore().get();
		if (node.getChildNodeStore().hasChildren()) {
			scrollPane.setContent(node.getChildNodeStore().get(0).getFxNodeStore().get());
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
				PropertyEntry.of(ItemProperty.class, new ItemProperty.ScrollPaneBuilder(), null),
				PropertyEntry.of(FitToWidthProperty.class, new FitToWidthProperty.ScrollPaneUpdatingBuilder()),
				PropertyEntry.of(FitToHeightProperty.class, new FitToHeightProperty.ScrollPaneUpdatingBuilder()),
				PropertyEntry.of(ShowScrollbarsProperty.class, new ShowScrollbarsProperty.ScrollPaneUpdatingBuilder()),
				PropertyEntry.of(OnScrollHorizontalEventProperty.class, new OnScrollHorizontalEventProperty.ScrollPaneUpdatingBuilder()),
				PropertyEntry.of(OnScrollVerticalEventProperty.class, new OnScrollVerticalEventProperty.ScrollPaneUpdatingBuilder()),
				PropertyEntry.of(TooltipProperty.class, new TooltipProperty.ControlUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<ScrollPane> {


		@Override
		public ScrollPane build(final SuiNode node) {
			return new ScrollPane();
		}

	}


}
