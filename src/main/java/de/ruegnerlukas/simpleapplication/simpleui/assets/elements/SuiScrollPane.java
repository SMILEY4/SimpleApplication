package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnScrollHorizontalEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnScrollVerticalEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.FitToHeightProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.FitToWidthProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ShowScrollbarsProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNodeChildListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNodeChildTransformListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
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




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
	public static SuiScrollPaneBuilder create() {
		return new SuiScrollPaneBuilder();
	}




	public static class SuiScrollPaneBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiScrollPaneBuilder>,
			RegionBuilderExtension<SuiScrollPaneBuilder>,
			CommonEventBuilderExtension<SuiScrollPaneBuilder>,
			ItemProperty.PropertyBuilderExtension<SuiScrollPaneBuilder>,
			FitToHeightProperty.PropertyBuilderExtension<SuiScrollPaneBuilder>,
			FitToWidthProperty.PropertyBuilderExtension<SuiScrollPaneBuilder>,
			TooltipProperty.PropertyBuilderExtension<SuiScrollPaneBuilder>,
			ShowScrollbarsProperty.PropertyBuilderExtension<SuiScrollPaneBuilder>,
			OnScrollVerticalEventProperty.PropertyBuilderExtension<SuiScrollPaneBuilder>,
			OnScrollHorizontalEventProperty.PropertyBuilderExtension<SuiScrollPaneBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return create(
					SuiScrollPane.class,
					state,
					tags,
					SuiNodeChildListener.DEFAULT,
					SuiNodeChildTransformListener.DEFAULT
			);
		}


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
