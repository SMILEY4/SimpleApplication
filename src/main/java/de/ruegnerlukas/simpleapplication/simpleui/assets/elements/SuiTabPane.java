package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedTabPane;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnSelectedTabEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnTabClosedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TabClosingPolicyProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TabPaneMenuSideProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TitleProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNodeChildListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.ArrayList;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiTabPane {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiTabPane() {
		// do nothing
	}




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
	public static SuiTabPaneBuilder create() {
		return new SuiTabPaneBuilder();
	}




	public static class SuiTabPaneBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiTabPaneBuilder>,
			RegionBuilderExtension<SuiTabPaneBuilder>,
			CommonEventBuilderExtension<SuiTabPaneBuilder>,
			ItemProperty.PropertyBuilderExtension<SuiTabPaneBuilder>,
			ItemListProperty.PropertyBuilderExtension<SuiTabPaneBuilder>,
			TooltipProperty.PropertyBuilderExtension<SuiTabPaneBuilder>,
			TabPaneMenuSideProperty.PropertyBuilderExtension<SuiTabPaneBuilder>,
			TabClosingPolicyProperty.PropertyBuilderExtension<SuiTabPaneBuilder>,
			OnTabClosedEventProperty.PropertyBuilderExtension<SuiTabPaneBuilder>,
			OnSelectedTabEventProperty.PropertyBuilderExtension<SuiTabPaneBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return create(
					SuiTabPane.class,
					state,
					tags,
					SuiTabPane.CHILD_NODE_LISTENER,
					null
			);
		}


	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SuiRegistry registry) {
		registry.registerBaseFxNodeBuilder(SuiTabPane.class, new FxNodeBuilder());
		registry.registerProperties(SuiTabPane.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiTabPane.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiTabPane.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiTabPane.class, List.of(
				PropertyEntry.of(ItemListProperty.class, new ItemListProperty.TabPaneBuilder(), null),
				PropertyEntry.of(ItemProperty.class, new ItemProperty.TabPaneBuilder(), null),
				PropertyEntry.of(TabPaneMenuSideProperty.class, new TabPaneMenuSideProperty.TabPaneUpdatingBuilder()),
				PropertyEntry.of(TabClosingPolicyProperty.class, new TabClosingPolicyProperty.TabPaneUpdatingBuilder()),
				PropertyEntry.of(OnSelectedTabEventProperty.class, new OnSelectedTabEventProperty.TabPaneUpdatingBuilder()),
				PropertyEntry.of(OnTabClosedEventProperty.class, new OnTabClosedEventProperty.TabPaneUpdatingBuilder()),
				PropertyEntry.of(TooltipProperty.class, new TooltipProperty.ControlUpdatingBuilder())
		));
	}




	/**
	 * The child node listener for tab panes.
	 */
	protected static final SuiNodeChildListener CHILD_NODE_LISTENER = node -> {
		final ExtendedTabPane pane = (ExtendedTabPane) node.getFxNodeStore().get();
		if (pane != null) {
			if (node.getChildNodeStore().hasChildren()) {
				pane.setTabs(createTabs(node.getChildNodeStore().getUnmodifiable()));
			} else {
				pane.clearTabs();
			}
		}
	};




	/**
	 * Creates tabs from the given nodes
	 *
	 * @param nodes the nodes
	 * @return the created tabs
	 */
	public static List<Tab> createTabs(final List<SuiNode> nodes) {
		final List<Tab> tabs = new ArrayList<>();
		for (SuiNode child : nodes) {
			tabs.add(createTab(child));
		}
		return tabs;
	}




	/**
	 * Creates a tab from the given node
	 *
	 * @param node the node
	 * @return the created tab
	 */
	public static Tab createTab(final SuiNode node) {
		final String title = node.getPropertyStore()
				.getSafe(TitleProperty.class)
				.map(TitleProperty::getTitle)
				.orElse("no-title");
		return new Tab(title, node.getFxNodeStore().get());
	}




	/**
	 * Finds the tab with the given node as content
	 *
	 * @param node the content of the tab to find.
	 * @return the tab or null.
	 */
	public static Tab findParentTab(final Node node) {
		if (node.getParent() != null && node.getParent().getParent() != null && node.getParent().getParent() instanceof TabPane) {
			final TabPane tabPane = (TabPane) node.getParent().getParent();
			for (Tab tab : tabPane.getTabs()) {
				if (tab.getContent().equals(node)) {
					return tab;
				}
			}
		}
		return null;
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<ExtendedTabPane> {


		@Override
		public ExtendedTabPane build(final SuiNode node) {
			final ExtendedTabPane tabPane = new ExtendedTabPane();
			tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
			return tabPane;
		}


	}


}
