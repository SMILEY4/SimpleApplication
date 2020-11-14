package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements;

import de.ruegnerlukas.simpleapplication.core.simpleui.core.events.SuiListChangeListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.utils.MutableBiConsumer;
import de.ruegnerlukas.simpleapplication.core.simpleui.utils.MutableConsumer;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ExtendedTabPane extends TabPane {


	/**
	 * The listener listening to closed tabs.
	 */
	private final MutableConsumer<Tab> tabCloseListener = new MutableConsumer<>();


	/**
	 * The listener listening to selected tabs.
	 */
	private final MutableBiConsumer<Tab, Tab> tabSelectedListener = new MutableBiConsumer<>();




	/**
	 * Default constructor.
	 */
	public ExtendedTabPane() {
		getTabs().addListener(new SuiListChangeListener<>(
				addedTab -> {
					addedTab.setOnCloseRequest(e -> onCloseRequest(addedTab));
					addedTab.setOnClosed(e -> onClosed(addedTab));
				},
				removedTab -> {
					// to nothing, listeners are removed in "onClosed()"
				}
		));
		getSelectionModel().selectedItemProperty().addListener((value, prev, next) -> tabSelectedListener.accept(prev, next));
	}




	/**
	 * Set the event listener that gets called when a tab gets called.
	 *
	 * @param listener the listener handling the closed tab or null
	 */
	public void setOnTabClosed(final Consumer<Tab> listener) {
		tabCloseListener.setConsumer(listener);
	}




	/**
	 * Set the event listener that gets called when a tab gets selected.
	 *
	 * @param listener the listener handling the selected tab or null
	 */
	public void setOnTabSelected(final BiConsumer<Tab, Tab> listener) {
		tabSelectedListener.setConsumer(listener);
	}




	/**
	 * Sets the tabs to the given ones
	 *
	 * @param tabs the new tabs
	 */
	public void setTabs(final List<Tab> tabs) {
		tabCloseListener.runMuted(() -> tabSelectedListener.runMuted(() -> {
			final Tab prevSelectedTab = getSelectionModel().getSelectedItem();
			if (!tabs.isEmpty()) {
				getTabs().setAll(tabs);
				final Tab tabToSelect = prevSelectedTab == null ? null : findSelectableTab(prevSelectedTab.getContent());
				getSelectionModel().select(tabToSelect);
			} else {
				getTabs().clear();
			}
		}));
	}




	/**
	 * Finds a selectable tab with the given content node. If no tab was found, the first one is returned
	 *
	 * @param content the content of the tab
	 * @return the tab with or the node as content or the first tab in the list.
	 */
	private Tab findSelectableTab(final Node content) {
		return getTabs().stream()
				.filter(tab -> Objects.equals(tab.getContent(), content))
				.findFirst()
				.orElse(getTabs().isEmpty() ? null : getTabs().get(0));
	}




	/**
	 * Removes all tabs from this tab pane.
	 */
	public void clearTabs() {
		tabCloseListener.runMuted(() -> tabSelectedListener.runMuted(() -> getTabs().clear()));
	}




	/**
	 * Called on a close-request on the given tab.
	 *
	 * @param tab the tab
	 */
	private void onCloseRequest(final Tab tab) {
		// do nothing for now
	}




	/**
	 * Called when the given tab was closed.
	 *
	 * @param tab the closed tab
	 */
	private void onClosed(final Tab tab) {
		tabCloseListener.accept(tab);
		tabSelectedListener.runMuted(() -> {
			tab.setOnCloseRequest(null);
			tab.setOnClosed(null);
			getSelectionModel().select(0);
		});
	}

}
