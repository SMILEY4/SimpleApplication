package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.TabAwareTabPane;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnSelectedTabEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnTabClosedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TabClosingPolicyProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TabPaneMenuSideProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TabTitleProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNodeChildListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiTabPane {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiTabPane() {
		// do nothing
	}




	/**
	 * Creates a new tab pane
	 *
	 * @param properties the properties
	 * @return the factory for a tab pane
	 */
	public static NodeFactory tabPane(final SuiProperty... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiTabPane.class, properties);
		return (state, tags) -> SuiNode.create(
				SuiTabPane.class,
				List.of(properties),
				state,
				tags,
				CHILD_NODE_LISTENER,
				null
		);
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
				PropertyEntry.of(OnTabClosedEventProperty.class, new OnTabClosedEventProperty.TabPaneUpdatingBuilder())
		));
	}




	/**
	 * The child node listener for tab panes.
	 */
	private static final SuiNodeChildListener CHILD_NODE_LISTENER = node -> {
		final TabPane pane = (TabPane) node.getFxNodeStore().get();
		if (pane != null) {

			muteListeners(node);

			final Tab prevSelectedTab = pane.getSelectionModel().getSelectedItem();
			if (node.getChildNodeStore().hasChildren()) {
				pane.getTabs().setAll(createTabs(node.getChildNodeStore().getUnmodifiable()));
				final Tab tabToSelect = pane.getTabs().stream()
						.filter(tab -> Objects.equals(tab.getContent(), prevSelectedTab.getContent()))
						.findFirst()
						.orElse(pane.getTabs().get(0));
				pane.getSelectionModel().select(tabToSelect);
			} else {
				pane.getTabs().clear();
			}

			unmuteListeners(node);

			final Tab nextSelectedTab = pane.getSelectionModel().getSelectedItem();
			final Node prevSelectedTabContent = prevSelectedTab == null ? null : prevSelectedTab.getContent();
			final Node nextSelectedTabContent = nextSelectedTab == null ? null : nextSelectedTab.getContent();
			if (prevSelectedTabContent != nextSelectedTabContent) {
				node.getPropertyStore().getSafe(OnSelectedTabEventProperty.class).ifPresent(prop -> {
					prop.getChangeListenerProxy().fireManually(prevSelectedTab, pane.getSelectionModel().getSelectedItem());
				});
			}

		}
	};




	/**
	 * Mutes the change listener of an {@link OnSelectedTabEventProperty}.
	 *
	 * @param node the node
	 */
	private static void muteListeners(final SuiNode node) {
		node.getPropertyStore().getSafe(OnSelectedTabEventProperty.class).ifPresent(prop ->
				prop.getChangeListenerProxy().setMuted(true));
	}




	/**
	 * Un-Mutes the change listener of an {@link OnSelectedTabEventProperty}.
	 *
	 * @param node the node
	 */
	private static void unmuteListeners(final SuiNode node) {
		node.getPropertyStore().getSafe(OnSelectedTabEventProperty.class).ifPresent(prop ->
				prop.getChangeListenerProxy().setMuted(false));
	}




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
				.getSafe(TabTitleProperty.class)
				.map(TabTitleProperty::getTitle)
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




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<TabPane> {


		@Override
		public TabPane build(final SuiNode node) {
			final TabPane tabPane = new TabAwareTabPane();
			tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
			return tabPane;
		}


	}


}
